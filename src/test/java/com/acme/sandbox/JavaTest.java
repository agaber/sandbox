package com.acme.sandbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class JavaTest {
  private int count = 0;

  @Before
  public void beforeEach() throws Exception {
    this.count = 0;
  }

  @Test
  public void messageformat() throws Exception {
	System.out.println(MessageFormat.format(
		"ID do trabalho: {0}[new-line-dont-translate][new-line-dont-translate]ID da "
		+ "campanha: {1}[new-line-dont-translate][new-line-dont-translate]Nome da "
		+ "campanha: {2}[new-line-dont-translate][new-line-dont-translate]Enviado "
		+ "por: {3}[new-line-dont-translate][new-line-dont-translate]Enviado em: "
		+ "{4,date,aaaa/MM/dd HH:mm z}[new-line-dont-translate][new-line-dont-translate]"
		+ "Status: processamento concludo.",
		"1", "2", "3", new Date()));
  }

  @Test
  public void nullShouldNotBeInstanceOfString() throws Exception {
    Object x = "ok";
    assertTrue(x instanceof String);

    Object y = null;
    assertFalse(y instanceof String);
  }

  @Test
  public void itShouldNotRemoveObjectNotInMap() throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    map.put("a", "a");
    map.put("b", "b");

    map.remove("c");
    assertEquals(2, map.size());
  }

  @Test
  public void itShouldReturnNullForKeyNotInMap() throws Exception {
    Map<String, String> map = new HashMap<String, String>();
    map.put("a", "a");
    map.put("b", "b");
    assertEquals(null, map.get("c"));
  }

  @Test
  public void itShouldGetTheFullyQualifiedClassName() throws Exception {
    assertEquals("com.acme.sandbox.JavaTest", this.getClass().getName());
    assertEquals("com.acme.sandbox.JavaTest", this.getClass().getCanonicalName());
  }

  @Test
  public void itShouldExecuteInEveryIteration() throws Exception {
    for (int i = 0; i < getStuff().size(); i++);

    // It will actually execute getStuff() every time.
    assertEquals(9, this.count);
  }

  @Test
  public void itShouldNotExecuteInEveryIteration() throws Exception {
    for (String x : getStuff()) { x += x; };
    assertEquals(1, this.count);
  }

  private List<String> getStuff() {
    count++;
    return Lists.newArrayList(Splitter.on(" ").split("a b c d e f g h"));
  }

//  private <T> void genericMethod(T x, T y) {
//    assertTrue(x.getClass().equals(y.getClass()));
//  }

  @Test
  public void itShouldUseReference() {
    Car carA = new Car("toyota");
    Car carB = carA;
    assertSame(carA, carB);

    carA = new Car("ford");
    assertNotSame(carA, carB);
  }
}
