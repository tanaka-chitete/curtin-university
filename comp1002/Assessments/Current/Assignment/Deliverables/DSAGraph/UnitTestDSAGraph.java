// Obtained from Tanaka Chitete
// (accessed 15/10/2020)

import java.util.*;

public class UnitTestDSAGraph {
    public static void main(String[] args) {
        try {
            DSAGraph undirectedGraph = new DSAGraph();
            DSAGraph directedGraph = new DSAGraph(true);

            System.out.println("UNDIRECTED GRAPH\n");
            testAddVertex(undirectedGraph);
            testAddEdge(undirectedGraph);
            testDisplayList(undirectedGraph);
            testDisplayMatrix(undirectedGraph);
            testHasVertex(undirectedGraph);
            testAreNeighbours(undirectedGraph);
            testGetNumVertices(undirectedGraph);
            testGetNumEdges(undirectedGraph);
            testBfs(undirectedGraph);
            testDfs(undirectedGraph);

            System.out.println("DIRECTED GRAPH\n");
            testAddVertex(directedGraph);
            testAddEdge(directedGraph);
            testDisplayList(directedGraph);
            testDisplayMatrix(directedGraph);
            testHasVertex(directedGraph);
            testAreNeighbours(directedGraph);
            testGetNumVertices(directedGraph);
            testGetNumEdges(directedGraph);
            testBfs(directedGraph);
            testDfs(directedGraph);
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAddVertex(DSAGraph graph) {
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("S");
    }

    private static void testAddEdge(DSAGraph graph) {
        graph.addEdge("A", "B");
        graph.addEdge("A", "S");
        graph.addEdge("C", "D");
        graph.addEdge("C", "E");
        graph.addEdge("C", "F");
        graph.addEdge("C", "S");
        graph.addEdge("F", "G");
        graph.addEdge("S", "G");
    }

    private static void testDisplayList(DSAGraph graph) {
        System.out.println("displayList");
        graph.displayList();
        System.out.println();
    }

    private static void testDisplayMatrix(DSAGraph graph) {
        System.out.println("displayMatrix");
        graph.displayMatrix();
        System.out.println();
    }

    private static void testHasVertex(DSAGraph graph) {
        System.out.println("hasVertex");
        System.out.println("D: " + graph.hasVertex("D"));
        System.out.println("F: " + graph.hasVertex("F"));
        System.out.println("K: " + graph.hasVertex("K") + "\n");
    }

    private static void testAreNeighbours(DSAGraph graph) {
        System.out.println("areNeighbours");
        System.out.println("D,F: " + graph.areNeighbours("D", "F"));
        System.out.println("F,G: " + graph.areNeighbours("F", "G"));
        System.out.println("B,C: " + graph.areNeighbours("B", "C") + "\n");
    }

    private static void testGetNumVertices(DSAGraph graph) {
        System.out.println("getNumVertices");
        System.out.println(graph.getNumVertices() + "\n");
    }

    private static void testGetNumEdges(DSAGraph graph) {
        System.out.println("getNumEdges");
        System.out.println(graph.getNumEdges() + "\n");
    }

    private static void testBfs(DSAGraph graph) {
        DSAQueue visited = graph.bfs();
        System.out.println("bfs\n" + visited + "\n");
    }

    private static void testDfs(DSAGraph graph) {
        DSAQueue visited = graph.dfs();
        System.out.println("dfs\n" + visited + "\n");
    }
}