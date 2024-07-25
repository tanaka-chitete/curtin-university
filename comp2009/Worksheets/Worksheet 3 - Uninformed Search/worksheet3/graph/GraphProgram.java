/*
 * NAME: GraphProgram
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP2009
 * PURPOSE: Make and display graphs using graph edges from files
 * CREATION: 25/09/2020
 * LAST MODIFICATION: 31/03/2021
 */

import java.util.*;

public class GraphProgram {
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;

    public static void main(String[] args) {
        int selection;
        Graph graph = null;
        do {
            System.out.println("Main Menu\n\n" +
                "1. Read\n" + 
                "2. Display\n" +
                "3. Search\n" + 
                "0. Quit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(QUIT, OPTION_3, prompt);
            System.out.println();

            // selection specifies 'Read'
            if (selection == OPTION_1) {
                graph = read();
            }
            // selection specifies 'Display'
            else if (selection == OPTION_2) {
                print(graph);
            }
            // selection speicies 'Search'
            else if (selection == OPTION_3) {
                search(graph);
            }
        }
        while (selection != QUIT);
    }

    /*
     * NAME: read
     * IMPORT(S): NONE
     * EXPORT(S): graph (DSAGraph)
     * PURPOSE: Make graphs using edges from a file
     * CREATION: 25/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    private static Graph read() {
        System.out.println("Direction\n\n" + 
            "1. Undirected\n" + 
            "2. Directed\n");
        String prompt = "Selection: ";
        int selection = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
        System.out.println();

        boolean directed;
        if (selection == OPTION_1) {
            directed = false;
        }
        else {
            directed = true;
        }

        prompt = "Filename: ";
        String filename = UserInterface.userInput(prompt);
        Graph graph = FileIO.read(filename, directed);
        System.out.println("\n" + filename + " read successfully\n");
        
        return graph;
    }

    /*
     * NAME: print
     * IMPORT(S): graph (DSAGraph)
     * EXPORT(S): NONE
     * PURPOSE: Display a graph as an adjacency list or adjacency matrix
     * CREATION: 25/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    private static void print(Graph graph) {
        int selection;

        System.out.println("Display\n\n" + 
            "1. Adjacency List\n" + 
            "2. Adjacency Matrix\n");
        String prompt = "Selection: ";
        selection = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
        System.out.println();

        // selection specifies 'Adjacency List'
        if (selection == OPTION_1) {
            System.out.println("Adjacency List");
            graph.printAdjacencyList();
        }
        // selection specifies 'Adjaceny Matrix' 
        else if (selection == OPTION_2) {
            System.out.println("Adjacency Matrix");
            graph.printAdjacencyMatrix();
        }
        System.out.println();
    }

    /*
     * NAME: search
     * IMPORT(S): graph (DSAGraph)
     * EXPORT(S): NONE
     * PURPOSE: Search a graph using BFS or DFS
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    private static void search(Graph graph) {
        int selection;

        System.out.println("Search\n\n" + 
            "1. BFS\n" + 
            "2. DFS\n");
        String prompt = "Selection: ";
        selection = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
        System.out.println();

        // selection specifies 'BFS'
        Queue<String> visitedNodes;
        if (selection == OPTION_1) {
            visitedNodes = graph.BFS();
            System.out.println("BFS\n" + visitedNodes + "\n");
            
        }
        // selection specifies 'DFS'
        else if (selection == OPTION_2) {
            visitedNodes = graph.DFS();
            System.out.println("DFS\n" + visitedNodes + "\n");
        }
    }
}