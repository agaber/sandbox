package com.acme.sandbox.pubsub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.inject.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.hornetq.api.core.TransportConfiguration;
import org.hornetq.api.jms.HornetQJMSClient;
import org.hornetq.api.jms.JMSFactoryType;
import org.hornetq.core.remoting.impl.netty.NettyConnectorFactory;
import org.hornetq.core.remoting.impl.netty.TransportConstants;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.acme.sandbox.common.MoreStrings;
import com.acme.sandbox.common.MoreStrings.Format;
import com.acme.sandbox.common.MoreStrings.Stringifiable;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * In this package I'm experimenting with publisher/subscribe in Java using
 * JBoss' HornetQ, which seemed popular and supported JMS 2.0.
 *
 * <p>To make this work you need a JMS server (again I was using HornetQ) and
 * you need to make configuration changes to your server. See
 * {@link #destination(Args)} for a description about that.
 *
 * <p>I'm just fooling around here but according to the documentation I've read
 * we should try to limit creation of Connection, Session, MessageProducer and
 * MessageConsumer to once per application (i.e. during startup). That be the
 * case we should provide all of those objects in this Module but right now
 * it's left to the injected classes to manage.
 *
 * <p>For optimzation of JMS see:
 * <li>http://activemq.apache.org/how-do-i-use-jms-efficiently.html.
 *
 * <p>Some other reference material I found useful at the time:
 * <li><a href="http://goo.gl/D7EvLh">Java EE 5 Manual</a>
 * <li><a href="http://goo.gl/N3M4Nw">JBoss HornetQ User Guide</a>
 * <li><a href="http://goo.gl/sKStNM">link 3</a>
 * <li><a href="http://goo.gl/5bw4Cl">link 4</a>
 * <li><a href="http://goo.gl/Yo87qo">link 5</a>
 * <li><a href="http://goo.gl/Ryt2XU">link 6</a>
 *
 * <p>I eschew configuration files here but I'm guessing that's how most people
 * use JMS. I think they also use JNDI normally. I didn't try any of that here
 * (and I don't think I want to).
 */
public class PubSubModule extends AbstractModule {
  private static final Logger LOG = LoggerFactory.getLogger(PubSubModule.class);
  private final String[] args;

  public PubSubModule(String[] args) {
    this.args = args;
  }

  @Override
  protected void configure() {
    // If I was writing unit tests it might be helpful to mock file I/O.
    bind(BufferedReader.class).toInstance(new BufferedReader(new InputStreamReader(System.in)));
    bind(PrintStream.class).toInstance(System.out);
  }

  @Provides @Singleton
  public Args args() throws CmdLineException {
    Args.Builder argsBuilder = new Args.Builder();
    CmdLineParser parser = new CmdLineParser(argsBuilder);
    parser.parseArgument(args);
    return argsBuilder.build();
  }

  @Provides @Singleton
  public ConnectionFactory connectionFactory(Args args) {
    LOG.info("Using Command Line Args: {}", MoreStrings.stringify(args, Format.YAML));
    ImmutableMap.Builder<String, Object> params = ImmutableMap.builder();
    params.put(TransportConstants.HOST_PROP_NAME, args.host);
    params.put(TransportConstants.PORT_PROP_NAME, args.port);
    TransportConfiguration transportConfig = new TransportConfiguration(
        NettyConnectorFactory.class.getName(),
        params.build());
    return HornetQJMSClient.createConnectionFactoryWithoutHA(
        JMSFactoryType.QUEUE_CF,
        transportConfig);
  }

  /**
   * Returns the DemoQueue or DemoTopic which is defined in the JMS server's
   * hornetq-jms.xml file. For my installation of Hornetq I had to add it here:
   *
   * <code>
   * $HORNETQ_HOME/config/stand-alone/non-clustered/hornetq-jms.xml
   * </code>
   *
   * <p>The relevant changes I made to the file above looked like this:
   * <pre>
   * &gt;queue name="DemoQueue"&lt;
   *   &gt;entry name="/queue/demo"/&lt;
   * &gt;/queue&lt;
   *
   * &gt;topic name="DemoTopic"&lt;
   *   &lt;entry name="/topic/demo"/&gt;
   * &gt;/topic&lt;
   * </pre>
   *
   * <p>Messages sent to a queue will only be received by one client, which is
   * different than a Topic which will be received by all listeners.
   *
   * <p>I think you can also do session.createQueue by the way. I need to learn
   * how to set persistence options and stuff like that and also how to add
   * filters.
   */
  @Provides @Singleton
  public Destination destination(Args args) {
    return args.topic
        ? HornetQJMSClient.createTopic("DemoTopic")
        : HornetQJMSClient.createQueue("DemoQueue");
  }

  /** Takes in the command line arguments. */
  @Stringifiable
  public static class Args {
    public final String host;
    public final int port;
    public final boolean topic;

    public Args(String host, int port, boolean topic) {
      this.host = host;
      this.port = port;
      this.topic = topic;
    }

    static class Builder {
      @Option(name = "-h", aliases = "--host", usage = "JMS host name.")
      public String host = "localhost";

      @Option(name = "-p", aliases = "--port", usage = "JMS port name.")
      public int port = 5445;

      @Option(
          name = "-t",
          aliases = "--topic",
          usage = "Specify this flag if a JMS topic should be used, otherwise use a JMS queue.")
      public boolean topic = false;

      public Args build() {
        return new Args(host, port, topic);
      }
    }
  }

}
