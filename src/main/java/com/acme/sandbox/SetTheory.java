package com.acme.sandbox;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public final class SetTheory {

  @SafeVarargs
  public static <T extends Comparable<T>> Collection<T> intersection(Collection<T>... sets) {
    if (sets.length == 0) {
      return ImmutableSet.of();
    }

    Set<T> result = Sets.newTreeSet(sets[0]);
    for (int i = 1; i < sets.length; i++) {
      Set<T> set = ImmutableSet.copyOf(sets[i]);
      for (T value : ImmutableSet.copyOf(result))
        if (!set.contains(value))
          result.remove(value);
    }
    return ImmutableSet.copyOf(result);
  }

  @SafeVarargs
  public static <T extends Comparable<T>> Collection<T> union(Collection<T>... sets) {
    Set<T> result = Sets.newTreeSet();
    for(Collection<T> set : sets)
      result.addAll(set);
    return ImmutableSet.copyOf(result);
  }

  @SafeVarargs
  public static <T extends Comparable<T>> T[] union(Class<T> type, T[]... sets) {
    Set<T> result = Sets.newTreeSet();
    for (T[] set : sets)
      for (T o : set)
        result.add(o);
    @SuppressWarnings("unchecked")
    T[] array = (T[]) Array.newInstance(type, result.size());
    return result.toArray(array);
  }
}
