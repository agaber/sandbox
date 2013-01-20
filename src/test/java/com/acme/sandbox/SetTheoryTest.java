package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.Lists;

public class SetTheoryTest {
  @Test
  public void intersection() throws Exception {
    Collection<Integer> a = Lists.newArrayList(1, 2, 3, 3, 3, 4);
    Collection<Integer> b = Lists.newArrayList(2, 3, 1, 4, 3, 7);
    Collection<Integer> c = Lists.newArrayList(1, 4, 7, 7);
    Collection<Integer> result = SetTheory.intersection(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 4 },
        result.toArray());
  }

  @Test
  public void intersectionNone() throws Exception {
    assertArrayEquals(
        "Should handle intersection of empty sets.",
        new String[] { },
        SetTheory.<String>intersection().toArray());

    Collection<Integer> a = Lists.newArrayList(1, 2, 3, 3, 3, 4);
    Collection<Integer> result = SetTheory.intersection(a);
    assertArrayEquals(
        "Should handle intersection of one set.",
        new Integer[] { 1, 2, 3, 4 },
        result.toArray());
  }

  @Test
  public void union() throws Exception {
    Collection<Integer> a = Lists.newArrayList(1, 2, 3, 3);
    Collection<Integer> b = Lists.newArrayList(2, 3, 1, 4);
    Collection<Integer> c = Lists.newArrayList(4, 5, 5, 5, 7);
    Collection<Integer> result = SetTheory.union(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4, 5, 7 },
        result.toArray());
  }

  @Test
  public void unionStrings() throws Exception {
    Collection<String> a = Lists.newArrayList("a", "b", "c");
    Collection<String> b = Lists.newArrayList("d", "e", "a");
    Collection<String> result = SetTheory.union(a, b);
    assertArrayEquals(
        new String[] { "a", "b", "c", "d", "e" },
        result.toArray());
  }

  @Test
  public void unionNone() throws Exception {
    assertArrayEquals(
        "Should handle union of empty sets.",
        new String[] { },
        SetTheory.<String>union().toArray());

    Collection<String> a = Lists.newArrayList("a", "b", "c");
    Collection<String> result = SetTheory.union(a);
    assertArrayEquals(
        "Should handle union of one set.",
        new String[] { "a", "b", "c" },
        result.toArray());
  }

  @Test
  public void unionArrays() throws Exception {
    Integer[] a = new Integer[] { 1, 2, 3, 4, 20 };
    Integer[] b = new Integer[] { 1, 4, 4 };
    Integer[] result = SetTheory.union(Integer.class, a, b);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4, 20 },
        result);
  }
}
