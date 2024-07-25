import java.util.*;

public class GraphUnitTest {
    public static void main(String[] args) {
        try {
            Graph undirectedGraph = new Graph();
            Graph directedGraph = new Graph(true);

            System.out.println("UNDIRECTED GRAPH\n");
            testAddNode(undirectedGraph);
            testAddEdge(undirectedGraph);
            testPrintAdjacencyList(undirectedGraph);
            testPrintAdjacencyMatrix(undirectedGraph);
            testHasNode(undirectedGraph);
            testHasEdge(undirectedGraph);
            testGetNodeCount(undirectedGraph);
            testGetEdgeCount(undirectedGraph);
            testBFS(undirectedGraph);
            testDFS(undirectedGraph);

            System.out.println("DIRECTED GRAPH\n");
            testAddNode(directedGraph);
            testAddEdge(directedGraph);
            testPrintAdjacencyList(directedGraph);
            testPrintAdjacencyMatrix(directedGraph);
            testHasNode(directedGraph);
            testHasEdge(directedGraph);
            testGetNodeCount(directedGraph);
            testGetEdgeCount(directedGraph);
            testBFS(directedGraph);
            testDFS(directedGraph);
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testAddNode(Graph graph) {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");
        graph.addNode("F");
        graph.addNode("G");
        graph.addNode("S");
    }

    private static void testAddEdge(Graph graph) {
        graph.addEdge("A", "B", 5);
        graph.addEdge("A", "S", 6);
        graph.addEdge("C", "D", 1);
        graph.addEdge("C", "E", 0);
        graph.addEdge("C", "F", 9);
        graph.addEdge("C", "S", 4);
        graph.addEdge("F", "G", 2);
        graph.addEdge("S", "G", 7);
    }

    private static void testPrintAdjacencyList(Graph graph) {
        System.out.println("printAdjacencyList");
        graph.printAdjacencyList();
        System.out.println();
    }

    private static void testPrintAdjacencyMatrix(Graph graph) {
        System.out.println("printAdjacencyMatrix");
        graph.printAdjacencyMatrix();
        System.out.println();
    }

    private static void testHasNode(Graph graph) {
        System.out.println("hasNode");
        System.out.println("D: " + graph.hasNode("D"));
        System.out.println("F: " + graph.hasNode("F"));
        System.out.println("K: " + graph.hasNode("K") + "\n");
    }

    private static void testHasEdge(Graph graph) {
        System.out.println("hasEdge");
        System.out.println("D,F: " + graph.hasEdge("D", "F"));
        System.out.println("F,G: " + graph.hasEdge("F", "G"));
        System.out.println("G,F: " + graph.hasEdge("G", "F") + "\n");
    }

    private static void testGetNodeCount(Graph graph) {
        System.out.println("getNodeCount");
        System.out.println(graph.getNodeCount() + "\n");
    }

    private static void testGetEdgeCount(Graph graph) {
        System.out.println("getNumEdges");
        System.out.println(graph.getNodeCount() + "\n");
    }

    private static void testBFS(Graph graph) {
        Queue<String> visitedNodes = graph.BFS();
        System.out.println("BFS\n" + visitedNodes + "\n");
    }

    private static void testDFS(Graph graph) {
        Queue<String> visitedNodes = graph.DFS();
        System.out.println("DFS\n" + visitedNodes + "\n");
    }
}