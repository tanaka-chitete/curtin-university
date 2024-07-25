/*
 * NAME: FileIO
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Make graphs using edges from files
 * CREATION: 23/09/2020
 * LAST MODIFICATION: 23/09/2020
 */

import java.io.*;

public class FileIO {
    /*
     * NAME: read
     * IMPORT(S): filename (String)
     * EXPORT(S): graph (DSAGraph)
     * PURPOSE: Make graph using edges from a file
     * CREATION: 25/09/2020
     * LAST MODIFICATION: 25/09/2020
     */

    public static DSAGraph read(String filename, boolean isDirected) {
        DSAGraph graph = new DSAGraph(isDirected);

        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String ln;
        String[] splitLn = new String[2];

        try {
            fileStream = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            ln = bufRdr.readLine();

            // Adds vertices and edges using edges from file 
            while (ln != null) {
                splitLn = ln.split(" ");
                String sourceVertexLabel = splitLn[0];
                String sinkVertexLabel = splitLn[1];

                if (!graph.hasVertex(sourceVertexLabel)) {
                    graph.addVertex(sourceVertexLabel);
                }

                if (!graph.hasVertex(sinkVertexLabel)) {
                    graph.addVertex(sinkVertexLabel);
                }

                if (!graph.areNeighbours(sourceVertexLabel, sinkVertexLabel)) {
                    graph.addEdge(sourceVertexLabel, sinkVertexLabel);
                }

                ln = bufRdr.readLine();
            }
            bufRdr.close();
        }
        catch (IOException e)
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch (IOException ex2)
                { } 
            }
            throw new IllegalArgumentException("File reading unsuccessful");
        }

        return graph;
    }
}