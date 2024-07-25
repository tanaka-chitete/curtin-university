/*
 * NAME: Graph
 * AUTHOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: Intelligent Agents (COMP2009)
 * PURPOSE: Implement generic graph
 * CREATION: 19/09/2020
 * LAST MODIFICATION: 31/03/2021
 */

import java.util.*;

public class Graph {
    // NESTED CLASSES

    /*
    * NAME: Node
    * AUTHOR: Tanaka Chitete
    * STUDENT_ID: 20169321
    * UNIT: Intelligent Agents (COMP2009)
    * PURPOSE: Implement nodes for Graph object
    * CREATION: 19/09/2020
    * LAST MODIFICATION: 31/03/2021
    */ 

    private class Node {
        // PRIVATE CLASS FIELDS
    
        private String label;
        private Object data;
        private boolean visited = false;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): label (String)
         * EXPORT(S): Address of new Node object
         * PURPOSE: Create new Node object in alternate state
         * CREATION: 25/09/2020
         * LAST MODIFICATION: 31/03/2021
         */
    
        private Node(String label) {
            if (label == null) {
                throw new IllegalArgumentException("Cannot construct Node with null label");
            }
            else {
                this.label = label;
                data = null;
            }
        }

        // GETTERS (ACCESSORS)
    
        private ArrayList<Node> getNeighbours(ArrayList<Edge> edges) {
            ArrayList<Node> neighbours = new ArrayList<>();
            for (Edge e : edges) {
                // One of this node's destinations is the current ege
                if (label.equals(e.source.label)) {
                    neighbours.add(e.destination);
                }
            }

            return neighbours;
        }

        // OPERATORS

        public String toString() {
            return String.format("{%s: %s}", label, data.toString());
        }
    }

    /*
    * NAME: Edge
    * AUTHOR: Tanaka Chitete
    * STUDENT_ID: 20169321
    * UNIT: Intelligent Agents (COMP2009)
    * PURPOSE: Implement edges for Graph object
    * CREATION: 19/09/2020
    * LAST MODIFICATION: 31/03/2021
    */ 

    private class Edge {
        // PRIVATE CLASS FIELDS
        
        private Node   source;
        private Node   destination;
        private String label;
        private int    weight;

        // CONSTRUCTORS

        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): source (Node), destination (Node), weight (int)
         * EXPORT(S): Address of new Edge object
         * PURPOSE: Create new Edge object in alternate state #1
         * CREATION: 31/03/2021
         * LAST MODIFICATION: 31/03/2021
         */   

        private Edge(Node source, Node destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): source (Node), destination (Node), String (label)
         * EXPORT(S): Address of new Edge object
         * PURPOSE: Create new Edge object in alternate state #2
         * CREATION: 31/03/2021
         * LAST MODIFICATION: 31/03/2021
         */   

        private Edge(Node source, Node destination) {
            new Edge(source, destination, 0);
        }
    }

    // PRIVATE CLASS FIELDS

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private boolean         directed;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new Graph object
     * PURPOSE: Create new Graph in default state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    public Graph() {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        directed = false;
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): directed (boolean)
     * EXPORT(S): Address of new Graph object
     * PURPOSE: Create new Graph in alternate state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    public Graph(boolean directed) {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        this.directed = directed;
    }

    // SETTERS (MUTATORS)

    public void addNode(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Cannot add Node with null label");
        }
        else if (!hasNode(label)) {
            Node n = new Node(label);
            nodes.add(n);
        }
    }

    public void addEdge(String sourceLabel, String destinationLabel, int weight) {
        if (sourceLabel == null) {
            throw new IllegalArgumentException("Cannot connect source Node with null label");
        }
        else if (destinationLabel == null) {
            throw new IllegalArgumentException("Cannot connect destination Node with null label");
        }
        else if (sourceLabel.equals(destinationLabel)) {
            throw new IllegalArgumentException("Cannot connect source and destination nodes with " + 
                "equivalent labels");
        }
        else {
            _addEdge(sourceLabel, destinationLabel, weight);
        }
    }

    public void addEdge(String sourceLabel, String destinationLabel) {
        addEdge(sourceLabel, destinationLabel, 0);
    }

    // GETTERS (ACCESSORS)

    public int getNodeCount() {
        return nodes.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public boolean hasNode(String label) {
        boolean hasNode;

        if (label == null) {
            throw new IllegalArgumentException("Cannot check if Graph has Node with null label");
        }
        else {
            hasNode = _hasNode(label);
        }

        return hasNode;
    }

    public boolean hasEdge(String sourceLabel, String destinationLabel) {
        boolean hasEdge;

        if (nodes.size() < 2) {
            hasEdge = false;
        }        
        else if (sourceLabel == null) {
            throw new IllegalArgumentException("Cannot check if Graph has Edge involving source " + 
                "Node with null label");
        }
        else if (destinationLabel == null) {
            throw new IllegalArgumentException("Cannot check if Graph has Edge involving " + 
                "destination Node with null label");
        }
        else if (sourceLabel.equals(destinationLabel)) {
            throw new IllegalArgumentException("Cannot check if Graph has Edge involving source " + 
                "and destination nodes with equivalent labels");
        }
        else {
            hasEdge = _hasEdge(sourceLabel, destinationLabel);
        }

        return hasEdge;
    }

    public Node getNode(String label) {
        Node node;

        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot get Node from empty Graph");
        }
        else if (label == null) {
            throw new IllegalArgumentException("Cannot get Node with null label");
        }
        else {
            node = _getNode(label);
            if (node == null) {
                throw new IllegalArgumentException("Cannot get Node with non-existing label");
            }
            else {
                return node;
            }
        }
    }

    public Edge getEdge(String sourceLabel, String destinationLabel) {
        Edge edge;

        if (nodes.size() < 2) {
            throw new IllegalArgumentException("Cannot get Edge from Graph with less than 2 nodes");
        }
        else if (sourceLabel == null) {
            throw new IllegalArgumentException("Cannot get Edge from Graph involving null source " +
                "label");
        }
        else if (destinationLabel == null) {
            throw new IllegalArgumentException("Cannot get Edge from Graph involving null " + 
                "destination label");
        }
        else {
            edge = _getEdge(sourceLabel, destinationLabel);
            if (edge == null) {
                throw new IllegalArgumentException("Cannot get non-existing Edge");
            }
            else {
                return edge;
            }
        }
    }

    public ArrayList<Node> getNeighbours(String label) {
        return getNode(label).getNeighbours(edges);
    }

    // OPERATORS




    public Queue<String> BFS() {
        Queue<Node> nodesWithNeighboursToVisit = new LinkedList<Node>();
        Queue<String> visitedNodes = new LinkedList<String>();

        Node startNode = nodes.get(0);

        // Prepares to search start node's neighbours
        nodesWithNeighboursToVisit.offer(startNode);
        // Prevents start node from being visited again
        startNode.visited = true;
        // Adds start node to queue of visited nodes
        visitedNodes.offer(startNode.label);

        while (!nodesWithNeighboursToVisit.isEmpty()) {
            Node currentNode = nodesWithNeighboursToVisit.poll();
            ArrayList<Node> currentNodeNeighbours = currentNode.getNeighbours(edges);
            // Visits neighbours of current node
            for (Node currentNeighbour : currentNodeNeighbours) {
                if (!currentNeighbour.visited) {
                    // Prepares to search current neighbour's neighbours
                    nodesWithNeighboursToVisit.offer(currentNeighbour);
                    // Prevents current neighbour from being visited again
                    currentNeighbour.visited = true;
                    // Adds current neighbour to queue of visited nodes
                    visitedNodes.offer(currentNeighbour.label);
                }
            }
        }

        // Prepares nodes for later reuse
        _clearAllVisited();

        return visitedNodes;
    }

    public Queue<String> DFS() {
        Queue<String> visitedNodes = new LinkedList<String>();

        for (Node currentNode : nodes) {
            // Visits neighbours of current node
            if (!currentNode.visited) {
                _DFS(currentNode, visitedNodes);
            }
        }

        // Prepares nodes for later reuse
        _clearAllVisited();

        return visitedNodes;
    }

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    public void displayPaths(String startVertexLabel, String endVertexLabel) 
    throws IllegalArgumentException {
        Stack<String> path = new Stack<String>();
        path.push(startVertexLabel);

        Node startVertex = getNode(startVertexLabel);
        Node endVertex = getNode(endVertexLabel);
        
        _displayPaths(startVertex, endVertex, path);

        // Prepares nodes for later reuse
        _clearAllVisited();
    }
    // End of code adapted from GeeksforGeeks

    public void printAdjacencyList() {
        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot print adjacency list of empty Graph");
        }
        else {
            String[][] adjacencyList = _getAdjacencyList();
            _print(adjacencyList);
        }
    }

    public void printAdjacencyMatrix() {
        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot print adjacency matrix of empty graph");
        }
        else {
            String[][] adjacencyMatrix = _getAdjacencyMatrix();
            _print(adjacencyMatrix);
        }
    }

    // PRIVATE SUBMODULES

    private void _addEdge(String sourceLabel, String destinationLabel, int weight) {
        if (!hasEdge(sourceLabel, destinationLabel)) {
            addNode(sourceLabel);
            addNode(destinationLabel);
    
            Node source = getNode(sourceLabel);
            Node destination = getNode(destinationLabel);    

            Edge e = new Edge(source, destination, weight);
            edges.add(e);
        }

        if (!directed) {
            Node source = getNode(destinationLabel);
            Node destination = getNode(sourceLabel);

            Edge e = new Edge(source, destination, weight);
            edges.add(e);
        }
    } 

    private boolean _hasNode(String label) {
        boolean hasNode = false;

        int i = 0;
        while (i < nodes.size() && !hasNode) {
            Node currentNode = nodes.get(i);
            if (currentNode.label.equals(label)) {
                hasNode = true;
            }

            i++;
        }

        return hasNode;
    }

    private boolean _hasEdge(String sourceLabel, String destinationLabel) {
        boolean hasEdge = false;

        int i = 0;
        while (i < edges.size() && !hasEdge) {
            Edge currentEdge = edges.get(i);
            if (currentEdge.source.label.equals(sourceLabel) && 
                currentEdge.destination.label.equals(destinationLabel)) {
                hasEdge = true;
            }

            i++;
        }

        return hasEdge;
    }

    private Node _getNode(String inLabel) {
        Node node = null;

        int i = 0;
        while (i < nodes.size() && node == null) {
            Node currentNode = nodes.get(i);
            if (currentNode.label.equals(inLabel)) {
                node = currentNode;
            }

            i++;
        }

        return node;
    }

    private Edge _getEdge(String sourceLabel, String destinationLabel) {
        Edge edge = null;

        int i = 0;
        while (i < edges.size() && edge == null) {
            Edge currentEdge = edges.get(i);
            if (currentEdge.source.label.equals(sourceLabel) && 
                currentEdge.destination.label.equals(destinationLabel)) {
                edge = currentEdge;
            }

            i++;
        }

        return edge;
    }

    private void _clearAllVisited() {
        Iterator<Node> nodesIterator = nodes.iterator();
        while (nodesIterator.hasNext()) {
            Node currentNode = (Node) nodesIterator.next();
            currentNode.visited = false;
        }
    }

    private void _DFS(Node currentNode, Queue<String> visitedNodes) {
        // Prevents node from being visited again
        currentNode.visited = true;
        // Adds current node to queue of visited nodes
        visitedNodes.offer(currentNode.label);

        ArrayList<Node> currentNodeNeighbours = getNeighbours(currentNode.label);
        for (Node currentNeighbour : currentNodeNeighbours) {
            // Visits neighbours of current neighbour
            if (!currentNeighbour.visited) {
                _DFS(currentNeighbour, visitedNodes);
            }
        }
    }

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    private void _displayPaths(Node currentNode, Node destinationNode, Stack<String> localPath) {
        // Stops recursing if path is complete
        if (currentNode.label.equals(destinationNode.label)) {
            System.out.println(localPath.toString());
        }
        else {
            // Prevents current node from being visited again
            currentNode.visited = true;

            // Visits neighbours of current node
            ArrayList<Node> currentNodeNeighbours = getNeighbours(currentNode.label);

            for (Node currentNeighbour : currentNodeNeighbours) {
                if (!currentNeighbour.visited) {
                    localPath.push(currentNeighbour.label);

                    _displayPaths(currentNeighbour, destinationNode, localPath);

                    // Prepares to visit next neighbour
                    localPath.pop();
                }
            }

            // Allows current node to be visited again
            currentNode.visited = false;
        }
    }
    // End of code adapted from GeeksforGeeks

    private String[][] _getAdjacencyList() {
        String[][] adjacencyList = new String[nodes.size()][nodes.size()];

        Iterator<Node> nodesIterator = nodes.iterator();
        for (int i = 0; i < adjacencyList.length; i++) {
            Node currentNode = nodesIterator.next();
            adjacencyList[i][0] = currentNode.label;
            Iterator<Node> currentNodeNeighboursIterator = 
                currentNode.getNeighbours(edges).iterator();
            
            for (int j = 1; j < adjacencyList[0].length; j++) {
                String currentNeighbourLabel;
                if (currentNodeNeighboursIterator.hasNext()) {
                    currentNeighbourLabel = currentNodeNeighboursIterator.next().label;
                }
                else {
                    currentNeighbourLabel = "";
                }

                adjacencyList[i][j] = currentNeighbourLabel;

            }
        }

        return adjacencyList;
    }

    private String[][] _getAdjacencyMatrix() {
        Iterator<Node> nodesIterator = nodes.iterator();
        String[][] adjacencyMatrix = new String[nodes.size() + 1][nodes.size() + 1];
        adjacencyMatrix[0][0] = " ";

        // Add y-axis labels
        for (int i = 1; i < adjacencyMatrix.length; i++) {
            Node currentNode = (Node) nodesIterator.next();
            adjacencyMatrix[i][0] = currentNode.label;
        }
        nodesIterator = nodes.iterator();

        // Add x-axis labels
        for (int j = 1; j < adjacencyMatrix[0].length; j++) {
            Node currentNode = (Node) nodesIterator.next();
            adjacencyMatrix[0][j] = currentNode.label;
        }
        nodesIterator = nodes.iterator();

        for (int i = 1; i < adjacencyMatrix.length; i++) {
            String currentSourceLabel = adjacencyMatrix[i][0];
            for (int j = 1; j < adjacencyMatrix[0].length; j++) {
                String currentDestinationLabel = adjacencyMatrix[0][j];
                if (currentSourceLabel.equals(currentDestinationLabel)) {
                    adjacencyMatrix[i][j] = "-";
                }
                else if (!hasEdge(currentSourceLabel, currentDestinationLabel)) {
                    adjacencyMatrix[i][j] = "-";
                }
                else {
                    Edge edge = getEdge(currentSourceLabel, currentDestinationLabel);
                    adjacencyMatrix[i][j] = Integer.toString(edge.weight);
                }
            }
        }

        return adjacencyMatrix;
    }

    private void _print(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.format("%s ", array[i][j]);
            }
            System.out.println();
        }
    }
}