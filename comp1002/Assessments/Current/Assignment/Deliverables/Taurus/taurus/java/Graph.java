/*
 * NAME: Graph
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Implement a generic graph
 * CREATION: 19/09/2020
 * LAST MODIFICATION: 12/11/2020
 */

import java.util.*;

public class Graph {
    // NESTED CLASSES

    private class Node {
        // PRIVATE CLASS FIELDS
    
        String label;
        LinkedList<Node> neighbours;
        boolean wasVisited;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inLabel (String)
         * EXPORT(S): Address of new Node
         * PURPOSE: Create new Node in alternate state
         * CREATION: 25/09/2020
         * LAST MODIFICATION: 25/09/2020
         */
    
        public Node(String inLabel) {
            if (inLabel == null) {
                throw new IllegalArgumentException("Cannot initialise with a null label");
            }
            else {
                label = inLabel;
                neighbours = new LinkedList<Node>();
                wasVisited = false;
            }
        }
    
        // SETTERS (MUTATORS)
    
        private void addNeighbour(Node inNeighbour) {
            neighbours.insertLast(inNeighbour);
        }
    
        // OPERATORS
    
        private boolean wasVisited() {
            return wasVisited;
        }
    }

    // PRIVATE CLASS FIELDS

    private LinkedList<Node> nodes;
    private boolean isDirected;
    private int nodeCount;
    private int edgeCount;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new Graph object
     * PURPOSE: Create new Graph in default state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public Graph() {
        nodes = new LinkedList<Node>();
        isDirected = false;
        nodeCount = 0;
        edgeCount = 0;
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): isDirected
     * EXPORT(S): Address of new Graph object
     * PURPOSE: Create new Graph in alternate state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public Graph(boolean inDirected) {
        nodes = new LinkedList<Node>();
        isDirected = inDirected;
        nodeCount = 0;
        edgeCount = 0;
    }

    // SETTERS (MUTATORS)

    public void add(String inLabel) {
        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot call add with a null label");
        }
        else if (has(inLabel)) {
            throw new IllegalArgumentException("Cannot call add with a label that has " + 
                "already been assigned to a node");
        }
        else {
            Node node = new Node(inLabel);
            nodes.insertLast(node);
            nodeCount++;
        }
    }

    public void connect(String inSourceVertexLabel, String inSinkVertexLabel) {
        if (nodeCount < 2) {
            throw new IllegalArgumentException("Cannot call connect on a graph with less than " + 
                "two nodes");
        }
        else if (inSourceVertexLabel == null || inSinkVertexLabel == null) {
            throw new IllegalArgumentException("Cannot call connect with a null label");
        }
        else if (inSourceVertexLabel.equals(inSinkVertexLabel)) {
            throw new IllegalArgumentException("Cannot call connect with equivalent labels");
        }
        else {
            _connect(inSourceVertexLabel, inSinkVertexLabel);
            edgeCount++;
        }
    }

    // GETTERS (ACCESSORS)

    public Node get(String inLabel) {
        Node node;

        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot call get on an empty graph");
        }
        else {
            node = _get(inLabel);
            if (node == null) {
                throw new IllegalArgumentException("Cannot call get with a non-existing label");
            }
        }

        return node;
    }

    public int nodeCount() {
        return nodeCount;
    }

    public int edgeCount() {
        return edgeCount;
    }

    // OPERATORS

    public boolean has(String inLabel) {
        boolean has;

        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot call has with a null label");
        }
        else {
            has = _has(inLabel);
        }

        return has;
    }

    public boolean areNeighbours(String inSourceVertexLabel, 
        String inSinkVertexLabel) {
        boolean areNeighbours;
        if (nodeCount < 2) {
            throw new IllegalArgumentException("Cannot call areNeighbours on a graph with less " + 
                "than 2 nodes");
        }
        else if (inSourceVertexLabel == null || inSinkVertexLabel == null) {
            throw new IllegalArgumentException("Cannot call areNeighbours with a null label");
        }
        else if (inSourceVertexLabel.equals(inSinkVertexLabel)) {
            throw new IllegalArgumentException("Cannot call areNeighbours with equivalent labels");
        }
        else {
            areNeighbours = _areNeighbours(inSourceVertexLabel, inSinkVertexLabel);
        }

        return areNeighbours;
    }

    /*
    public Queue<String> bfs() {
        Queue<Node> next = new Queue<Node>();
        Queue<String> visited = new Queue<String>();

        Node startVertex = (Node) nodes.peekFirst();

        // Prepares to search start node's neighbours
        next.offer(startVertex);
        // Prevents start node from being visited again
        startVertex.wasVisited = true;
        // Adds start node to queue of visited nodes
        visited.offer(startVertex.label);

        while (!next.isEmpty()) {
            Node currentNode = (Node) next.poll();
            Iterator<Node> neighboursIter = currentNode.neighbours.iterator();
            // Visits neighbours of current node
            while (neighboursIter.hasNext()) {
                Node currentNeighbour = 
                    (Node) neighboursIter.next();
                if (!currentNeighbour.wasVisited) {
                    // Prepares to search current neighbour's neighbours
                    next.offer(currentNeighbour);
                    // Prevents current neighbour from being visited again
                    currentNeighbour.wasVisited = true;
                    // Adds current neighbour to queue of visited nodes
                    visited.offer(currentNeighbour.label);
                }
            }
        }
        // Prepares nodes for later reuse
        _markAllUnvisited();

        return visited;
    }
    */

    /*
    public Queue<String> dfs() {
        Iterator<Node> nodesIter = nodes.iterator();
        Queue<String> visited = new Queue<String>();

        while (nodesIter.hasNext()) {
            Node currentNode = (Node) nodesIter.next();
            // Visits neighbours of current node
            if (!currentNode.wasVisited()) {
                _dfsVisit(currentNode, visited);
            }
        }
        // Prepares nodes for later reuse
        _markAllUnvisited();

        return visited;
    }
    */

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    public void displayPaths(String startVertexLabel, String endVertexLabel) 
    throws IllegalArgumentException {
        Stack<String> path = new Stack<String>();
        path.push(startVertexLabel);

        Node startVertex = get(startVertexLabel);
        Node endVertex = get(endVertexLabel);
        
        _displayPaths(startVertex, endVertex, path);

        // Prepares nodes for later reuse
        _markAllUnvisited();
    }
    // End of code adapted from GeeksforGeeks

    /*
    public void displayList() {
        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayList on an empty graph");
        }
        else {
            String[][] adjList = _makeAdjList();
            _printArray(adjList);
        }
    }
    */

    /*
    public void displayMatrix() {
        if (nodes.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayMatrix on an empty graph");
        }
        else {
            String[][] adjMatrix = _makeAdjMatrix();
            _printArray(adjMatrix);
        }
    }
    */

    // PRIVATE SUBMODULES

    private void _connect(String inSourceVertexLabel, String inSinkVertexLabel) {
        Iterator<Node> nodesIter = nodes.iterator();
        Node sourceVertex = null;
        Node sinkVertex = null;

        // Finds nodes with inLabel1 and inLabel2
        while (nodesIter.hasNext() && 
            (sourceVertex == null || sinkVertex == null)) {
            Node currentNode = (Node) nodesIter.next();
            // Marks node with label inLabel1 as found
            if (currentNode.label.equals(inSourceVertexLabel)) {
                sourceVertex = currentNode;
            }
            // Marks node with label inLabel2 as found
            else if (currentNode.label.equals(inSinkVertexLabel)) {
                sinkVertex = currentNode;
            }
        }

        if (sourceVertex == null || sinkVertex == null) {
            throw new IllegalArgumentException("Cannot call connect with labels that have not " + 
                "already been assigned to nodes");
        }
        else {
            sourceVertex.addNeighbour(sinkVertex);
            if (!isDirected) {
                sinkVertex.addNeighbour(sourceVertex);
            }
        }
    }

    private boolean _has(String inLabel) {
        Iterator<Node> nodesIter = nodes.iterator();
        boolean has = false;
        while (nodesIter.hasNext() && !has) {
            Node currentNode = (Node) nodesIter.next();
            if (currentNode.label.equals(inLabel)) {
                has = true;
            }
        }

        return has;
    }

    private boolean _areNeighbours(String inSourceVertexLabel, String inSinkVertexLabel) {
        Iterator<Node> nodesIter = nodes.iterator();
        boolean areNeighbours = false;
        // Loops through nodes to find node with label inLabel1 
        while (nodesIter.hasNext() && !areNeighbours) {
            Node currentNode = (Node) nodesIter.next();
            // node with label inLabel1 has been found
            if (currentNode.label.equals(inSourceVertexLabel)) {
                LinkedList<Node> neighbours = currentNode.neighbours;
                Iterator<Node> neighboursIter = neighbours.iterator();
                // Loops through neighbours to find node with label inLabel2
                while (neighboursIter.hasNext() && !areNeighbours) {
                    Node currentNeighbour = 
                        (Node) neighboursIter.next();
                    // node with label inLabel2 has been found
                    if (currentNeighbour.label.equals(inSinkVertexLabel)) {
                        areNeighbours = true;
                    }
                }
            }
        }

        return areNeighbours;
    }

    private Node _get(String inLabel) {
        Iterator<Node> nodesIter = nodes.iterator();
        Node node = null;
        while (nodesIter.hasNext() && node == null) {
            Node currentNode = (Node) nodesIter.next();
            if (currentNode.label.equals(inLabel)) {
                node = currentNode;
            }
        }

        return node;
    }

    private void _markAllUnvisited() {
        Iterator<Node> nodesIter = nodes.iterator();
        while (nodesIter.hasNext()) {
            Node currentNode = (Node) nodesIter.next();
            currentNode.wasVisited = false;
        }
    }

    /*
    private void _dfsVisit(Node currentNode, Queue<String> visited) {
        // Prevents node from being visited again
        currentNode.wasVisited = true;
        // Adds current node to queue of visited nodes
        visited.offer(currentNode.label);

        Iterator<Node> neighboursIter = currentNode.neighbours.iterator();
        while (neighboursIter.hasNext()) {
            Node currentNeighbour = 
                (Node) neighboursIter.next();
            // Visits neighbours of current neighbour
            if (!currentNeighbour.wasVisited()) {
                _dfsVisit(currentNeighbour, visited);
            }
        }
    }
    */

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    private void _displayPaths(Node currentNode, Node endVertex, Stack<String> localPath) {
        String currentVertexLabel = currentNode.label;
        String endVertexLabel = endVertex.label;

        // Stops recursing if path is complete
        if (currentVertexLabel.equals(endVertexLabel)) {
            System.out.println(localPath.toString());
        }
        else {
            // Prevents current node from being visited again
            currentNode.wasVisited = true;

            // Visits neighbours of current node
            Iterator<Node> neighboursIter = currentNode.neighbours.iterator();
            while (neighboursIter.hasNext()) {
                Node currentNeighbour = 
                    (Node) neighboursIter.next();
                if (!currentNeighbour.wasVisited()) {
                    localPath.push(currentNeighbour.label);

                    _displayPaths(currentNeighbour, endVertex, localPath);

                    // Prepares to visit next neighbour
                    localPath.pop();
                }
            }

            // Allows current node to be visited again
            currentNode.wasVisited = false;
        }
    }
    // End of code adapted from GeeksforGeeks

    /*
    private String[][] _makeAdjList() {
        Iterator<Node> nodesIter = nodes.iterator();
        String[][] adjList = new String[nodeCount][nodeCount];

        for (int i = 0; i < adjList.length; i++) {
            Node currentNode = (Node) nodesIter.next();
            adjList[i][0] = currentNode.label;
            Iterator<Node> neighboursIter = currentNode.neighbours.iterator();
            
            for (int j = 1; j < adjList[0].length; j++) {
                Node currentNeighbour = (Node) neighboursIter.next();
                if (currentNeighbour == null) {
                    adjList[i][j] = "";
                }
                else {
                    adjList[i][j] = currentNeighbour.label;
                }
            }
        }

        return adjList;
    }
    */

    /*
    private String[][] _makeAdjMatrix() {
        Iterator<Node> nodesIter = nodes.iterator();
        String[][] adjMatrix = new String[nodeCount + 1][nodeCount + 1];
        adjMatrix[0][0] = " ";

        // Adds y-labels
        for (int i = 1; i < adjMatrix.length; i++) {
            Node currentNode = (Node) nodesIter.next();
            adjMatrix[i][0] = currentNode.label;
        }
        nodesIter = nodes.iterator();

        // Adds x-labels
        for (int j = 1; j < adjMatrix[0].length; j++) {
            Node currentNode = (Node) nodesIter.next();
            adjMatrix[0][j] = currentNode.label;
        }
        nodesIter = nodes.iterator();

        for (int i = 1; i < adjMatrix.length; i++) {
            String outerCurrentLabel = adjMatrix[i][0];
            for (int j = 1; j < adjMatrix[0].length; j++) {
                String innerCurrentLabel = adjMatrix[0][j];
                if (outerCurrentLabel.equals(innerCurrentLabel)) {
                    adjMatrix[i][j] = "0";
                }
                else if (!areNeighbours(outerCurrentLabel, innerCurrentLabel)) {
                    adjMatrix[i][j] = "0";
                }
                else {
                    adjMatrix[i][j] = "1";
                }
            }
        }

        return adjMatrix;
    }
    */

    /*
    private void _printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(" " + array[i][j] + " ");
            }
            System.out.println();
        }
    }
    */
}
