/*
 * NAME: DSABinarySearchTreeProgram
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Perform various read and write operations of DSABinarySearchTrees
 * CREATION: 23/09/2020
 * LAST MODIFICATION: 23/09/2020
 */

public class DSABinarySearchTreeProgram {
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;

    public static void main(String[] args) {
        int sel;
        DSABinarySearchTree bst = null;
        do {
            System.out.println("Main Menu\n\n" + 
                "1. Read\n" +
                "2. Display\n" + 
                "3. Write\n" +
                "0. Exit\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(QUIT, OPTION_3, prompt);

            // sel specifies 'Read'
            if (sel == OPTION_1) {
                bst = read();
            }
            // sel specifies 'Display'
            else if (sel == OPTION_2) {
                bst.display();
            }
            // sel specifies 'Write'
            else if (sel == OPTION_3) {
                write(bst);
            }
        }
        while (sel != QUIT);
    }

    /*
     * NAME: read
     * IMPORT(S): NONE
     * EXPORT(S): bst (DSABinarySearchTree)
     * PURPOSE: Read CSV and TXT files into a BST
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    private static DSABinarySearchTree read() {
        int sel;
        String prompt, filename;
        DSABinarySearchTree bst = null;

        System.out.println("Read\n\n" +
            "1. CSV\n" + 
            "2. TXT\n");
        prompt = "Selection: ";
        sel = UserInterface.userInput(OPTION_1, OPTION_2, prompt);

        prompt = "filename: ";
        filename = UserInterface.userInput(prompt);

        // sel specifies 'CSV'
        if (sel == OPTION_1) {
            bst = FileIO.readCSV(filename);
        }
        // sel specifies 'TXT'
        else if (sel == OPTION_2) {
            bst = FileIO.readTXT(filename);
        }

        System.out.println("Binary Search Tree read from: " + filename);

        return bst;
    } 

    /*
     * NAME: write
     * IMPORT(S): bst (DSABinarySearchTree)
     * EXPORT(S): NONE
     * PURPOSE: Save bst to CSV file with preorder, inorder or postorder 
     *          traversal
     * CREATION: 23/09/2020
     * LAST MODIFICATION: 23/09/2020
     */

    private static void write(DSABinarySearchTree bst) {
        int sel;
        String prompt, filename;

        System.out.println("Write\n\n" + 
            "1. TXT\n" + 
            "2. CSV\n");
        prompt = "Selection: ";
        sel = UserInterface.userInput(OPTION_1, OPTION_2, prompt);

        // sel specifies 'TXT'
        if (sel == OPTION_1) {
            prompt = "filename: ";
            filename = UserInterface.userInput(prompt);
            filename += ".txt";

            FileIO.writeTXT(bst, filename);
            System.out.println("Binary Search Tree saved as: " + filename);
        }

        // sel specifies 'CSV'
        else if (sel == OPTION_2) {
            System.out.println("CSV\n\n" + 
                "1. Preorder\n" +
                "2. Inorder\n" + 
                "3. Postorder\n");
            prompt = "Selection: ";
            sel = UserInterface.userInput(OPTION_1, OPTION_3, prompt);

            prompt = "filename: ";
            filename = UserInterface.userInput(prompt);
            filename += ".csv";
            // sel specifies 'Preorder'
            DSAQueue bstQueue = null;
            if (sel == OPTION_1) {
                bstQueue = bst.traversePreorder();
            }
            // sel specifies 'Inorder'
            else if (sel == OPTION_2) {
                bstQueue = bst.traverseInorder();
            }
            // sel specifies 'Postorder'
            else if (sel == OPTION_3) {
                bstQueue = bst.traversePostorder();
            }

            FileIO.writeCSV(bstQueue, filename);
            System.out.println("Binary Search Tree saved as: " + filename);
        }
    }
}
