/*
 * NAME: FileIO
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Make graphs using edges from files
 * CREATION: 23/09/2020
 * LAST MODIFICATION: 31/03/2021
 */

import java.io.*;

public class FileIO {
    /*
     * NAME: read
     * IMPORT(S): filename (String), directed (boolean)
     * EXPORT(S): graph (DSAGraph)
     * PURPOSE: Make graph using edges from a file
     * CREATION: 25/09/2020
     * LAST MODIFICATION: 31/03/2021
     */

    public static Graph read(String filename, boolean directed) {
        Graph graph = new Graph(directed);

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
                String sourceLabel = splitLn[0];
                String destinationLabel = splitLn[1];
                int weight = Integer.parseInt(splitLn[2]);

                graph.addEdge(sourceLabel, destinationLabel, weight);

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