package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public final class Algorithms {
  public static <T extends Comparable<T>> void bubbleSort(List<T> list) {
    checkNotNull(list, "list parameter can't be null");
    for (int i = list.size() - 1; i > 0; i--)
      for (int j = 0; j < i; j++)
        if (list.get(j).compareTo(list.get(j + 1)) > 0)
          swap(list, j, j + 1);
  }

  public static <T extends Comparable<T>> void quickSort(List<T> list) {
    checkNotNull(list, "list parameter can't be null");
    quickSort(list, 0, list.size() - 1);
  }

  private static <T extends Comparable<T>> void quickSort(List<T> list, int first, int last) {
    if (first >= last) return;
    int pivot = partition(list, first, last);
    quickSort(list, first, pivot);
    quickSort(list, pivot + 1, last);
  }

  private static <T extends Comparable<T>> int partition(List<T> list, int first, int last) {
    T pivot = list.get(first);
    while (first < last) {
      while (list.get(first).compareTo(pivot) < 0) first++;
      while (list.get(last).compareTo(pivot) > 0) last--;
      swap(list, first, last);
    }
    return first;
  }

  private static <T> void swap(List<T> list, int i, int j) {
    T swap = list.get(i);
    list.set(i, list.get(j));
    list.set(j, swap);
  }

  @SafeVarargs
  public static <T extends Comparable<T>> Set<T> intersection(Set<T>... sets) {
    checkNotNull(sets, "sets parameter can't be null");
    if (sets.length == 0)
      return ImmutableSet.of();

    Set<T> result = Sets.newTreeSet(sets[0]);
    for (int i = 1; i < sets.length; i++)
      for (T value : ImmutableSet.copyOf(result))
        if (!sets[i].contains(value))
          result.remove(value);
    return ImmutableSet.copyOf(result);
  }

  @SafeVarargs
  public static <T extends Comparable<T>> Set<T> union(Set<T>... sets) {
    checkNotNull(sets, "sets parameter can't be null");
    Set<T> result = Sets.newTreeSet();
    for(Collection<T> set : sets)
      result.addAll(set);
    return ImmutableSet.copyOf(result);
  }

  public static BigInteger factorial(BigInteger n) {
    return n.compareTo(BigInteger.ONE) >= 0
        ? factorial(n.subtract(BigInteger.ONE)).multiply(n)
        : BigInteger.ONE;
  }

  public static BigInteger factorial(long n) {
    return factorial(BigInteger.valueOf(n));
  }

  public static long fibonacci(long n) {
    if (n < 1) {
      return 0;
    }
    return n < 3 ? 1 : fibonacci(n - 1) + fibonacci(n - 2);
  }

  public static long binaryToDecimal(String binary) {
    checkArgument(
        binary.matches("^[0-1]*$"),
        "String parameter must be a binary value.");
    long decimal = 0;
    int two = 1;
    for (int i = binary.length() - 1; i >= 0; i--) {
      decimal += Byte.parseByte(String.valueOf(binary.charAt(i))) * two;
      two *= 2;
    }
    return decimal;
  }

  /**
   * Returns true if the given array of numbers has 180 degrees of rotation
   * symmetry. For example, 16891 is the same if you rotated it 180°.
   */
  public static boolean isSymmetric(int... numbers) {
    checkArgument(numbers.length > 0);
    Map<Integer, Integer> symmetryMap = ImmutableMap.of(
        0, 0,
        1, 1,
        6, 9,
        8, 8,
        9, 6);

    for (int i = 0, j = numbers.length - 1; i < numbers.length && i <= j; i++, j--) {
      int a = numbers[i];
      int b = numbers[j];
      if (!symmetryMap.containsKey(a) || b != symmetryMap.get(a)) {
        return false;
      }
    }

    return true;
  }

  private Algorithms() {}
}
