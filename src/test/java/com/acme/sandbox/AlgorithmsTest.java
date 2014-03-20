package com.acme.sandbox;

import static org.junit.Assert.assertArrayEquals;
import static com.acme.sandbox.Algorithms.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AlgorithmsTest {
  @Test
  public void shouldBubbleSort() throws Exception {
    List<Integer> list = Lists.newArrayList(3, 20, 10, 2, 1, 5);
    bubbleSort(list);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 5, 10, 20 },
        list.toArray());
  }

  @Test
  public void shouldQuickSort() throws Exception {
    List<Integer> list = Lists.newArrayList(3, 20, 10, 2, 1, 5);
    quickSort(list);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 5, 10, 20 },
        list.toArray());
  }

  @Test
  public void shouldFindIntersection() throws Exception {
    Set<Integer> a = Sets.newHashSet(3, 1, 3, 3, 2, 4);
    Set<Integer> b = Sets.newHashSet(2, 3, 7, 4, 1, 3);
    Set<Integer> c = Sets.newHashSet(1, 7, 7, 4);
    Set<Integer> result = intersection(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 4 },
        result.toArray());
  }

  @Test
  public void shouldFindEmptyIntersection() throws Exception {
    assertArrayEquals(
        "Should handle intersection of empty sets.",
        new String[] { },
        intersection().toArray());

    Set<Integer> a = Sets.newHashSet(1, 4, 3, 3, 3, 2);
    Set<Integer> result = intersection(a);
    assertArrayEquals(
        "Should handle intersection of one set.",
        new Integer[] { 1, 2, 3, 4 },
        result.toArray());
  }

  @Test
  public void shouldFindUnion() throws Exception {
    Set<Integer> a = Sets.newHashSet(3, 1, 2, 3);
    Set<Integer> b = Sets.newHashSet(4, 3, 1, 2);
    Set<Integer> c = Sets.newHashSet(5, 7, 5, 5, 4);
    Set<Integer> result = union(a, b, c);
    assertArrayEquals(
        new Integer[] { 1, 2, 3, 4, 5, 7 },
        result.toArray());
  }

  @Test
  public void shouldFindUnionOfStrings() throws Exception {
    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> b = Sets.newHashSet("d", "e", "a");
    Set<String> result = union(a, b);
    assertArrayEquals(
        new String[] { "a", "b", "c", "d", "e" },
        result.toArray());
  }

  @Test
  public void shouldFindEmptyUnion() throws Exception {
    assertArrayEquals(
        "Should handle union of empty sets.",
        new String[] { },
        union().toArray());

    Set<String> a = Sets.newHashSet("a", "b", "c");
    Set<String> result = union(a);
    assertArrayEquals(
        "Should handle union of one set.",
        new String[] { "a", "b", "c" },
        result.toArray());
  }
}
