/** 
 * Make Graph objects by reading in graph files
 * 
 * @author Tanaka Chitete
 */

import java.io.*;

public class FileIO {
    public static Graph read(String filename) {
        Graph graph = new Graph();
        try {
            BufferedReader bReader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                int sourceLabel = Integer.parseInt(splitLine[0]);
                int destinationLabel = Integer.parseInt(splitLine[1]);
                graph.addEdge(sourceLabel, destinationLabel);
            }
            bReader.close();
        }
        catch (IOException e) {
            graph = null;
        }

        return graph;
    }
}