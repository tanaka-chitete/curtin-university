/*
 * NAME: ReadAndWriteLinkedLists
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Read and write serialised linked lists
 * CREATION: 01/09/2020
 * LAST MODIFICATION: 01/09/2020
 */

import java.io.IOException;
import java.util.*;

public class ReadAndWriteLinkedLists {
    private static final int QUIT = 0;
    private static final int OPTION_4 = 4;

    public static void main(String[] args) {
        DSALinkedList linkedList = new DSALinkedList();
        insertMany(linkedList, 10);

        int selection;

        do {
            System.out.println("Main Menu\n\n" + 
                        
                               "1. Clear Linked List\n" + 
                               "2. Load Linked List from File\n" + 
                               "3. Display Linked List\n" +
                               "4. Save Linked List to File\n" +
                               "0. Quit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(QUIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                case 1:
                    linkedList = new DSALinkedList();
                    System.out.println("Linked List has been cleared\n");
                    break;
                case 2:
                    linkedList = readLinkedList();
                    break;
                case 3:
                    displayLinkedList(linkedList);
                    break;
                case 4:
                    writeLinkedList(linkedList);
                    break;
            }
        } while (selection != QUIT);
    }
    
    /*
     * NAME: readLinkedList
     * IMPORT(S): NONE
     * EXPORT(S): linkedList (DSALinkedList)
     * PURPOSE: Call appropriate submodules to read a serialised linked list
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    private static DSALinkedList readLinkedList() {
        DSALinkedList linkedList = new DSALinkedList();

        try {
            String prompt = "Filename: ";
            String filename = UserInterface.userInput(prompt);
            linkedList = FileIO.read(filename);
            System.out.println("File " + filename + " loaded\n");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
        }

        return linkedList;
    }

    /*
     * NAME: displayLinkedList
     * IMPORT(S): linkedList(DSALinkedList)
     * EXPORT(S): NONE
     * PURPOSE: Display linked list on the console
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    private static void displayLinkedList(DSALinkedList linkedList) {
        if (linkedList.isEmpty()) {
            System.out.println("Linked List is empty\n");
        }
        else {
            System.out.println("Linked List");

            Iterator linkedListIterator = linkedList.iterator();

            int i = 0;
            while (linkedListIterator.hasNext()) {
                System.out.println("[" + i + "]: " + linkedListIterator.next());
                i++;
            }
            System.out.println();
        }
    }    

    /*
     * NAME: writeLinkedList
     * IMPORT(S): linkedList (DSALinkedList)
     * EXPORT(S): NONE
     * PURPOSE: Call appropriate submodules to write a linked list to file
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    private static void writeLinkedList(DSALinkedList linkedList) {
        try {
            String prompt = "Filename: ";
            String filename = UserInterface.userInput(prompt);
            FileIO.write(linkedList, filename);
    
            System.out.println("File saved as " + filename + "\n");
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    /*
     * NAME: insertMany
     * IMPORT(S): linkedList (DSALinkedList), numInsertElements (int)
     * EXPORT(S): NONE
     * PURPOSE: Insert ints 0 to numInsertElements - 1 at tail of linkedList
     * CREATION: 01/09/2020
     * LAST MODIFICATION: 01/09/2020
     */

    private static void insertMany(DSALinkedList linkedList, 
                                   int numInsertElements) {
        for (int i = 0; i < numInsertElements; i++) {
            linkedList.insertLast(Integer.valueOf(i));
        }
    }
}