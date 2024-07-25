/** 
 * Make Graph objects by reading in graph files
 * 
 * @author Tanaka Chitete
 */

import java.io.*;
import java.util.Map;
import java.util.HashMap;

public class FileIO {
    public static Graph readGraph(String edgesFilename, String heuristicsFilename) {
        Graph graph = new Graph();
        try {
            Map<Integer, Integer> heuristics = readHeuristics(heuristicsFilename);
            BufferedReader bReader = new BufferedReader(new FileReader(edgesFilename));
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                int sourceLabel = Integer.parseInt(splitLine[0]);
                int destinationLabel = Integer.parseInt(splitLine[1]);
                int weight = Integer.parseInt(splitLine[2]);
                graph.addEdge(sourceLabel,
                              heuristics.get(sourceLabel), 
                              destinationLabel, 
                              heuristics.get(destinationLabel),
                              weight);
            }
            bReader.close();
        }
        catch (IOException e) {
            graph = null;
        }

        return graph;
    }

    private static Map<Integer, Integer> readHeuristics(String heuristicsFilename) throws IOException {
        Map<Integer, Integer> heuristics = new HashMap<>();
        BufferedReader bReader = new BufferedReader(new FileReader(heuristicsFilename));
        String line;
        while ((line = bReader.readLine()) != null) {
            String[] splitLine = line.split(" ");
            Integer label = Integer.parseInt(splitLine[0]);
            Integer heuristic = Integer.parseInt(splitLine[1]);
            heuristics.put(label, heuristic);
        }
        bReader.close();

        return heuristics;
    }
}