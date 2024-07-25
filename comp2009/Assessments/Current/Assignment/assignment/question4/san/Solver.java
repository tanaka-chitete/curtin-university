import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Solver {
    public static void solve(int temperature, int step) {
        List<Integer> current = makeInitialBoard();
        int costOfCurrent = Computer.computeCost(current);
        List<Integer> successor = new ArrayList<>();
        Random rand = new Random();
        int iteration = 0;

        Printer.printBoard(current, costOfCurrent, temperature, iteration); // Incase initial is solution
        while (temperature > Constants.FREEZING_TEMPERATURE && costOfCurrent != 0) {
            iteration++;
            temperature -= step;
            int randColOne;
            int randColTwo;
            successor = current;
            // Get two random queens to swap
            do {
                randColOne = rand.nextInt(Constants.NUM_QUEENS);
                randColTwo = rand.nextInt(Constants.NUM_QUEENS);
            }
            // Keep swapping until we get two queens in different rows
            while (successor.get(randColOne) == successor.get(randColTwo));
            
            // Swap the two queens we randomly selected
            Collections.swap(successor, randColOne, randColTwo);

            // Determine whether to accept sucessor based on cost delta (or objective function)
            int delta = Computer.computeCost(successor) - costOfCurrent;
            if (delta < 0) {
                current = successor;
                costOfCurrent = Computer.computeCost(current);
            }
            else {
                double p = Math.exp((double) -delta / temperature);
                double randomDouble = rand.nextDouble();

                if (randomDouble < p) {
                    current = successor;
                    costOfCurrent = Computer.computeCost(current);
                }
            }

            Printer.printBoard(current, costOfCurrent, temperature, iteration);
        }
    }

    private static List<Integer> makeInitialBoard() {
        // Create a random board to use as our initial state
        List<Integer> initial = new ArrayList<>();
        for (int i = 0; i < Constants.NUM_QUEENS; i++) {
            initial.add(i);
        }
        Collections.shuffle(initial);

        return initial;
    }
}
