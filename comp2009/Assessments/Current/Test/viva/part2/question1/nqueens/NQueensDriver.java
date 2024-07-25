import java.util.List;

public class NQueensDriver {
    public static void main(String[] args) {
        int n = 4;

        List<List<String>> solutions = NQueensSolver.solve(n);
        printSolutions(solutions);
    }

    private static void printSolutions(List<List<String>> solutions) {
        for (List<String> solution : solutions) {
            for (String row : solution) {
                System.out.println(row);
            }
            System.out.println();
        }
    }
}
