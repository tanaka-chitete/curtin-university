/**
 * Implements a generic graph
 * 
 * @author Tanaka Chitete (20169321)
 */

import java.util.*;

public class Graph {
    /**
     * Implements nodes for Graph object
     * 
     * @author Tanaka Chitete (20169321)
     */

    private class GraphNode implements Comparable<GraphNode> {
        /** Class Fields (GraphNode) */
    
        private int       label;
        private int       data;
        private GraphNode parent;
        private int       f;
        private int       g;
        private int       h;
    
        /** Alternate Constructor (GraphNode) */

        private GraphNode(int label, int h) {
            this.label = label;
            this.data = label;
            parent = null;
            f = 0;
            g = 0;
            this.h = h;
        }

        /** Utilities (GraphNode) */

        @Override
        public int compareTo(GraphNode node) {
            return Integer.compare(this.f, node.f);
        }

        @Override
        public String toString() {
            return String.format("{label: %d, f-cost: %d}", label, f);
        }

        public int computeHeuristic(GraphNode destination) {
            return h;
        }
    }

    /**
     * Implements edges for Graph object
     * 
     * @author Tanaka Chitete (20169321)
     */

    private class GraphEdge {
        /** Class Fields (Edge) */
        
        private GraphNode   source;
        private GraphNode   destination;
        private int         weight;

        /** Alternate Constructor (Edge) */

        private GraphEdge(GraphNode source, GraphNode destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override 
        public String toString() {
            return String.format("(%s, %s)", source, destination);
        }
    }

    /** Class Fields (Graph) */

    private List<GraphNode> nodes;
    private List<GraphEdge> edges;
    private boolean         directed;

    /** Default Constructor (Graph) */

    public Graph() {
        nodes = new ArrayList<GraphNode>();
        edges = new ArrayList<GraphEdge>();
        directed = false;
    }

    /** Alternate Constructor (Graph) */

    public Graph(boolean directed) {
        nodes = new ArrayList<GraphNode>();
        edges = new ArrayList<GraphEdge>();
        this.directed = directed;
    }

    /** Setters (Graph) */

    public GraphNode addNode(int label, int h) {
        if (!containsNode(label)) {
            nodes.add(new GraphNode(label, h));
        }

        return nodes.get(nodes.size() - 1);
    }

    public void addEdge(int sourceLabel, 
                        int sourceH, 
                        int destinationLabel,
                        int destinationH,
                        int weight) {
        if (!containsEdge(sourceLabel, destinationLabel)) {
            GraphNode source = findNode(sourceLabel);
            if (source == null) {
                source = addNode(sourceLabel, sourceH);
            }
        
            GraphNode destination = findNode(destinationLabel);
            if (destination == null) {
                destination = addNode(destinationLabel, destinationH);
            }

            if (directed) {
                edges.add(new GraphEdge(source, destination, weight));
            }
            else {
                edges.add(new GraphEdge(source, destination, weight));
                edges.add(new GraphEdge(destination, source, weight));
            }
        }
    }

    /** Getters (Graph) */

    public boolean containsNode(int label) {
        return findNode(label) != null;
    }

    private GraphNode findNode(int label) {
        int i = 0;
        GraphNode target = null;
        while (target == null && i < nodes.size()) {
            GraphNode current = nodes.get(i);
            if (current.label == label) {
                target = current;
            }
            else {
                i++;
            }
        }

        return target;
    }

    public boolean containsEdge(int sourceLabel, int destinationLabel) {
        return findEdge(sourceLabel, destinationLabel) != null;
    }

    private GraphEdge findEdge(int sourceLabel, int destinationLabel) {
        int i = 0;
        GraphEdge target = null;
        while (target == null && i < edges.size()) {
            GraphEdge current = edges.get(i);
            if (current.source.label == sourceLabel && 
            current.destination.label == destinationLabel) {
                    target = current;
            }
            else {
                i++;
            }
        }

        return target;
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    // private List<GraphNode> getNeighbours(int label) {
    //     List<GraphNode> neighbours = new ArrayList<>();
    //     for (GraphEdge e : edges) {
    //         if (e.source.label == label) {
    //             neighbours.add(e.destination);
    //         }
    //     }

    //     return neighbours;
    // }

    // Adapted from Stack Abuse
    // https://stackabuse.com/graphs-in-java-a-star-algorithm/
    // Accessed 11/05/2021
    public List<String> aStar() {
        GraphNode source = getMinNode();
        GraphNode destination = getMaxNode();

        PriorityQueue<GraphNode> open = new PriorityQueue<>();
        PriorityQueue<GraphNode> closed = new PriorityQueue<>();

        source.f = source.g + source.computeHeuristic(destination);
        open.add(source);

        List<String> path;
        GraphNode target = null;
        while (!open.isEmpty()) {
            GraphNode node = open.peek();
            if (node.label == destination.label) {
                target = node;
            }

            for (GraphEdge e : edges) {
                if (e.source == node) {
                    GraphNode neighbour = e.destination;
                    int totalWeight = node.g + e.weight;

                    if (!open.contains(neighbour) && !closed.contains(neighbour)) {
                        neighbour.parent = node;
                        neighbour.g = totalWeight;
                        neighbour.f = neighbour.g + neighbour.computeHeuristic(destination);

                        open.add(neighbour);
                    }
                    else if (totalWeight < neighbour.g) {
                        neighbour.parent = node;
                        neighbour.g = totalWeight;
                        neighbour.f = neighbour.g + neighbour.computeHeuristic(destination);

                        if (closed.contains(neighbour)) {
                            closed.remove(neighbour);
                            open.add(neighbour);
                        }
                    }
                }
            }
            open.remove(node);
            closed.add(node);
        }        

        if (target != null) {
            path = makePath(target);
        }
        else {
            path = new ArrayList<String>();
        }

        return path;
    }
    // End of code adapted from Stack Abuse

    /** Utilities */

    private List<String> makePath (GraphNode target) {
        List<String> path = new ArrayList<>();
        path.add(target.toString());
        int totalCost = target.f;
        GraphNode node = target;
        while (node.parent != null) {
            path.add(0, node.parent.toString());
            totalCost += node.parent.f;
            node = node.parent;
        }
        path.add("total: " + totalCost);

        return path;
    }

    private GraphNode getMinNode() {
        GraphNode min = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            GraphNode current = nodes.get(i);
            if (current.data < min.data) {
                min = current;
            }
        }

        return min;
    }

    private GraphNode getMaxNode() {
        GraphNode max = nodes.get(0);
        for (int i = 1; i < nodes.size(); i++) {
            GraphNode current = nodes.get(i);
            if (current.data > max.data) {
                max = current;
            }
        }

        return max;
    }
}