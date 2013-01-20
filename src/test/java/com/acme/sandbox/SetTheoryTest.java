package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

public class SetTheoryTest {
  @Test
  public void intersection() throws Exception {
    Set<Integer> a = Sets.newHashSet(1, 2, 3, 3, 3, 4);
    Set<Integer> b = Sets.newHashSet(2, 3, 1, 4, 3, 7);
    Set<Integer> c = Sets.newHashSet(1, 4, 7, 7);
    Set<Integer> result = SetTheory.intersection(a, b, c);
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

    Set<Integer> a = Sets.newHashSet(1, 2, 3, 3, 3, 4);
    Set<Integer> result = SetTheory.intersection(a);
    assertArrayEquals(
        "Should handle intersection of one set.",
        new Integer[] { 1, 2, 3, 4 },
        result.toArray());
  }

  @Test
  public void union() throws Exception {
    Set<Integer> a = Sets.newHashSet(1, 2, 3, 3);
    Set<Integer> b = Sets.newHashSet(2, 3, 1, 4);
    Set<Integer> c = Sets.newHashSet(4, 5, 5, 5, 7);
    Set<Integer> result = SetTheory.union(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4, 5, 7 },
        result.toArray());
  }

  @Test
  public void unionStrings() throws Exception {
    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> b = Sets.newHashSet("d", "e", "a");
    Set<String> result = SetTheory.union(a, b);
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

    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> result = SetTheory.union(a);
    assertArrayEquals(
        "Should handle union of one set.",
        new String[] { "a", "b", "c" },
        result.toArray());
  }
}
