package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkNotNull;

import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

  private Algorithms() {}
}
