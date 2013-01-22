package com.acme.sandbox;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import com.google.common.collect.Lists;

public class Hashtable<K, V> {
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

  public Hashtable() {
    this(100);
  }

  public Hashtable(int initialCapacity) {
    checkArgument(initialCapacity > 0, "the initialCapacity must be greater than zero");
    this.capacity = initialCapacity;
    this.values = new Object[capacity];
  }

  public V get(final K key) {
    List<Node<K, V>> bucket = getBucket(key);

    for (Node<K, V> node : bucket)
      if (node.key.equals(key))
        return node.value;

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

  public boolean isEmpty() {
    return size == 0;
  }

  public void put(final K key, final V value) {
    List<Node<K, V>> bucket = getBucket(key);
    Node<K, V> node = new Node<K, V>(key, value);
    if (!bucket.contains(node)) {
      bucket.add(node);
      size++;
    } else {
      bucket.remove(node);
      bucket.add(node);
    }
  }

  public V remove(K key) {
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

  public int size() {
    return size;
  }

  @SuppressWarnings("unchecked")
  private List<Node<K, V>> getBucket(final K key) {
    checkNotNull(key, "key must not be null");
    int index = key.hashCode() % capacity;
    List<Node<K, V>> bucket = (List<Node<K, V>>) values[index];

    if (bucket == null) {
      values[index] = bucket = Lists.newArrayList();
    }

    return bucket;
  }
}
