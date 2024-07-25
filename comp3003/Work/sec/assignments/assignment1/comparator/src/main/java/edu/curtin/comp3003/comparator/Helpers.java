package edu.curtin.comp3003.comparator;

import java.util.Scanner;

import java.io.File;
import java.io.IOException;

public class Helpers {
    // Adapted from David Cooper
    // Assignment 1 (Specification) | Software Engineering Concepts (COMP3003)
    // Accessed 13/09/2021
    public static double computeSimilarity(char[] contents1, char[] contents2) {
        int[][] subSolutions = new int[contents1.length + 1][contents2.length + 1];
        boolean[][] directionsLeft = new boolean[contents1.length + 1][contents2.length + 1];

        for (int i = 1; i <= contents1.length; i++) {
            for (int j = 1; j <= contents2.length; j++) {
                if (contents1[i - 1] == contents2[j - 1]) {
                    subSolutions[i][j] = subSolutions[i - 1][j - 1] + 1;
                } else if (subSolutions[i - 1][j] > subSolutions[i][j - 1]) {
                    subSolutions[i][j] = subSolutions[i - 1][j];
                    directionsLeft[i][j] = true;
                } else {
                    subSolutions[i][j] = subSolutions[i][j - 1];
                    directionsLeft[i][j] = false;
                }
            }
        }

        int matches = 0;
        int i = contents1.length;
        int j = contents2.length;

        while (i > 0 && j > 0) {
            if (contents1[i - 1] == contents2[j - 1]) {
                matches++;
                i--;
                j--;
            } else if (directionsLeft[i][j]) {
                i--;
            } else {
                j--;
            }
        }

        return (double)(matches * 2) / (double)(contents1.length + contents2.length);
    }
    // End of code adapted from above source

    // Adapted from darkpbj
    // Reading text file into char array in Java
    // Accessed 15/09/2021
    public static char[] extractContents(File file) {
        char[] contents = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            contents = new char[stringBuilder.length()];
            stringBuilder.getChars(0, stringBuilder.length(), contents, 0);

            scanner.close();
        } catch (IOException e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return contents;
    }
    // End of code adapted from above source
}
