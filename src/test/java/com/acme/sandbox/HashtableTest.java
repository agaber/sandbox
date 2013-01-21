package com.acme.sandbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HashtableTest {

  @Test
  public void itShouldPutAndGet() {
    Hashtable<String, String> table = new Hashtable<String, String>();
    table.put("a", "abc");
    table.put("b", "bcd");
    table.put("c", "cde");

    assertEquals("abc", table.get("a"));
    assertEquals("bcd", table.get("b"));
    assertEquals("cde", table.get("c"));
  }
}
