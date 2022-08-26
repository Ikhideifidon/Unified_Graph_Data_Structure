package com.github.graph.ikhideifidon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final List<Graph.Vertex<Integer>> vertices = new ArrayList<>();
        final Graph.Vertex<Integer> v1 = new Graph.Vertex<>(1);
        final Graph.Vertex<Integer> v2 = new Graph.Vertex<>(2);
        final Graph.Vertex<Integer> v3 = new Graph.Vertex<>(3);
        final Graph.Vertex<Integer> v4 = new Graph.Vertex<>(4);
        final Graph.Vertex<Integer> v5 = new Graph.Vertex<>(5);
        final Graph.Vertex<Integer> v6 = new Graph.Vertex<>(6);
        final Graph.Vertex<Integer> v7 = new Graph.Vertex<>(7);
        final Graph.Vertex<Integer> v8 = new Graph.Vertex<>(8);
        final Graph.Vertex<Integer> v9 = new Graph.Vertex<>(9);
        {
            vertices.add(v1);
            vertices.add(v2);
            vertices.add(v3);
            vertices.add(v4);
            vertices.add(v5);
            vertices.add(v6);
            vertices.add(v7);
            vertices.add(v8);
            vertices.add(v9);
        }

        final List<Graph.Edge<Integer>> edges = new ArrayList<>();
        final Graph.Edge<Integer> e1_2 = new Graph.Edge<>(7, v1, v2);
        final Graph.Edge<Integer> e1_3 = new Graph.Edge<>(9, v1, v3);
        final Graph.Edge<Integer> e1_6 = new Graph.Edge<>(14, v1, v6);
        final Graph.Edge<Integer> e2_3 = new Graph.Edge<>(10, v2, v3);
        final Graph.Edge<Integer> e2_4 = new Graph.Edge<>(15, v2, v4);
        final Graph.Edge<Integer> e3_4 = new Graph.Edge<>(11, v3, v4);
        final Graph.Edge<Integer> e3_6 = new Graph.Edge<>(2, v3, v6);
        final Graph.Edge<Integer> e5_6 = new Graph.Edge<>(9, v5, v6);
        final Graph.Edge<Integer> e4_5 = new Graph.Edge<>(6, v4, v5);
        final Graph.Edge<Integer> e1_7 = new Graph.Edge<>(1, v1, v7);
        final Graph.Edge<Integer> e1_8 = new Graph.Edge<>(1, v1, v8);
        final Graph.Edge<Integer> e7_9 = new Graph.Edge<>(8, v7, v9);
        {
            edges.add(e1_2);
            edges.add(e1_3);
            edges.add(e1_6);
            edges.add(e2_3);
            edges.add(e2_4);
            edges.add(e3_4);
            edges.add(e3_6);
            edges.add(e5_6);
            edges.add(e4_5);
            edges.add(e1_7);
            edges.add(e1_8);
            edges.add(e7_9);
        }

        final Graph<Integer> graph = new Graph<>(vertices, edges);
        System.out.println(graph);
        System.out.println(Arrays.toString(DepthFirstTraversal.depthFirstSearch(graph, v1)));
        System.out.println(graph.neighbors(v4));
        System.out.println(Arrays.toString(BreadthFirstTraversal.breadthFirstSearch(graph, v1)));
        System.out.println(graph.numberOfSelfLoop());
        System.out.println(Arrays.toString(DepthFirstTraversal.depthFirstSearchRecursively(graph, v4)));
    }
}
