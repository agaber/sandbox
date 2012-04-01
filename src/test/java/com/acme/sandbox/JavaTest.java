package com.acme.sandbox;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class JavaTest {

  @Test
  public void itShouldNotRemoveObjectNotInMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("a", "a");
    map.put("b", "b");

    map.remove("c");
    assertEquals(2, map.size());
  }

  @Test
  public void itShouldReturnNullForKeyNotInMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("a", "a");
    map.put("b", "b");
    assertEquals(null, map.get("c"));
  }

  @Test
  public void itShouldGetTheFullyQualifiedClassName() {
    assertEquals("com.acme.sandbox.JavaTest", this.getClass().getCanonicalName());
  }

  @Test
  public void itShouldNotAcceptSubTypes() {

  }

  @SuppressWarnings("unused")
  private <T> void genericMethod(T x, T y) {
    assertTrue(x.getClass().equals(y.getClass()));
  }
}
