package com.acme.sandbox.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Prompts for messages and publishes them to JMS.
 *
 * <pre>
 * mvn exec:java \
 *  -Dexec.mainClass="com.acme.sandbox.pubsub.Publisher" \
 *  -Dexec.args="-h localhost -p 5445"
 * </pre>
 */
public class Publisher implements Runnable {
  private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

  public static void main(String... args) throws Exception {
    Injector injector = Guice.createInjector(new PubSubModule(args));
    injector.getInstance(Publisher.class).run();
    LOG.info("We're done here.");
  }

  private final ConnectionFactory connectionFactory;
  private final Destination destination;
  private final BufferedReader reader;
  private final PrintStream out;

  @Inject
  Publisher(
      ConnectionFactory connectionFactory,
      Destination destination,
      BufferedReader reader,
      PrintStream out) {
    this.connectionFactory = connectionFactory;
    this.destination = destination;
    this.reader = reader;
    this.out = out;
  }

  public void run() {
    try (Connection connection = connectionFactory.createConnection()) {
      connection.start();
      Session session = connection.createSession();
      produce(session.createProducer(destination), session);
    } catch (JMSException | IOException e) {
      LOG.error("Something bad happened.", e);
    }
  }

  private void produce(MessageProducer producer, Session session) throws IOException, JMSException {
    out.println("Enter messages. Type quit to end.");
    boolean quit = false;
    while (!quit) {
      out.printf("%n> ");
      String message = reader.readLine().trim();
      if (!(quit = "quit".equals(message))) {
        // The receiver could potentially filter by the correlation ID:
        // session.createConsumer(queue,  "JMSCorrelationID ='ID'"
        ObjectMessage o = session.createObjectMessage();
        o.setJMSCorrelationID("messages");
        o.setStringProperty("message", message);
        producer.send(o);
      }
    }
  }
}
