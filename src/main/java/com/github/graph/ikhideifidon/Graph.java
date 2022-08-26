package com.github.graph.ikhideifidon;

import java.util.*;

public class Graph<T extends Object & Comparable<T>> {

    private final List<Vertex<T>> allVertices = new LinkedList<>();
    private final List<Edge<T>> allEdges = new LinkedList<>();

    public enum TYPE {
        DIRECTED, UNDIRECTED,
    }

    /** Default type is UNDIRECTED. **/
    private TYPE type = TYPE.UNDIRECTED;

    /** An Undirected Graph without Vertex and Edge. **/
    public Graph() { }

    /** A type of Graph without Vertex and Edge whose direction is obtained at Runtime.. **/
    public Graph(TYPE type) { this.type = type; }

    /** A type Graph that is created from a collection of vertices and edges. **/
    public Graph(TYPE type, Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this(type);

        this.allVertices.addAll(vertices);
        this.allEdges.addAll(edges);

        for (Edge<T> edge : edges) {
            final Vertex<T> from = edge.from;
            final Vertex<T> to = edge.to;

            // The added vertex's connections might not be represented in
            // the Graph yet, so we implicitly add them

            // E.g. vertices = [A, B, C] and edges = [[A-B], [M-B]]. It's obvious that M (in edges)
            // is not yet present in the list of vertices. But since B is a valid vertex,
            // we therefore add vertex M to the list of vertices.  [A, B, C, M].
            if (!this.allVertices.contains(from) || !this.allVertices.contains(to))
                continue;

            from.addEdge(edge);
            if (this.type == TYPE.UNDIRECTED) {
                // If undirected, the 'to' vertex becomes the source and
                // the 'from' vertex becomes the destination.
                Edge<T> reciprocal = new Edge<>(edge.cost, to, from);
                to.addEdge(reciprocal);
                this.allEdges.add(reciprocal);
            }
        }

    }

    /** An Undirected Graph that is created from a collection of vertices and edges. **/
    public Graph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this(TYPE.UNDIRECTED, vertices, edges);
    }

    // Setters and Getters
    public TYPE getType() { return type; }

    public List<Vertex<T>> getAllVertices() { return allVertices; }

    public List<Edge<T>> getAllEdges() { return allEdges; }

    public List<Edge<T>> neighbors(Vertex<T> vertex) {
        if (vertex != null && getAllVertices().contains(vertex)) {
            for (Vertex<T> v : getAllVertices()) {
                if (v.equals(vertex))
                   return v.getEdges();
            }
        }
        return null;
    }

    public int degree(Vertex<T> vertex) {
        int degree = 0;
        if (vertex != null && getAllVertices().contains(vertex)) {
            for (Vertex<T> v : getAllVertices()) {
                if (v.equals(vertex))
                    return v.getEdges().size();
            }
        }
        return degree;
    }

    public int maximumDegree() {
        int maximumDegree = 0;
        for (Vertex<T> vertex : getAllVertices()) {
            if (vertex.getEdges().size() > maximumDegree)
                maximumDegree = vertex.getEdges().size();

        }
        return maximumDegree;
    }

    public int numberOfSelfLoop() {
        int numberOfSelfLoop = 0;
        for (Edge<T> edge : getAllEdges()) {
            if (edge.getTo().compareTo(edge.getFrom()) == 0)
                numberOfSelfLoop++;
        }
        return numberOfSelfLoop / 2;                    // Each edge is counted twice.
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        // This graph comparison is based on:
        // 1. type
        // 2. size of allVertices
        // 3. size of allEdges
        // 4. each vertex
        // 5. each edge

        if (!(o instanceof Graph<?> that))
            return false;

        final boolean equalType = this.getType() == that.getType();
        if (!equalType)
            return false;

        final boolean equalSizeAllVertices = this.getAllVertices().size() == that.getAllVertices().size();
        if (!equalSizeAllVertices)
            return false;

        final boolean equalSizeAllEdges = this.getAllEdges().size() == that.getAllEdges().size();
        if (!equalSizeAllEdges)
            return false;

        // Vertex Comparison
        final Object[] thisVertices = this.getAllVertices().toArray();
        final Object[] thatVertices = that.getAllVertices().toArray();
        Arrays.sort(thisVertices);
        Arrays.sort(thatVertices);

        for (int i = 0; i < thisVertices.length; i++) {
            final Vertex<T> thisVertex = (Vertex<T>) thisVertices[i];
            final Vertex<T> thatVertex = (Vertex<T>) thatVertices[i];
            // Each Vertex comparison
            if (!thisVertex.equals(thatVertex))
                return false;
        }

        // Edge Comparison
        final Object[] thisEdges = this.getAllEdges().toArray();
        final Object[] thatEdges = that.getAllEdges().toArray();
        Arrays.sort(thisEdges);
        Arrays.sort(thatEdges);

        for (int i = 0; i < thisEdges.length; i++) {
            final Edge<T> thisEdge = (Edge<T>) thisEdges[i];
            final Edge<T> thatEdge = (Edge<T>) thatEdges[i];
            // Each Edge comparison
            if (!thisEdge.equals(thatEdge))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = this.type.hashCode();
        result = 31 * result + Integer.hashCode(this.getAllVertices().size());
        result = 31 * result + Integer.hashCode(this.getAllEdges().size());

        for (Vertex<T> vertex : getAllVertices())
            result = 31 * result + vertex.hashCode();

        for (Edge<T> edge : getAllEdges())
            result = 31 * result + edge.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (Vertex<T> v : allVertices)
            builder.append(v.toString());
        return builder.toString();
    }

    public static class Vertex<T extends Object & Comparable<T>> implements Comparable<Vertex<T>> {

        // Instance Variables
        private T value = null;
        private int weight = 0;
        private final List<Edge<T>> edges = new ArrayList<>();  // An ArrayList of all edges that are incident to the vertex.

        // Constructors
        public Vertex(T value) {
            this.value = value;
        }

        public Vertex(T value, int weight) {
            this(value);
            this.weight = weight;
        }

        // Copy Constructor
        /** Deep copy the edges along with the value and the weight **/
        public Vertex(Vertex<T> vertex) {
            this(vertex.value, vertex.weight);
            this.edges.addAll(vertex.edges);
        }

        // Getters
        public T getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }

        public List<Edge<T>> getEdges() {
            return edges;
        }

        public Edge<T> getEdge(Vertex<T> vertex) {
            for (Edge<T> e : edges) {
                if (e.getTo().compareTo(vertex) == 0)
                    return e;
            }
            return null;
        }

        // Setter
        public void setWeight(int weight) {
            this.weight = weight;
        }

        public void addEdge(Edge<T> edge) {
            edges.add(edge);
        }

        /** Given a vertex, is there a direct path to that vertex? In other word, is there any incident edge at that vertex? **/
        public boolean pathTo(Vertex<T> vertex) {
            for (Edge<T> e : edges) {
                if (e.getTo().compareTo(vertex) == 0)
                    return true;
            }
            return false;
        }


        @Override
        @SuppressWarnings("rawtypes")
        public boolean equals(Object vertex) {
            if (!(vertex instanceof Vertex v1))
                return false;

            final boolean equalValue = v1.value == this.value;
            if (!equalValue)
                return false;

            final boolean equalSize = v1.getEdges().size() == this.getEdges().size();
            if (!equalSize)
                return false;

            final boolean equalWeight = v1.weight == this.weight;
            if (!equalWeight)
                return false;

            final Iterator<Edge<T>> iter1 = this.edges.iterator();
            @SuppressWarnings("unchecked")
            final Iterator<Edge<T>> iter2 = v1.edges.iterator();

            while(iter1.hasNext()) {
                final Edge<T> e1 = iter1.next();
                final Edge<T> e2 = iter2.next();
                // Compare the cost
                if (e1.cost != e2.cost)
                    return false;
            }
            return true;
        }

        // HashCode defined around a vertex.
        @Override
        public int hashCode() {
            int result = this.value.hashCode();
            result = 31 * result + Integer.hashCode(this.weight);
            result = 31 * result + Integer.hashCode(this.edges.size());
            return result;
        }



        @Override
        public int compareTo(Vertex<T> vertex) {
            // Compare all its instances.
            // Compare values
            final int value = this.getValue().compareTo(vertex.getValue());
            if (value != 0)
                return value;

            // Compare weights
            final int weight = Integer.compare(this.getWeight(), vertex.getWeight());
            if (weight != 0)
                return weight;

            // Compare sizes
            final int size = Integer.compare(this.getEdges().size(), vertex.getEdges().size());
            if (size != 0)
                return size;

            Iterator<Edge<T>> iter1 = this.getEdges().iterator();
            Iterator<Edge<T>> iter2 = vertex.getEdges().iterator();

            while (iter1.hasNext()) {
                Edge<T> e1 = iter1.next();
                Edge<T> e2 = iter2.next();
                // Compare cost
                if (e1.cost > e2.cost)
                    return 1;
                if (e1.cost < e2.cost)
                    return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("Value = ").append(value).append(" weight = ").append(weight).append("\n");
            for (Edge<T> e : edges)
                builder.append("\t\t\t").append(e.toString());
            return builder.toString();
        }
    }


    public static class Edge<T extends Object & Comparable<T>> implements Comparable<Edge<T>> {

        // Instance Variables
        private final Vertex<T> from;
        private final Vertex<T> to;
        private int cost;

        // Constructors
        public Edge(int cost, Vertex<T> from, Vertex<T> to) {
            if (from == null || to == null)
                throw (new NullPointerException("Both 'to' and 'from' vertices cannot be null."));
            this.cost = cost;
            this.from = from;
            this.to = to;
        }

        /**
         * Deep copy the Edge.
         **/
        public Edge(Edge<T> edge) {
            this(edge.cost, edge.from, edge.to);
        }

        // Setters and Getters
        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Vertex<T> getFrom() {
            return from;
        }

        public Vertex<T> getTo() {
            return to;
        }

        @Override
        public int compareTo(Edge<T> edge) {
            // Compare all the instances:
            // Compare cost
            final int cost = Integer.compare(this.getCost(), edge.getCost());
            if (cost != 0)
                return cost;

            // if cost are equal, compare 'from' vertex
            final int from = this.getFrom().compareTo(edge.getFrom());
            if (from != 0)
                return from;

            // Lastly, compare 'to' vertex
            return this.getTo().compareTo(edge.getTo());
        }

        @Override
        @SuppressWarnings("rawtypes")
        public boolean equals(Object o) {
            if (!(o instanceof Edge edge))
                return false;

            final boolean equalCost = this.getCost() == edge.getCost();
            if (!equalCost)
                return false;

            final boolean equalFrom = this.getFrom().equals(edge.getFrom());
            if (!equalFrom)
                return false;

            return this.getTo() == edge.getTo();
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(this.getCost());
            result = 31 * result + this.getFrom().hashCode();
            result = 31 * result + this.getTo().hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "[" + getFrom().getValue() + "(" + getFrom().getWeight() + ")" + "]" + " -> " +
                    "[" + getTo().getValue() + "(" + getTo().getWeight() + ")" + "]" + " = " + getCost() + "\n";
        }
    }
}
