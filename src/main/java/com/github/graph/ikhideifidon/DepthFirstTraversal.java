package com.github.graph.ikhideifidon;

import java.util.*;

public class DepthFirstTraversal {

    public static <T extends Object & Comparable<T>> T[] depthFirstSearch(Graph<T> graph, Graph.Vertex<T> source) {
        final List<Graph.Vertex<T>> vertices = new ArrayList<>(graph.getAllVertices());
        final Deque<Graph.Vertex<T>> stack = new LinkedList<>();
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) new Object[vertices.size()];
        final Set<T> visited = new HashSet<>();
        stack.push(source);

        int i = 0;
        while (!stack.isEmpty()) {
            Graph.Vertex<T> current = stack.pop();

            if (!visited.contains(current.getValue())) {
                visited.add(current.getValue());
                result[i++] = current.getValue();
                for (Graph.Edge<T> edge : current.getEdges())
                    stack.push(edge.getTo());
            }
        }
        return result;
    }

}
