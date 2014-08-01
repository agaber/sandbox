package com.acme.sandbox;

import static com.acme.sandbox.Algorithms.bubbleSort;
import static com.acme.sandbox.Algorithms.intersection;
import static com.acme.sandbox.Algorithms.quickSort;
import static com.acme.sandbox.Algorithms.union;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
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


  @Test
  public void testFactorial() throws Exception {
    assertEquals(120, Algorithms.factorial(new BigInteger("5")).intValue());
    assertEquals(BigInteger.valueOf(1), Algorithms.factorial(1));
    assertEquals(BigInteger.valueOf(2), Algorithms.factorial(2));
    assertEquals(BigInteger.valueOf(6), Algorithms.factorial(3));
    assertEquals(BigInteger.valueOf(120), Algorithms.factorial(5));
    assertEquals(BigInteger.valueOf(720), Algorithms.factorial(6));
    assertEquals(
        new BigInteger("933262154439441526816992388562667004907159682643816214"
            + "6859296389521759999322991560894146397615651828625369792082722375"
            + "8251185210916864000000000000000000000000"),
        Algorithms.factorial(100));
  }

  @Test
  public void testFibonacci() throws Exception {
    long[] expected = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377,};
    for (int i = 0; i < expected.length; i++) {
      assertEquals(expected[i], Algorithms.fibonacci(i));
    }
  }

  @Test
  public void testBinaryToDecimal() throws Exception {
    assertEquals(1, Algorithms.binaryToDecimal("1"));
    assertEquals(2, Algorithms.binaryToDecimal("10"));
    assertEquals(3, Algorithms.binaryToDecimal("11"));
    assertEquals(4, Algorithms.binaryToDecimal("100"));
    assertEquals(8, Algorithms.binaryToDecimal("1000"));
    assertEquals(16, Algorithms.binaryToDecimal("10000"));
    assertEquals(32, Algorithms.binaryToDecimal("100000"));
    assertEquals(64, Algorithms.binaryToDecimal("1000000"));
    assertEquals(429, Algorithms.binaryToDecimal("110101101"));
  }

  @Test
  public void binaryToDecimalShouldRequireBinaryValues() throws Exception {
    try {
      Algorithms.binaryToDecimal("3");
      fail("Expected an error");
    } catch (IllegalArgumentException e) {
      // good.
    }

    try {
      Algorithms.binaryToDecimal("abc");
      fail("Expected an error");
    } catch (IllegalArgumentException e) {
      // good.
    }
  }

  @Test
  public void isSymmetric() throws Exception {
    assertTrue(Algorithms.isSymmetric(0));
    assertTrue(Algorithms.isSymmetric(1));
    assertTrue(Algorithms.isSymmetric(8));

    assertTrue(Algorithms.isSymmetric(1, 8, 1));
    assertTrue(Algorithms.isSymmetric(6, 8, 9));
    assertTrue(Algorithms.isSymmetric(6, 9));
    assertTrue(Algorithms.isSymmetric(8, 8));
    assertTrue(Algorithms.isSymmetric(1, 1));
    assertTrue(Algorithms.isSymmetric(8, 8, 8));
    assertTrue(Algorithms.isSymmetric(1, 6, 8, 9, 1));
    assertTrue(Algorithms.isSymmetric(8, 1, 8));

    assertFalse(Algorithms.isSymmetric(2));
    assertFalse(Algorithms.isSymmetric(3));
    assertFalse(Algorithms.isSymmetric(4));
    assertFalse(Algorithms.isSymmetric(5));
    assertFalse(Algorithms.isSymmetric(6));
    assertFalse(Algorithms.isSymmetric(7));
    assertFalse(Algorithms.isSymmetric(9));
    assertFalse(Algorithms.isSymmetric(6, 7));
    assertFalse(Algorithms.isSymmetric(6, 7, 9));
    assertFalse(Algorithms.isSymmetric(4, 8, 4));
    assertFalse(Algorithms.isSymmetric(8, 5, 8));
    assertFalse(Algorithms.isSymmetric(9, 9));
  }
}
