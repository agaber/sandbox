package com.acme.sandbox.pubsub;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Subscribes to JMS messages and prints them. I was running several of these
 * in different terminals to see how it behaves.
 *
 * <pre>
 * mvn exec:java \
 *  -Dexec.mainClass="com.acme.sandbox.pubsub.Subscriber" \
 *  -Dexec.args="-h localhost -p 5445"
 * </pre>
 */
public class Subscriber implements Runnable, MessageListener {
  private static final Logger LOG = LoggerFactory.getLogger(Subscriber.class);

  public static void main(String... args) throws Exception {
    // Print a message before shutting down.
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        LOG.info("We're done here.");
      }
    });

    // Listen for new messages.
    Injector injector = Guice.createInjector(new PubSubModule(args));
    injector.getInstance(Subscriber.class).run();
  }

  private final ConnectionFactory connectionFactory;
  private final Destination destination;
  private final PrintStream out;

  @Inject
  Subscriber(
      ConnectionFactory connectionFactory,
      Destination destination,
      PrintStream out) {
    this.connectionFactory = connectionFactory;
    this.destination = destination;
    this.out = out;
  }

  public void run() {
    try (Connection connection = connectionFactory.createConnection()) {
      connection.start();
      Session session = connection.createSession();
      MessageConsumer consumer = session.createConsumer(destination);
      consumer.setMessageListener(this);

      // Keep alive.
      while (true) {
        sleep(TimeUnit.MINUTES.toMillis(1));
      }
    } catch (JMSException e) {
      LOG.error("Something bad happened.", e);
    }
  }

  @Override
  public void onMessage(Message message) {
    try {
      out.printf("%nReceived message: %s%n", message.getStringProperty("message"));
    } catch (JMSException e) {
      LOG.error("Unable to receive message.", e);
    }
  }

  private void sleep(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      Thread.interrupted();
    }
  }
}
