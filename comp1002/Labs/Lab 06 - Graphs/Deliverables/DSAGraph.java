import java.util.*;

public class DSAGraph {
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
            throw new IllegalArgumentException("Cannot call addVertex with a" + 
                "null label");
        }
        else if (hasVertex(inLabel)) {
            throw new IllegalArgumentException("Cannot call addVertex with a" +
                "label that has already been assigned to a vertex");
        }
        else {
            DSAGraphVertex vertex = new DSAGraphVertex(inLabel);
            vertices.insertLast(vertex);
        }
    }

    public void addEdge(String inSourceVertexLabel, String inSinkVertexLabel) {
        if (getNumVertices() < 2) {
            throw new IllegalArgumentException("Cannot call addEdge on a " + 
                "graph with less than two vertices");
        }
        else if (inSourceVertexLabel == null || inSinkVertexLabel == null) {
            throw new IllegalArgumentException("Cannot call addEdge with a " +
                "null label");
        }
        else if (inSourceVertexLabel.equals(inSinkVertexLabel)) {
            throw new IllegalArgumentException("Cannot call addEdge with " + 
                "equivalent labels");
        }
        else {
            _addEdge(inSourceVertexLabel, inSinkVertexLabel);
        }
    }

    private void _addEdge(String inSourceVertexLabel, 
        String inSinkVertexLabel) {
        Iterator verticesIter = vertices.iterator();
        DSAGraphVertex sourceVertex = null;
        DSAGraphVertex sinkVertex = null;

        // Finds vertices with inLabel1 and inLabel2
        while (verticesIter.hasNext() && 
            (sourceVertex == null || sinkVertex == null)) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            // Marks vertex with label inLabel1 as found
            if (currentVertex.getLabel().equals(inSourceVertexLabel)) {
                sourceVertex = currentVertex;
            }
            // Marks vertex with label inLabel2 as found
            else if (currentVertex.getLabel().equals(inSinkVertexLabel)) {
                sinkVertex = currentVertex;
            }
        }

        if (sourceVertex == null || sinkVertex == null) {
            throw new IllegalArgumentException("Cannot call addEdge with " + 
                "labels that have not already been assigned to vertices");
        }
        else {
            sourceVertex.addNeighbour(sinkVertex);
            if (!directed) {
                sinkVertex.addNeighbour(sourceVertex);
            }
        }
    }

    // GETTERS (ACCESSORS)

    public boolean hasVertex(String inLabel) {
        boolean hasVertex;

        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot call hasVertex with a " + 
                "null label");
        }
        else {
            hasVertex = _hasVertex(inLabel);
        }

        return hasVertex;
    }

    private boolean _hasVertex(String inLabel) {
        Iterator verticesIter = vertices.iterator();
        boolean hasVertex = false;
        while (verticesIter.hasNext() && !hasVertex) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            if (currentVertex.getLabel().equals(inLabel)) {
                hasVertex = true;
            }
        }

        return hasVertex;
    }

    public boolean areNeighbours(String inSourceVertexLabel, 
        String inSinkVertexLabel) {
        boolean areNeighbours;
        if (getNumVertices() < 2) {
            throw new IllegalArgumentException("Cannot call areNeighbours on " + 
                "a graph with less than 2 vertices");
        }
        else if (inSourceVertexLabel == null || inSinkVertexLabel == null) {
            throw new IllegalArgumentException("Cannot call areNeighbours with " + 
                "a null label");
        }
        else if (inSourceVertexLabel.equals(inSinkVertexLabel)) {
            throw new IllegalArgumentException("Cannot call areNeighbours with " + 
                "equivalent labels");
        }
        else {
            areNeighbours = _areNeighbours(inSourceVertexLabel, inSinkVertexLabel);
        }

        return areNeighbours;
    }

    private boolean _areNeighbours(String inSourceVertexLabel, 
        String inSinkVertexLabel) {
        Iterator verticesIter = vertices.iterator();
        boolean areNeighbours = false;
        // Loops through vertices to find vertex with label inLabel1 
        while (verticesIter.hasNext() && !areNeighbours) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            // Vertex with label inLabel1 has been found
            if (currentVertex.getLabel().equals(inSourceVertexLabel)) {
                DSALinkedList neighbours = currentVertex.getNeighbours();
                Iterator neighboursIter = neighbours.iterator();
                // Loops through neighbours to find vertex with label inLabel2
                while (neighboursIter.hasNext() && !areNeighbours) {
                    DSAGraphVertex currentNeighbour = 
                        (DSAGraphVertex) neighboursIter.next();
                    // Vertex with label inLabel2 has been found
                    if (currentNeighbour.getLabel().equals(inSinkVertexLabel)) {
                        areNeighbours = true;
                    }
                }
            }
        }

        return areNeighbours;
    }

    public DSAGraphVertex getVertex(String inLabel) {
        DSAGraphVertex vertex;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getVertex on an " +
                "empty graph");
        }
        else {
            vertex = _getVertex(inLabel);
        }

        return vertex;
    }

    private DSAGraphVertex _getVertex(String inLabel) {
        Iterator verticesIter = vertices.iterator();
        DSAGraphVertex vertex = null;
        while (verticesIter.hasNext() && vertex == null) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            if (currentVertex.getLabel().equals(inLabel)) {
                vertex = currentVertex;
            }
            verticesIter.next();
        }

        return vertex;
    }

    public int getNumVertices() {
        int numVertices;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getNumVertices " + 
                "on an empty graph");
        }
        else {
            numVertices = _getNumVertices();
        }

        return numVertices;
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

    public int getNumEdges() {
        int numEdges;

        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call getNumEdges " +
                "an empty graph");
        }
        else {
            numEdges = _getNumEdges();
        }

        return numEdges;
    }

    private int _getNumEdges() {
        Iterator verticesIter = vertices.iterator();
        int numEdges = 0;
        while (verticesIter.hasNext()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            Iterator neighboursIter = currentVertex.getNeighbours().iterator();
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

    // OPERATORS

    public DSAQueue bfs() {
        DSAQueue next = new DSAQueue();
        DSAQueue visited = new DSAQueue();

        DSAGraphVertex startVertex = (DSAGraphVertex) vertices.peekFirst();

        // Prepares to search start vertex's neighbours
        next.enqueue(startVertex);
        // Prevents start vertex from being visited again
        startVertex.markVisited();
        // Adds start vertex to queue of visited vertices
        visited.enqueue(startVertex.getLabel());

        while (!next.isEmpty()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) next.dequeue();
            Iterator neighboursIter = currentVertex.getNeighbours().iterator();
            // Visits neighbours of current vertex
            while (neighboursIter.hasNext()) {
                DSAGraphVertex currentNeighbour = 
                    (DSAGraphVertex) neighboursIter.next();
                if (!currentNeighbour.wasVisited()) {
                    // Prepares to search current neighbour's neighbours
                    next.enqueue(currentNeighbour);
                    // Prevents current neighbour from being visited again
                    currentNeighbour.markVisited();
                    // Adds current neighbour to queue of visited vertices
                    visited.enqueue(currentNeighbour.getLabel());
                }
            }
        }
        // Prepares vertices for later reuse
        _markAllUnvisited();

        return visited;
    }

    private void _markAllUnvisited() {
        Iterator verticesIter = vertices.iterator();
        while (verticesIter.hasNext()) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            currentVertex.markUnvisited();
        }
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

    private void _dfsVisit(DSAGraphVertex currentVertex, DSAQueue visited) {
        // Prevents vertex from being visited again
        currentVertex.markVisited();
        // Adds current vertex to queue of visited vertices
        visited.enqueue(currentVertex.getLabel());

        Iterator neighboursIter = currentVertex.getNeighbours().iterator();
        while (neighboursIter.hasNext()) {
            DSAGraphVertex currentNeighbour = 
                (DSAGraphVertex) neighboursIter.next();
            // Visits neighbours of current neighbour
            if (!currentNeighbour.wasVisited()) {
                _dfsVisit(currentNeighbour, visited);
            }
        }
    }

    public void displayList() {
        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayList " + 
                "on an empty graph");
        }
        else {
            String[][] adjList = _makeAdjList();
            _printArray(adjList);
        }
    }

    private String[][] _makeAdjList() {
        Iterator verticesIter = vertices.iterator();
        int numVertices = getNumVertices();
        String[][] adjList = new String[numVertices][numVertices];

        for (int i = 0; i < adjList.length; i++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjList[i][0] = currentVertex.getLabel();
            Iterator neighboursIter = currentVertex.getNeighbours().iterator();
            
            for (int j = 1; j < adjList[0].length; j++) {
                DSAGraphVertex currentNeighbour = 
                    (DSAGraphVertex) neighboursIter.next();
                if (currentNeighbour == null) {
                    adjList[i][j] = "";
                }
                else {
                    adjList[i][j] = currentNeighbour.getLabel();
                }
            }
        }

        return adjList;
    }

    private void _printArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(" " + array[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void displayMatrix() {
        if (vertices.isEmpty()) {
            throw new IllegalArgumentException("Cannot call displayMatrix " + 
                "on an emtpy graph");
        }
        else {
            String[][] adjMatrix = _makeAdjMatrix();
            _printArray(adjMatrix);
        }
    }

    private String[][] _makeAdjMatrix() {
        Iterator verticesIter = vertices.iterator();
        int numVertices = getNumVertices();
        String[][] adjMatrix = new String[numVertices + 1][numVertices + 1];
        adjMatrix[0][0] = " ";

        // Adds y-labels
        for (int i = 1; i < adjMatrix.length; i++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjMatrix[i][0] = currentVertex.getLabel();
        }
        verticesIter = vertices.iterator();

        // Adds x-labels
        for (int j = 1; j < adjMatrix[0].length; j++) {
            DSAGraphVertex currentVertex = (DSAGraphVertex) verticesIter.next();
            adjMatrix[0][j] = currentVertex.getLabel();
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
}
