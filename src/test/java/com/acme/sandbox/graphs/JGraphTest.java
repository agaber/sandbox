package com.acme.sandbox.graphs;

import java.util.List;

import static org.junit.Assert.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DirectedMaskSubgraph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.graph.MaskFunctor;
import org.jgrapht.graph.MaskSubgraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JGraphTest {

  @Test
  public void graphTest() throws Exception {
    DirectedGraph<String, Edge> graph = new DirectedMultigraph<>(Edge.class);

    // add the vertices
    graph.addVertex("v1");
    graph.addVertex("v2");
    graph.addVertex("v3");
    graph.addVertex("v4");

    // add edges to create a circuit
    graph.addEdge("v1", "v2", new Edge("v1", "v2", "->"));
    graph.addEdge("v2", "v3", new Edge("v2", "v3", "->"));
    graph.addEdge("v3", "v4", new Edge("v3", "v4", "->"));
    graph.addEdge("v4", "v1", new Edge("v4", "v1", "->"));

    MaskSubgraph<String, Edge> subgraph = new DirectedMaskSubgraph<>(graph, new Mask("->"));
    GraphIterator<String, Edge> iterator = new DepthFirstIterator<>(subgraph, "v3");
    List<String> path = Lists.newArrayList();
    while (iterator.hasNext()) {
      path.add(iterator.next());
    }
    assertArrayEquals(new String[] {"v3", "v4", "v1", "v2" }, path.toArray());
  }

  @Test
  public void undirectedWithDirected() throws Exception {
    DirectedGraph<String, Edge> graph = new DirectedMultigraph<>(Edge.class);

    // add the vertices
    graph.addVertex("v1");
    graph.addVertex("v2");
    graph.addVertex("v3");
    graph.addVertex("v4");

    // add edges to create a circuit
    graph.addEdge("v1", "v2", new Edge("v1", "v2", "->"));
    graph.addEdge("v2", "v3", new Edge("v2", "v3", "->"));
    graph.addEdge("v3", "v2", new Edge("v3", "v2", "->"));

    MaskSubgraph<String, Edge> subgraph = new DirectedMaskSubgraph<>(graph, new Mask("->"));
    GraphIterator<String, Edge> iterator = new DepthFirstIterator<>(subgraph, "v2");
    List<String> path = Lists.newArrayList();
    while (iterator.hasNext()) {
      path.add(iterator.next());
    }
    assertArrayEquals(new String[] {"v2", "v3" }, path.toArray());
  }

  public class Edge {
    public final String head;
    public final String tail;
    public final String type;

    public Edge(String head, String tail, String type) {
      this.head = head;
      this.tail = tail;
      this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
      return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
      return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
      return String.format("%s %s %s", head, tail, type);
    }
  }

  private static class Mask implements MaskFunctor<String, Edge> {
    private final String mask;

    public Mask(String string) {
      this.mask = string;
    }

    @Override
    public boolean isEdgeMasked(Edge edge) {
      return !mask.equals(edge.type);
    }

    @Override
    public boolean isVertexMasked(String vertex) {
      return false;
    }
  }
}
