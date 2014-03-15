package com.acme.sandbox.pubsub;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.inject.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

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
 * <p>Some reference material I found useful at the time:
 * <li>http://docs.oracle.com/javaee/5/tutorial/doc/bnbpn.html
 * <li>http://docs.jboss.org/hornetq/2.4.0.Final/docs/user-manual/html_single/index.html
 * <li>http://hornetq.blogspot.com/2009/09/hornetq-simple-example-using-maven.html
 * <li>http://java.dzone.com/articles/hornetq-getting-started
 * <li>http://activemq.apache.org/hello-world.html
 * <li>http://howtodoinjava.com/2013/03/22/basic-jms-messaging-example-using-hornetq-stand-alone-server/
 *
 * <p>I eschew configuration files but I'm guessing that's how most people use
 * JMS. I didn't try that (and I don't want to).
 */
public class PubSubModule extends AbstractModule {
  private static final Logger LOG = LoggerFactory.getLogger(PubSubModule.class);

  private final String[] args;

  public PubSubModule(String[] args) {
    this.args = args;
  }

  @Override
  protected void configure() {
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
   * Returns the DemoQueue which is defined in the JMS server's hornetq-jms.xml
   * file. For my installation of Hornetq I had to add it here:
   *
   * <code>
   * $HORNETQ_HOME/config/stand-alone/non-clustered/hornetq-jms.xml
   * </code>
   *
   * <p>Messages sent to a queue will only be received by one client, which is
   * different than a Topic which I believe will be received by all listeners.
   *
   * <p>I think you can also do session.createQueue by the way. I need to learn
   * how to set persistence options and stuff like that and also how to add
   * filters.
   */
  @Provides @Singleton
  public Queue queue() {
    return HornetQJMSClient.createQueue("DemoQueue");
  }

  /** Takes in the command line arguments. */
  @Stringifiable
  public static class Args {
    public final String host;
    public final int port;

    public Args(String host, int port) {
      this.host = host;
      this.port = port;
    }

    static class Builder {
      @Option(name = "-h", aliases = "--host", usage = "JMS host name.")
      public String host = "localhost";

      @Option(name = "-p", aliases = "--port", usage = "JMS port name.")
      public int port = 5445;

      public Args build() {
        return new Args(host, port);
      }
    }
  }

}
