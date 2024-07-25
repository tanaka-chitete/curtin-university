/*
 * NAME: FileIO
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Read and write BSTs from and to files, respectively
 * CREATION: 23/09/2020
 * LAST MODIFICATION: 23/09/2020
 */

import java.util.*;
import java.io.*;

public class FileIO {
    /*
     * NAME: readTXT
     * IMPORT(S): filename (String)
     * EXPORT(S): bst (DSABinarySearchTree)
     * PURPOSE: Deserialise DSABinarySearchTrees
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public static DSABinarySearchTree readTXT(String filename) {
        DSABinarySearchTree inBst = new DSABinarySearchTree();
        FileInputStream fileStream = null;

        try {
            fileStream = new FileInputStream(filename);
            ObjectInputStream objectStream = 
                new ObjectInputStream(fileStream);
            inBst = (DSABinarySearchTree) objectStream.readObject();
            objectStream.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class DSABinarySearchTree not found" + 
                               e.getMessage() + "\n");
        }
        catch (FileNotFoundException e) {
            System.out.println("File reading unsuccessful\n");
        }
        catch (IOException e) {
            if (fileStream != null) {
                try {
                    fileStream.close();
                }
                catch (IOException ex2)
                { } 
            }
            throw new IllegalArgumentException("File reading unsuccessful\n");
        }

        return inBst;
    } 

    /*
     * NAME: readCSV
     * IMPORT(S): filename (String)
     * EXPORT(S): bst (DSABinarySearchTree)
     * PURPOSE: Read CSVs into DSABinarySearchTrees
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public static DSABinarySearchTree readCSV(String filename) {
        DSABinarySearchTree bst = new DSABinarySearchTree();

        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String ln;
        String[] splitLn = new String[2];

        try {
            fileStream = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            ln = bufRdr.readLine();

            // Reads each line of CSV into bst
            while (ln != null) {
                // Splits line by ','
                splitLn = ln.split(",");
                // Copies splitLn into bst
                String key = splitLn[0];
                String value = splitLn[1];
                bst.insert(key, value);

                ln = bufRdr.readLine();
            }
            bufRdr.close();
        }
        catch (IOException e)
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch (IOException ex2)
                { } 
            }
            throw new IllegalArgumentException("File reading unsuccessful");
        }

        return bst;
    }

    /*
     * NAME: writeTXT
     * IMPORT(S): bst (DSABinarySearchTree), filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Serialise DSABinarySearchTrees
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public static void writeTXT(DSABinarySearchTree bst, String filename) 
    {
        FileOutputStream fileStream = null;

        try {
            // Underlying stream
            fileStream = new FileOutputStream(filename);
            // Serialisation stream
            ObjectOutputStream objectStream = 
                new ObjectOutputStream(fileStream); 
            // Serialise and save to file
            objectStream.writeObject(bst); 
            objectStream.close(); 
        }
        catch (IOException e) {
            if (fileStream != null) {
                try {
                    fileStream.close();
                }
                catch(IOException ex2)
                { }
            }
            throw new IllegalArgumentException("File writing unsuccessful\n");
        }
    }

    /*
     * NAME: writeCSV
     * IMPORT(S): bstQueue (DSAQueue), filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Save bstQueue as a csv file
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    public static void writeCSV(DSAQueue bstQueue, String filename) {
        FileOutputStream fileStream = null;
        PrintWriter pw;
        Iterator bstQueueItrtr = bstQueue.iterator();

        // Attempts to write lines of file
        try {
            fileStream = new FileOutputStream(filename);
            pw = new PrintWriter(fileStream);
            while (bstQueueItrtr.hasNext()) {
                pw.print(bstQueueItrtr.next());
                pw.print("\n");
            }
            pw.close();
        }
        catch (IOException e) {
            if (fileStream != null) {
                try {
                    fileStream.close();
                }
                catch(IOException ex2) { 
                }
            }
            throw new IllegalArgumentException("File writing unsuccessful");
        }
    }
}