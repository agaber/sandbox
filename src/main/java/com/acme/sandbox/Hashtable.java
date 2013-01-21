package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class Hashtable<K, V> {
  private int capacity;
  private Object[] values;

  public Hashtable() {
    this(100);
  }

  public Hashtable(int initialCapacity) {
    checkArgument(initialCapacity > 0, "the initialCapacity must be greater than zero");
    this.capacity = initialCapacity;
    this.values = new Object[capacity];
  }

  public V get(final K key) {
    List<V> bucket = getBucket(key);

    // TODO: How do we deal with collisions?
    for (V v : bucket) {
      return v;
    }

    return null;
  }

  // TODO
  public boolean containsKey(K k) {
    return false;
  }

  // TODO
  public boolean containsValue(V value) {
    return false;
  }

  // TODO
  public boolean isEmpty() {
    return true;
  }

  public void put(final K key, final V value) {
    List<V> bucket = getBucket(key);
    bucket.add(value);
  }

  // TODO
  public V remove(K key) {
    return null;
  }

  // TODO
  public int size() {
    return 0;
  }

  @SuppressWarnings("unchecked")
  private List<V> getBucket(final K key) {
    checkNotNull(key, "key must not be null");
    int index = key.hashCode() % capacity;
    List<V> bucket = (List<V>) values[index];

    if (bucket == null) {
      values[index] = bucket = Lists.newArrayList();
    }

    return bucket;
  }
}
