package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;

/**
 * My own implementation of {@link Map}, for fun.
 *
 * @param <K> key type
 * @param <V> value type
 */
public class Dictionary<K, V> implements Map<K, V> {
  private static class Node<K, V> {
    public final K key;
    public final V value;

    public Node(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof Node && key.equals(((Node<?, ?>) o).key);
    }
  }

  private int capacity;
  private int size;
  private Object[] values;

  public Dictionary() {
    this(100);
  }

  public Dictionary(int initialCapacity) {
    checkArgument(initialCapacity > 0, "the initialCapacity must be greater than zero");
    this.capacity = initialCapacity;
    this.values = new Object[capacity];
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    // TODO
    return null;
  }

  @Override
  public void clear() {
    // TODO
  }

  @Override
  public boolean containsKey(Object key) {
    // TODO
    return false;
  }

  @Override
  public boolean containsValue(Object value) {
    // TODO
    return false;
  }

  @Override
  public V get(Object key) {
    List<Node<K, V>> bucket = getBucket(key);

    for (Node<K, V> node : bucket)
      if (node.key.equals(key))
        return node.value;

    return null;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }


  @Override
  public Set<K> keySet() {
    // TODO
    return null;
  }

  @Override
  public V put(final K key, final V value) {
    List<Node<K, V>> bucket = getBucket(key);
    Node<K, V> node = new Node<K, V>(key, value);
    if (!bucket.contains(node)) {
      bucket.add(node);
      size++;
    } else {
      bucket.remove(node);
      bucket.add(node);
    }

    return value;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    // TODO
  }

  @Override
  public V remove(Object key) {
    List<Node<K, V>> bucket = getBucket(key);
    Node<K, V> found = null;

    for (Node<K, V> node : bucket) {
      if (node.key.equals(key)) {
        found = node;
        break;
      }
    }

    if (found != null && bucket.remove(found)) {
      size--;
      return found.value;
    }

    return null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Collection<V> values() {
    // TODO
    return null;
  }

  @SuppressWarnings("unchecked")
  private List<Node<K, V>> getBucket(final Object key) {
    checkNotNull(key, "key must not be null");
    int index = key.hashCode() % capacity;
    List<Node<K, V>> bucket = (List<Node<K, V>>) values[index];

    if (bucket == null) {
      values[index] = bucket = Lists.newArrayList();
    }

    return bucket;
  }
}
