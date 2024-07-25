import java.util.List;
import java.util.ArrayList;

public class Computer {
    /**
     * Computes the total number of pairs of attacking queens
     */

    public static int computeCost(List<Integer> board) {
        int size = board.size();
        int totalNumOfPairs = 0;
        List<Integer> aBoard = new ArrayList<>();
        List<Integer> mBoard = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            aBoard.add(i + board.get(i));
            mBoard.add(i - board.get(i));
        }

        int aNum = 1;
        int mNum = 1;
        for (int i = 0; i < size - 1; i++) {
            int j = i + 1;

            if (mBoard.get(i) == mBoard.get(j)) {
                mNum++;
            }
            else {
                totalNumOfPairs += computeNumOfPairs(mNum);
                mNum = 1;
            }

            if (aBoard.get(i) == aBoard.get(j)) {
                aNum++;
            }
            else {
                totalNumOfPairs += computeNumOfPairs(aNum);
                aNum = 1;
            }

            if (j == size - 1) {
                totalNumOfPairs += computeNumOfPairs(aNum);
                totalNumOfPairs += computeNumOfPairs(mNum);
                break;
            }
        }

        return totalNumOfPairs;
    }

    /**
     * Computes the number of pairs of attacking queens
     */

    private static int computeNumOfPairs(int num) {
        int numOfPairs;

        if (num < 2) {
            numOfPairs = 0;
        }
        else if (num == 2) {
            numOfPairs = 1;
        }
        else {
            numOfPairs = (num - 1) * num / 2;
        }

        return numOfPairs;
    }   
}
