package com.github.graph.ikhideifidon;

import java.util.*;

public class DepthFirstPaths<T extends Object & Comparable<T>> {
    private final Map<Graph.Vertex<T>, Boolean> marked;
    private final List<Graph.Vertex<T>> edgeTo;
    private final Graph.Vertex<T> source;

    public DepthFirstPaths(Graph<T> graph, Graph.Vertex<T> source) {
        marked = new HashMap<>();
        edgeTo = new LinkedList<>();
        this.source = source;
        dfs(graph, source);
    }

    private void dfs(Graph<T> graph, Graph.Vertex<T> start) {
        marked.put(start, true);
        for (Graph.Edge<T> edge : graph.neighbors(start)) {
            Graph.Vertex<T> vertex = edge.getTo();
            if (!marked.containsKey(vertex)) {
                marked.put(vertex, true);
                edgeTo.add(start);
                dfs(graph, vertex);
            }
        }
    }

    public boolean hasPathTo(Graph.Vertex<T> destination) {
        return marked.get(destination);
    }

    public Iterable<Graph.Vertex<T>> pathTo(Graph.Vertex<T> destination) {
        if (!hasPathTo(destination)) return null;
        Deque<Graph.Vertex<T>> path = new LinkedList<>();
        for (Graph.Vertex<T> vertex : edgeTo) {
            if (vertex != source)
                path.push(vertex);
            path.push(source);
        }
        return path;
    }

}
