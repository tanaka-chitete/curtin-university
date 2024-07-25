import java.util.IllegalFormatException;
import java.util.List;

public class Search {
    public static void main(String[] args) {
        switch (args.length) {
            case 1:
                getAllPaths(args);
                break;
            case 2:
                getAllLimitedPaths(args);
                break;
            default:
                System.err.println("Usage: dfs <filename> [limit]");
                break;
        }
    }

    private static void getAllPaths(String[] args) {
        Graph g = FileIO.read(args[0]);
        if (g == null) {
            System.err.println("Error: Could not generate graph");
        }
        else {
            displayPaths(g.getAllPaths());
        }
    }

    private static void getAllLimitedPaths(String[] args) {
        try {
            int limit = Integer.parseInt(args[1]);
            if (limit > 0) {
                Graph g = FileIO.read(args[0]);
                if (g == null) {
                    System.err.println("Error: Could not generate graph");
                }
                else {
                    displayPaths(g.getAllPaths(limit));
                }
            }
            else {
                System.err.format("Error: %d is an invalid limit", limit);
            }
        }
        catch (IllegalFormatException e) {
            System.err.format("Error: %s is not an integer%n", args[1]);
        }
    }

    private static void displayPaths(List<List<Integer>> allPaths) {
        if (allPaths.isEmpty()) {
            System.out.println("No paths found");
        }
        else {
            for (List<Integer> p : allPaths) {
                System.out.println(p);
            }
        }
    }
}
