import java.util.IllegalFormatException;
import java.util.List;

public class AStar {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: astar <edges file> <heuristics file>");
        }
        else {
            aStar(args);
        }
    }

    private static void aStar(String[] args) {
        Graph g = FileIO.readGraph(args[0], args[1]);
        if (g == null) {
            System.err.println("Error: Could not generate graph");
        }
        else {
            displayPaths(g.aStar());
        }
    }

    private static void displayPaths(List<String> path) {
        if (path.isEmpty()) {
            System.out.println("No paths found");
        }
        else {
            System.out.println(path);
        }
    }
}
