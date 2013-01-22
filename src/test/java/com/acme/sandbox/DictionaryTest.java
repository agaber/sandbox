package com.acme.sandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class DictionaryTest {

  @Test
  public void itShouldPutAndGet() throws Exception {
    Map<String, String> table = new Dictionary<String, String>();
    table.put("a", "abc");
    table.put("b", "bcd");
    table.put("c", "cde");

    assertEquals("abc", table.get("a"));
    assertEquals("bcd", table.get("b"));
    assertEquals("cde", table.get("c"));

    table.put("c", "new value");
    assertEquals("new value", table.get("c"));

    assertNull(table.get("x"));
  }


  @Test
  public void itShouldReturnTheSizeAndIsEmpty() throws Exception {
    Map<String, String> table = new Dictionary<String, String>();
    assertTrue(table.isEmpty());

    table.put("a", "abc");
    assertEquals(1, table.size());
    assertFalse(table.isEmpty());

    table.put("b", "bcd");
    assertEquals(2, table.size());
    assertFalse(table.isEmpty());

    table.put("c", "cde");
    assertEquals(3, table.size());
    assertFalse(table.isEmpty());
  }

  @Test
  public void itShouldRemove() throws Exception {
    Map<Integer, String> table = new Dictionary<Integer, String>();
    table.put(1, "a");
    table.put(2, "b");

    String removed = table.remove(1);
    assertEquals("a", removed);
    assertEquals("b", table.get(2));
    assertEquals(1, table.size());
    assertFalse(table.isEmpty());

    String removed2 = table.remove(2);
    assertEquals("b", removed2);
    assertEquals(0, table.size());
    assertTrue(table.isEmpty());

    String removed3 = table.remove(4);
    assertNull(removed3);
    assertEquals(0, table.size());
    assertTrue(table.isEmpty());
  }

  @Test
  public void itShouldClear() throws Exception {
    Map<Integer, String> table = new Dictionary<Integer, String>();
    table.put(1, "a");
    table.put(2, "b");

    table.clear();
    assertEquals(0, table.size());
    assertTrue(table.isEmpty());
  }
}
