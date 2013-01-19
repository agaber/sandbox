package com.acme.sandbox;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

public final class SetTheory {

  @SafeVarargs
  public static <T> Collection<T> union(Collection<T>... sets) {
    Set<T> result = Sets.newHashSet();

    for(Collection<T> set : sets)
      for(T value : set)
        if(!result.contains(value))
          result.add(value);

    return ImmutableSet.copyOf(result);
  }

  @SafeVarargs
  public static <T> Collection<T> intersection(Collection<T>... sets) {
    Set<T> result = Sets.newHashSet();
    Set<T> allValues = Sets.newHashSet();

    for(Collection<T> set : sets) {
      for(T value : set) {
        if(allValues.contains(value))
          result.add(value);
        allValues.add(value);
      }
    }

    return ImmutableSet.copyOf(result);
  }
}
