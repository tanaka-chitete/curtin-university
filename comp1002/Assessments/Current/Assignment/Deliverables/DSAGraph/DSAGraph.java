// Adapted from Tanaka Chitete
// Adapted 30/09/2020

import java.io.Serializable;
import java.util.*;

/*
 * NAME: DSAGraph
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a general-purpose graph
 * CREATION: 19/09/2020
 * LAST MODIFICATION: 07/10/2020
 */

public class DSAGraph implements Serializable {
    // NESTED CLASSES

    public class DSAGraphVertex implements Serializable {
        // PRIVATE CLASS FIELDS
    
        String label;
        DSALinkedList neighbours;
        boolean wasVisited;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inLabel (String)
         * EXPORT(S): Address of new DSAGraphVertex
         * PURPOSE: Create new DSAGraphVertex in alternate state
         * CREATION: 25/09/2020
         * LAST MODIFICATION: 25/09/2020
         */
    
        public DSAGraphVertex(String inLabel) {
            if (inLabel == null) {
                throw new IllegalArgumentException("Cannot initialise with a null label");
            }
            else {
                label = inLabel;
                neighbours = new DSALinkedList();
                wasVisited = false;
            }
        }
    
        // SETTERS (MUTATORS)
    
        private void addNeighbour(DSAGraphVertex inNeighbour) {
            neighbours.insertLast(inNeighbour);
        }
    
        // OPERATORS
    
        private boolean wasVisited() {
            boolean visited;
    
            if (wasVisited) {
                visited = false;
            }
            else {
                visited = true;
            }
    
            return visited;
        }
    }

    // PRIVATE CLASS FIELDS

    DSALinkedList vertices;
    boolean directed;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAGraph object
     * PURPOSE: Create new DSAGraph in default state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public DSAGraph() {
        vertices = new DSALinkedList();
        directed = false;
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): directed
     * EXPORT(S): Address of new DSAGraph object
     * PURPOSE: Create new DSAGraph in alternate state
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public DSAGraph(boolean inDirected) {
        vertices = new DSALinkedList();
        directed = inDirected;
    }

    // SETTERS (MUTATORS)

    public void addVertex(String inLabel) {
        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot call addVertex with a null label");
        }
        else if (hasVertex(inLabel)) {
            throw new IllegalArgumentException("Cannot call addVertex with a label that has " + 
                "already been assigned to a vertex");
        }
        else {
            DSAGraphVertex vertex = new DSAGraphVertex(inLabel);
            vertices.insertLast(vertex);
        }
    }

    public void addEdge(String inSourceVertexLabel, String inSinkVertexLabel) {
        if (getNumVertices() < 2) {
            throw new IllegalArgumentException("Cannot call addEdge on a graph with less than " + 
                "two vertices");
        }
        else if (inSourceVertexLabel == null || inSinkVertexLabel == null) {
            throw new IllegalArgumentException("Cannot call addEdge with a null label");
        }
        else if (inSourceVertexLabel.equals(inSinkVertexLabel)) {
            throw new IllegalArgumentException("Cannot call addEdge with equivalent labels");
        }
        else {
            _addEdge(inSourceVertexLabel, inSinkVertexLabel);
        }
    }

    // GETTERS (ACCESSORS)

    public DSAGraphVertex getVertex(String inLabel) {
        DSAGraphVertex vertex;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getVertex on an empty graph");
        }
        else {
            vertex = _getVertex(inLabel);
        }

        return vertex;
    }

    public int getNumVertices() {
        int numVertices;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getNumVertices on an empty graph");
        }
        else {
            numVertices = _getNumVertices();
        }

        return numVertices;
    }

    public int getNumEdges() {
        int numEdges;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getNumEdges an empty graph");
        }
        else {
            numEdges = _getNumEdges();
        }

        return numEdges;
    }

    // OPERATORS

    public boolean hasVertex(String inLabel) {
        boolean hasVertex;

        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot call hasVertex with a null label");
        }
        else {
            hasVertex = _hasVertex(inLabel);
        }

        return hasVertex;
    }

    public boolean areNeighbours(String inSourceVertexLabel, 
        String inSinkVertexLabel) {
        boolean areNeighbours;
        if (getNumVertices() < 2) {
            throw new IllegalArgumentException("Cannot call areNeighbours on a graph with less " + 
                "than 2 vertices");
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

    public DSAQueue bfs() {
        DSAQueue next = new DSAQueue();
        DSAQueue visited = new DSAQueue();

        DSAGraphVertex startVertex = (DSAGraphVertex) vertices.peekFirst();

        // Prepares to search start vertex's neighbours
        next.enqueue(startVertex);
        // Prevents start vertex from being visited again
        startVertex.wasVisited = true;
        // Adds start vertex to queue of visited vertices
        visited.enqueue(startVertex.label);

        while (!next.isEmpty()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) next.dequeue();
            Iterator neighboursIter = currentVertex.neighbours.iterator();
            // Visits neighbours of current vertex
            while (neighboursIter.hasNext()) {
                DSAGraphVertex currentNeighbour = 
                    (DSAGraphVertex) neighboursIter.next();
                if (!currentNeighbour.wasVisited) {
                    // Prepares to search current neighbour's neighbours
                    next.enqueue(currentNeighbour);
                    // Prevents current neighbour from being visited again
                    currentNeighbour.wasVisited = true;
                    // Adds current neighbour to queue of visited vertices
                    visited.enqueue(currentNeighbour.label);
                }
            }
        }
        // Prepares vertices for later reuse
        _markAllUnvisited();

        return visited;
    }

    public DSAQueue dfs() {
        DSAQueue visited = new DSAQueue();
        Iterator verticesIter = vertices.iterator();

        while (verticesIter.hasNext()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            // Visits neighbours of current vertex
            if (!currentVertex.wasVisited()) {
                _dfsVisit(currentVertex, visited);
            }
        }
        // Prepares vertices for later reuse
        _markAllUnvisited();

        return visited;
    }

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    public void displayPaths(String startVertexLabel, String endVertexLabel) {
        DSAStack path = new DSAStack();
        path.push(startVertexLabel);

        DSAGraphVertex startVertex = getVertex(startVertexLabel);
        DSAGraphVertex endVertex = getVertex(endVertexLabel);
        
        _displayPaths(startVertex, endVertex, path);

        // Prepares vertices for later reuse
        _markAllUnvisited();
    }
    // End of code adapted from GeeksforGeeks

    public void displayList() {
        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayList on an empty graph");
        }
        else {
            String[][] adjList = _makeAdjList();
            _printArray(adjList);
        }
    }

    public void displayMatrix() {
        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayMatrix on an empty graph");
        }
        else {
            String[][] adjMatrix = _makeAdjMatrix();
            _printArray(adjMatrix);
        }
    }

    // PRIVATE SUBMODULES

    private void _addEdge(String inSourceVertexLabel, String inSinkVertexLabel) {
        Iterator verticesIter = vertices.iterator();
        DSAGraphVertex sourceVertex = null;
        DSAGraphVertex sinkVertex = null;

        // Finds vertices with inLabel1 and inLabel2
        while (verticesIter.hasNext() && 
            (sourceVertex == null || sinkVertex == null)) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            // Marks vertex with label inLabel1 as found
            if (currentVertex.label.equals(inSourceVertexLabel)) {
                sourceVertex = currentVertex;
            }
            // Marks vertex with label inLabel2 as found
            else if (currentVertex.label.equals(inSinkVertexLabel)) {
                sinkVertex = currentVertex;
            }
        }

        if (sourceVertex == null || sinkVertex == null) {
            throw new IllegalArgumentException("Cannot call addEdge with labels that have not " + 
                "already been assigned to vertices");
        }
        else {
            sourceVertex.addNeighbour(sinkVertex);
            if (!directed) {
                sinkVertex.addNeighbour(sourceVertex);
            }
        }
    }

    private boolean _hasVertex(String inLabel) {
        Iterator verticesIter = vertices.iterator();
        boolean hasVertex = false;
        while (verticesIter.hasNext() && !hasVertex) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            if (currentVertex.label.equals(inLabel)) {
                hasVertex = true;
            }
        }

        return hasVertex;
    }

    private boolean _areNeighbours(String inSourceVertexLabel, String inSinkVertexLabel) {
        Iterator verticesIter = vertices.iterator();
        boolean areNeighbours = false;
        // Loops through vertices to find vertex with label inLabel1 
        while (verticesIter.hasNext() && !areNeighbours) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            // Vertex with label inLabel1 has been found
            if (currentVertex.label.equals(inSourceVertexLabel)) {
                DSALinkedList neighbours = currentVertex.neighbours;
                Iterator neighboursIter = neighbours.iterator();
                // Loops through neighbours to find vertex with label inLabel2
                while (neighboursIter.hasNext() && !areNeighbours) {
                    DSAGraphVertex currentNeighbour = 
                        (DSAGraphVertex) neighboursIter.next();
                    // Vertex with label inLabel2 has been found
                    if (currentNeighbour.label.equals(inSinkVertexLabel)) {
                        areNeighbours = true;
                    }
                }
            }
        }

        return areNeighbours;
    }

    private DSAGraphVertex _getVertex(String inLabel) {
        Iterator verticesIter = vertices.iterator();
        DSAGraphVertex vertex = null;
        while (verticesIter.hasNext() && vertex == null) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            if (currentVertex.label.equals(inLabel)) {
                vertex = currentVertex;
            }
        }

        return vertex;
    }

    private int _getNumVertices() {
        Iterator verticesIter = vertices.iterator();
        int numVertices = 0;
        while (verticesIter.hasNext()) {
            numVertices++;
            verticesIter.next();
        }

        return numVertices;
    }

    private int _getNumEdges() {
        Iterator verticesIter = vertices.iterator();
        int numEdges = 0;
        while (verticesIter.hasNext()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            Iterator neighboursIter = currentVertex.neighbours.iterator();
            while (neighboursIter.hasNext()) {
                numEdges++;
                neighboursIter.next();
            }
        }

        if (!directed) {
            numEdges /= 2;
        }

        return numEdges;
    }

    private void _markAllUnvisited() {
        Iterator verticesIter = vertices.iterator();
        while (verticesIter.hasNext()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            currentVertex.wasVisited = false;
        }
    }

    private void _dfsVisit(DSAGraphVertex currentVertex, DSAQueue visited) {
        // Prevents vertex from being visited again
        currentVertex.wasVisited = true;
        // Adds current vertex to queue of visited vertices
        visited.enqueue(currentVertex.label);

        Iterator neighboursIter = currentVertex.neighbours.iterator();
        while (neighboursIter.hasNext()) {
            DSAGraphVertex currentNeighbour = 
                (DSAGraphVertex) neighboursIter.next();
            // Visits neighbours of current neighbour
            if (!currentNeighbour.wasVisited()) {
                _dfsVisit(currentNeighbour, visited);
            }
        }
    }

    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/find-paths-given-source-destination/
    // Accessed 07/10/2020
    private void _displayPaths(DSAGraphVertex currentVertex, DSAGraphVertex endVertex, 
    DSAStack localPath) {
        String currentVertexLabel = currentVertex.label;
        String endVertexLabel = endVertex.label;

        // Stops recursing if path is complete
        if (currentVertexLabel.equals(endVertexLabel)) {
            System.out.println(localPath.toString());
        }
        else {
            // Prevents current vertex from being visited again
            currentVertex.wasVisited = true;

            // Visits neighbours of current vertex
            Iterator neighboursIter = 
                currentVertex.neighbours.iterator();
            while (neighboursIter.hasNext()) {
                DSAGraphVertex currentNeighbour = 
                    (DSAGraphVertex) neighboursIter.next();
                if (!currentNeighbour.wasVisited()) {
                    localPath.push(currentNeighbour.label);

                    _displayPaths(currentNeighbour, endVertex, localPath);

                    // Prepares to visit next neighbour
                    localPath.pop();
                }
            }

            // Allows current vertex to be visited again
            currentVertex.wasVisited = false;
        }
    }
    // End of code adapted from GeeksforGeeks

    private String[][] _makeAdjList() {
        Iterator verticesIter = vertices.iterator();
        int numVertices = getNumVertices();
        String[][] adjList = new String[numVertices][numVertices];

        for (int i = 0; i < adjList.length; i++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjList[i][0] = currentVertex.label;
            Iterator neighboursIter = currentVertex.neighbours.iterator();
            
            for (int j = 1; j < adjList[0].length; j++) {
                DSAGraphVertex currentNeighbour = 
                    (DSAGraphVertex) neighboursIter.next();
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

    private String[][] _makeAdjMatrix() {
        Iterator verticesIter = vertices.iterator();
        int numVertices = getNumVertices();
        String[][] adjMatrix = new String[numVertices + 1][numVertices + 1];
        adjMatrix[0][0] = " ";

        // Adds y-labels
        for (int i = 1; i < adjMatrix.length; i++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjMatrix[i][0] = currentVertex.label;
        }
        verticesIter = vertices.iterator();

        // Adds x-labels
        for (int j = 1; j < adjMatrix[0].length; j++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjMatrix[0][j] = currentVertex.label;
        }
        verticesIter = vertices.iterator();

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

    private void _printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(" " + array[i][j] + " ");
            }
            System.out.println();
        }
    }
}
