package com.acme.sandbox.persistence;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ContactRunner {
  private static final Logger LOG = LoggerFactory.getLogger(ContactRunner.class);

  // mvn \
  //    compile \
  //    org.datanucleus:datanucleus-maven-plugin:enhance \
  //    exec:java \
  //   -Dexec.mainClass=com.acme.sandbox.persistence.ContactRunner
  // Note: Use JCommander if you need to parse command line ops.

  public static void main(String[] args) {
    PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("mongodb");
    ContactDao dao = new ContactDao(pmf);

    Contact contact = new Contact();
    contact.name = "Contact # " + System.currentTimeMillis();
    contact.email = "test@yahoo.com";
    contact.phoneNumber = "2122221234";
    contact.address = new Address();
    contact.address.setAddress("212 Main St.\nSuite 123");
    contact.address.setCity("New York");
    contact.address.setRegion("NY");
    contact.address.setPostalCode("10011");
    contact.address.setCountry("US");

    dao.save(contact);
    LOG.info("Saved contact {}.", contact);

    LOG.info("Searching...");
    List<Contact> found = dao.search("id > 0", "id desc");
    for (Contact c : found) {
      System.out.printf("%s%n", c);
    }

    // LOG.info("Deleting {}", found.get(0));
    // dao.delete(found.get(0));
  }
}
