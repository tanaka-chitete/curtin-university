/*
 * NAME: FileIO
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Read and write .csv files into DSAHeaps and from DSAHeaps, respectively
 * CREATION: 19/10/2020
 * LAST MODIFICATION: 19/10/2020
 */

import java.io.*;

public class FileIO {
    /*
     * NAME: read
     * IMPORT(S): filename (String)
     * EXPORT(S): String filename
     * PURPOSE: Read .csv files into DSAHeapEntry arrays
     * CREATION: 19/10/2020
     * LAST MODIFICATION: 19/10/2020
     */

    public static DSAHeapEntry[] read(String filename) {
        int lineCount = _countLines(filename);
        DSAHeapEntry[] heap = new DSAHeapEntry[lineCount];

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;
        String[] splitLine = new String[2];

        try {
            fileInputStream = new FileInputStream(filename);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();

            // Reads lines into array
            int i = 0;
            while (line != null) {
                splitLine = line.split(",");
                int inPriority = Integer.parseInt(splitLine[0]);
                Object inValue = inPriority;

                // Adds key as priority and value into heap
                DSAHeapEntry newEntry = new DSAHeapEntry(inPriority, inValue);
                heap[i] = newEntry;

                line = bufferedReader.readLine();

                i++;
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException ex2) { 
                } 
            }
            throw new IllegalArgumentException("File reading unsuccessful");
        }

        return heap;
    }

    /*
     * NAME: _countLines
     * IMPORT(S): filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Count the number of lines in a file
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    private static int _countLines(String filename) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line;

        int lineCount = 0;

        try {
            fileInputStream = new FileInputStream(filename);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            line = bufferedReader.readLine();

            // Counts lines
            while (line != null) {
                lineCount++;

                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (IOException e2) {
                }
            }
            System.out.println("File line-counting unsuccessful");
        }

        return lineCount;
    }
}