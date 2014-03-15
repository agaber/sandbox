package com.acme.sandbox.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Publisher {
  private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

  public static void main(String... args) throws Exception {
    Injector injector = Guice.createInjector(new PubSubModule(args));
    injector.getInstance(Publisher.class).execute();
    LOG.info("We're done here.");
  }

  private final ConnectionFactory connectionFactory;
  private final Queue queue;
  private final BufferedReader reader;
  private final PrintStream out;

  @Inject
  Publisher(
      ConnectionFactory connectionFactory,
      Queue queue,
      BufferedReader reader,
      PrintStream out) {
    this.connectionFactory = connectionFactory;
    this.queue = queue;
    this.reader = reader;
    this.out = out;
  }

  public void execute() throws Exception {
    try (Connection connection = connectionFactory.createConnection()) {
      connection.start();
      produceMessages(connection.createSession());
    }
  }

  private void produceMessages(Session session) throws IOException, JMSException {
    MessageProducer producer = session.createProducer(queue);
    out.println("Enter messages. Type quit to end.");
    boolean quit = false;
    while (!quit) {
      out.printf("%n> ");
      String message = reader.readLine().trim();
      if (!(quit = "quit".equals(message))) {
        producer.send(session.createTextMessage(message));
      }
    }
  }
}
