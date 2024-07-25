import java.util.ArrayList;
import java.util.List;

public class NQueensSolver {
    public static List<List<String>> solve(int n) {
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        List<List<String>> solutions = new ArrayList<>();
        solve(board, 0, solutions);

        return solutions;
    }

    private static void solve(char[][] board, int rowIndex, List<List<String>> solutions) {
        if (rowIndex == board.length) {
            solutions.add(construct(board)); // Our goal
        }
        else {
            for (int columnIndex = 0; columnIndex < board[0].length; columnIndex++) {
                // Our constraints (START)
                if (isValid(board, rowIndex, columnIndex)) {
                    board[rowIndex][columnIndex] = 'Q'; // Our choice
                    solve(board, rowIndex + 1, solutions);
                    board[rowIndex][columnIndex] = '.'; // Undo our choice
                }
                // Our constraints (END)
            }
        }
    }

    private static boolean isValid(char[][] board, int queenRowIndex, int queenColumnIndex) {
        // Check if column contains another queen
        for (int rowIndex = 0; rowIndex < queenRowIndex; rowIndex++) {
            if (board[rowIndex][queenColumnIndex] == 'Q') {
                return false;
            }
        }

        // Check if 45° diagonal contains another queen
        for (int rowIndex = queenRowIndex - 1, columnIndex = queenColumnIndex - 1; 
                 rowIndex >= 0 && columnIndex >= 0; 
                 rowIndex--, columnIndex--) {
            if (board[rowIndex][columnIndex] == 'Q') {
                return false;
            }
        }

        // Check if 135° diagonal contains another queen
        for (int rowIndex = queenRowIndex - 1, columnIndex = queenColumnIndex + 1;
                 rowIndex >= 0 && columnIndex < board.length;
                 rowIndex--, columnIndex++) {
            if (board[rowIndex][columnIndex] == 'Q') {
                return false;
            }
        }

        return true;
    }

    private static List<String> construct(char[][] board) {
        List<String> solution = new ArrayList<String>();
        for (int i = 0; i < board.length; i++) {
            String row = new String(board[i]);
            solution.add(row);
        }

        return solution;
    }
}