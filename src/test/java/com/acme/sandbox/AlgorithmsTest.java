package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AlgorithmsTest {
  @Test
  public void bubbleSort() throws Exception {
    List<Integer> list = Lists.newArrayList(3, 20, 10, 2, 1, 5);
    Algorithms.bubbleSort(list);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 5, 10, 20 },
        list.toArray());
  }

  @Test
  public void intersection() throws Exception {
    Set<Integer> a = Sets.newHashSet(3, 1, 3, 3, 2, 4);
    Set<Integer> b = Sets.newHashSet(2, 3, 7, 4, 1, 3);
    Set<Integer> c = Sets.newHashSet(1, 7, 7, 4);
    Set<Integer> result = Algorithms.intersection(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 4 },
        result.toArray());
  }

  @Test
  public void intersectionNone() throws Exception {
    assertArrayEquals(
        "Should handle intersection of empty sets.",
        new String[] { },
        Algorithms.<String>intersection().toArray());

    Set<Integer> a = Sets.newHashSet(1, 4, 3, 3, 3, 2);
    Set<Integer> result = Algorithms.intersection(a);
    assertArrayEquals(
        "Should handle intersection of one set.",
        new Integer[] { 1, 2, 3, 4 },
        result.toArray());
  }

  @Test
  public void union() throws Exception {
    Set<Integer> a = Sets.newHashSet(3, 1, 2, 3);
    Set<Integer> b = Sets.newHashSet(4, 3, 1, 2);
    Set<Integer> c = Sets.newHashSet(5, 7, 5, 5, 4);
    Set<Integer> result = Algorithms.union(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4, 5, 7 },
        result.toArray());
  }

  @Test
  public void unionStrings() throws Exception {
    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> b = Sets.newHashSet("d", "e", "a");
    Set<String> result = Algorithms.union(a, b);
    assertArrayEquals(
        new String[] { "a", "b", "c", "d", "e" },
        result.toArray());
  }

  @Test
  public void unionNone() throws Exception {
    assertArrayEquals(
        "Should handle union of empty sets.",
        new String[] { },
        Algorithms.<String>union().toArray());

    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> result = Algorithms.union(a);
    assertArrayEquals(
        "Should handle union of one set.",
        new String[] { "a", "b", "c" },
        result.toArray());
  }
}
