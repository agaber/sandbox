package com.acme.sandbox.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UndirectedGraph<T> implements Iterable<T> {
  private final Map<T, Set<T>> graph;

  public UndirectedGraph() {
    graph = new HashMap<>();
  }

  public boolean addVertex(T vertex) {
    if (graph.containsKey(vertex)) {
      return false;
    }
    graph.put(vertex, new HashSet<T>());
    return true;
  }

  public void addEdge(T start, T dest) {
    Set<T> startVertex = graph.get(start);
    Set<T> destVertex = graph.get(dest);

    if (startVertex == null || destVertex == null) {
      throw new IllegalStateException("No such nodes in the graph.");
    }

    startVertex.add(dest);
    destVertex.add(start);
  }

  @Override
  public Iterator<T> iterator() {
    return null;
  }
}
