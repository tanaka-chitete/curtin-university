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

    private class GraphNode {
        /** Class Fields (GraphNode) */
    
        private int     label;
        private int     data;
        private boolean visited;
    
        /** Alternate Constructor (GraphNode) */

        private GraphNode(int label) {
            this.label = label;
            this.data = label;
            visited = false;
        }

        /** Utilities (GraphNode) */

        @Override
        public String toString() {
            return Integer.toString(label);
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

        /** Alternate Constructor 1 (Edge) */

        private GraphEdge(GraphNode source, GraphNode destination, int weight) {
            this.source = source;
            this.destination = destination;
        }

        /** Alternate Constructor 2 (Edge) */

        private GraphEdge(GraphNode source, GraphNode destination) {
            this(source, destination, 0);
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

    public GraphNode addNode(int label) {
        if (!containsNode(label)) {
            nodes.add(new GraphNode(label));
        }

        return nodes.get(nodes.size() - 1);
    }

    public void addEdge(int sourceLabel, int destinationLabel) {
        addEdge(sourceLabel, destinationLabel, 0);
    }

    public void addEdge(int sourceLabel, int destinationLabel, int weight) {
        if (!containsEdge(sourceLabel, destinationLabel)) {
            GraphNode source = findNode(sourceLabel);
            if (source == null) {
                source = addNode(sourceLabel);
            }
        
            GraphNode destination = findNode(destinationLabel);
            if (destination == null) {
                destination = addNode(destinationLabel);
            }

            if (directed) {
                edges.add(new GraphEdge(source, destination));
            }
            else {
                edges.add(new GraphEdge(source, destination));
                edges.add(new GraphEdge(destination, source));
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

    private List<GraphNode> getNeighbours(int label) {
        List<GraphNode> neighbours = new ArrayList<>();
        for (GraphEdge e : edges) {
            if (e.source.label == label) {
                neighbours.add(e.destination);
            }
        }

        return neighbours;
    }

    public List<List<Integer>> getAllPaths() {
        return getAllPaths(Integer.MAX_VALUE);
    }

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    public List<List<Integer>> getAllPaths(int maxDepth) {
        GraphNode source = getMinNode();
        GraphNode destination = getMaxNode();
        List<List<Integer>> allPaths = new ArrayList<>();
        Stack<Integer> path = new Stack<Integer>();
        path.push(source.label);
        
        allPaths = getAllPaths(source, destination, maxDepth, allPaths, path);

        // Prepares nodes for later reuse
        setAllUnvisited();

        return allPaths;
    }
    // End of code adapted from GeeksforGeeks

    /** Utilities */

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    private List<List<Integer>> getAllPaths(GraphNode currentNode, 
                                            GraphNode destination, 
                                            int maxDepth,
                                            List<List<Integer>> allPaths,
                                            Stack<Integer> localPath) {
        if (maxDepth >= 0) {
            if (currentNode.label == destination.label) {
                allPaths.add(new ArrayList<>(localPath));
            }
            else {
                // Prevent occurrence of loops in local path
                currentNode.visited = true;
    
                // Visit neighbours of current node
                List<GraphNode> neighbours = getNeighbours(currentNode.label);
                for (GraphNode n : neighbours) {
                    if (!n.visited) {
                        localPath.push(n.label);
                        allPaths = getAllPaths(n, 
                                               destination,  
                                               maxDepth - 1, 
                                               allPaths, 
                                               localPath);
                        localPath.pop();
                    }
                }
    
                // Allow current node to be listed in subsequent paths
                currentNode.visited = false;
            }
        }

        return allPaths;
    }
    // End of code adapted from GeeksforGeeks

    private void setAllUnvisited() {
        for (GraphNode n : nodes) {
            n.visited = false;
        }
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