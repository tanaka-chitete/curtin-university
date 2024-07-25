/*
 * NAME: FileIO
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Read and write linked lists from and to files, respectively
 * CREATION: 01/09/2020
 * LAST MODIFICATION: 01/09/2020
 */

import java.io.*;

public class FileIO {
    /*
     * NAME: read
     * IMPORT(S): filename (String)
     * EXPORT(S): linkedList (DSALinkedList)
     * PURPOSE: Deserialise DSALinkedLists
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public static DSALinkedList read(String filename) {
        DSALinkedList inLinkedList = new DSALinkedList();
        FileInputStream fileStream = null;

        try {
            fileStream = new FileInputStream(filename);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            inLinkedList = (DSALinkedList)objectStream.readObject();
            objectStream.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Class DSALinkedList not found" + 
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

        return inLinkedList;
    } 

    /*
     * NAME: write
     * IMPORT(S): linkedList (DSALinkedList), filename (String)
     * EXPORT(S): NONE
     * PURPOSE: Serialise DSALinkedLists
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public static void write(DSALinkedList linkedList, String filename) {
        FileOutputStream fileStream = null;

        try {
            // Underlying stream
            fileStream = new FileOutputStream(filename);
            ObjectOutputStream objectStream = 
                new ObjectOutputStream(fileStream); // Serialisation stream
            objectStream.writeObject(linkedList); // Serialise and save to file
            objectStream.close(); // Cleanup
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
}