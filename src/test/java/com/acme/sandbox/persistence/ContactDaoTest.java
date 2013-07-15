package com.acme.sandbox.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MongoDatabaseTestRunner.class)
public class ContactDaoTest {
  static final Logger LOG = LoggerFactory.getLogger(MongoDatabaseTestRunner.class);
  static PersistenceManagerFactory pmf;

  // Class under test.
  ContactDao dao;

  @BeforeClass
  public static void beforeAll() throws Exception {
    pmf = JDOHelper.getPersistenceManagerFactory("mongodbtest");
    LOG.info("Connection URL: {}", pmf.getConnectionURL());
  }

  @Before
  public void setUp() throws Exception {
    dao = new ContactDao(pmf);
  }

  @Test
  public void basicCrudOps() throws Exception {
    Contact c1 = newContact("DB Cooper");

    dao.save(c1);
    assertTrue("Contact ID should be set.", c1.id > 0);
    assertTrue("Address ID should be set.", c1.address.getId() > 0);
    assertNotNull("Should retain values after save.", c1.phoneNumber);

    List<Contact> searchResults = dao.search("id == " + c1.id);
    assertEquals(1, searchResults.size());

    Contact expected = newContact("DB Cooper");
    expected.id = c1.id;
    expected.address.setId(c1.address.getId());
    assertEquals(expected, searchResults.get(0));

    dao.delete(c1);
    List<Contact> deleteResults = dao.search("id == " + c1.id);
    assertEquals(0, deleteResults.size());
  }

  private Contact newContact(String name) {
    Contact c1 = new Contact();
    c1.name = name;
    c1.email = name.toLowerCase().replaceAll(" ", ".") + "@hotmail.com";
    c1.phoneNumber = "2122221234";
    c1.address = new Address();
    c1.address.setAddress("212 Main St.\nSuite 123");
    c1.address.setCity("New York");
    c1.address.setRegion("NY");
    c1.address.setPostalCode("10011");
    c1.address.setCountry("US");
    return c1;
  }

  // TODO: Test save/search/deletion of Address.
  // TODO: Test search stuff like pagination, text search, etc.
}
