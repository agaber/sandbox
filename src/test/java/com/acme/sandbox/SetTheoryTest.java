package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;

import java.util.Collection;

import org.junit.Test;

import com.google.common.collect.Lists;

public class SetTheoryTest {

  @Test
  public void union() {
    Collection<Integer> a = Lists.newArrayList(1, 2, 3, 3);
    Collection<Integer> b = Lists.newArrayList(2, 3, 1, 4);
    Collection<Integer> result = SetTheory.union(a, b);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4 },
        result.toArray());
  }

  @Test
  public void unionStrings() {
    Collection<String> a = Lists.newArrayList("a", "b", "c");
    Collection<String> b = Lists.newArrayList("d", "e", "a");
    Collection<String> result = SetTheory.union(a, b);
    assertArrayEquals(
        new String[] { "d", "e", "b", "c", "a" },
        result.toArray());
  }

  @Test
  public void intersection() {
    Collection<Integer> a = Lists.newArrayList(1, 2, 3, 3, 3);
    Collection<Integer> b = Lists.newArrayList(2, 3, 1, 4, 3);
    Collection<Integer> result = SetTheory.intersection(a, b);
    assertArrayEquals(
        new Integer[] { 1, 2, 3 },
        result.toArray());
  }
}
