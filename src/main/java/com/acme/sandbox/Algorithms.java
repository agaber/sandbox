package com.acme.sandbox;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public final class Algorithms {
  public static <T extends Comparable<T>> void bubbleSort(List<T> unsorted) {
    for (int i = unsorted.size() - 1; i > 0; i--)
      for (int j = 0; j < i; j++)
        if (unsorted.get(j).compareTo(unsorted.get(j + 1)) > 0)
          swap(unsorted, j, j + 1);
  }

  @SafeVarargs
  public static <T extends Comparable<T>> Set<T> intersection(Set<T>... sets) {
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
    Set<T> result = Sets.newTreeSet();
    for(Collection<T> set : sets)
      result.addAll(set);
    return ImmutableSet.copyOf(result);
  }

  private static <T> void swap(List<T> list, int i, int j) {
    T swap = list.get(i);
    list.set(i, list.get(j));
    list.set(j, swap);
  }
}
