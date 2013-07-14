package com.acme.sandbox.persistence;

import java.util.Collection;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class ContactDao {
  private final PersistenceManagerFactory pmf;

  public ContactDao(PersistenceManagerFactory pmf) {
    this.pmf = pmf;
  }

  public void delete(Contact contact) {
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction transaction = pm.currentTransaction();
    try {
      transaction.begin();
      pm.deletePersistent(pm.getObjectById(contact.getClass(), contact.id));
      transaction.commit();
    } finally {
      close(pm, transaction);
    }
  }

  public long save(Contact contact) {
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction transaction = pm.currentTransaction();
    try {
      transaction.begin();
      pm.makePersistent(contact);
      transaction.commit();
      return contact.id;
    } finally {
      close(pm, transaction);
    }
  }

  public List<Contact> search(String query) {
    return search(query, null);
  }

  public List<Contact> search(String query, String ordering) {
    PersistenceManager pm = pmf.getPersistenceManager();
    Transaction transaction = pm.currentTransaction();
    try {
      Extent<Contact> extent = pm.getExtent(Contact.class, true);
      Query q = pm.newQuery(extent, query);

      if (!Strings.isNullOrEmpty(ordering)) {
        q.setOrdering(ordering);
      }

      @SuppressWarnings("unchecked")
      Collection<Contact> c = (Collection<Contact>) q.execute();
      return Lists.newArrayList(c);
    } finally {
      close(pm, transaction);
    }
  }

  private void close(PersistenceManager pm, Transaction transaction) {
    if (transaction.isActive()) {
      transaction.rollback();
    }
    pm.close();
  }
}