import java.util.List;
import java.util.ArrayList;

public class Printer {
    public static void printBoard(List<Integer> oneDimensionalBoard, 
                                  int costOfBoard, 
                                  int temperature, 
                                  int iteration) {
        List<List<Character>> twoDimensionalBoard = makeTwoDimensionalBoard(oneDimensionalBoard);
        int n = twoDimensionalBoard.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(twoDimensionalBoard.get(i).get(j));
            }
            System.out.println();
        }
        System.out.format("Number of Pairs of Attacking Queens: %d%n", costOfBoard);
        System.out.format("Iteration:                           %d%n", iteration);
        System.out.format("Temperature:                         %d%n", temperature);
        System.out.println(); // For the sake of formatting
    }
    
    private static List<List<Character>> makeTwoDimensionalBoard(List<Integer> oneDimensionalBoard) {
        int n = oneDimensionalBoard.size();
        List<List<Character>> twoDimensionalBoard = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Character> contentsOfRowI = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                contentsOfRowI.add('_');
            }
            twoDimensionalBoard.add(contentsOfRowI);
        }

        for (int i = 0; i < n; i++) {
            List<Character> contentsOfRowI = twoDimensionalBoard.get(oneDimensionalBoard.get(i));
            contentsOfRowI.add(i, 'Q');
        }

        return twoDimensionalBoard;
    }
}
