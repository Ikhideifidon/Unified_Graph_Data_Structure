package com.github.graph.ikhideifidon;

import java.util.*;

public class DepthFirstTraversal {

    public static <T extends Object & Comparable<T>> T[] depthFirstSearch(Graph<T> graph, Graph.Vertex<T> source) {
        final List<Graph.Vertex<T>> vertices = new ArrayList<>(graph.getAllVertices());
        final Deque<Graph.Vertex<T>> stack = new LinkedList<>();
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) new Object[vertices.size()];
        final Set<Graph.Vertex<T>> visited = new HashSet<>();
        stack.push(source);

        int i = 0;
        while (!stack.isEmpty()) {
            Graph.Vertex<T> current = stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);
                result[i++] = current.getValue();
                for (Graph.Edge<T> edge : current.getEdges())
                    stack.push(edge.getTo());
            }
        }
        return result;
    }

    private static <T extends Object & Comparable<T>> T[] helper(Graph<T> graph, Graph.Vertex<T> source, Set<Graph.Vertex<T>> visited, T[] result, int index) {
        visited.add(source);
        result[index] = source.getValue();
        for (Graph.Edge<T> edge : graph.neighbors(source)) {
            Graph.Vertex<T> vertex = edge.getTo();
            if (!visited.contains(vertex)) {
                helper(graph, vertex, visited, result, index + 1);
            }
        }
        return result;
    }

    public static <T extends Object & Comparable<T>> T[] depthFirstSearchRecursively(Graph<T> graph, Graph.Vertex<T> source) {
        Set<Graph.Vertex<T>> visited = new HashSet<>();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[graph.getAllVertices().size()];
        return helper(graph, source, visited, result, 0);
    }




    }
