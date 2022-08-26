package com.github.graph.ikhideifidon;

import java.util.*;

public class BreadthFirstTraversal {
    public static <T extends Object & Comparable<T>> T[] breadthFirstSearch(Graph<T> graph, Graph.Vertex<T> source) {
        List<Graph.Vertex<T>> vertices = new ArrayList<>(graph.getAllVertices());
        Set<T> visited = new HashSet<>();
        Deque<Graph.Vertex<T>> queue = new LinkedList<>();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[vertices.size()];
        queue.offer(source);

        int i = 0;
        while (!queue.isEmpty()) {
            Graph.Vertex<T> current = queue.poll();

            if (!visited.contains(current.getValue())) {
                visited.add(current.getValue());
                result[i++] = current.getValue();

                for (Graph.Edge<T> edge : current.getEdges())
                    queue.offer(edge.getTo());
            }
        }
        return result;
    }
}
