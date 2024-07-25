/*
 * NAME: DSAHashTableProgram
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Perform various operations in regards to hash tables
 * CREATION: 28/09/2020
 * LAST MODIFICATION: 30/09/2020
 */

public class DSAHashTableProgram {
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    public static final int OPTION_5 = 5;

    public static void main(String[] args) {
        int sel;
        DSAHashTable hashTable = null;
        do {
            System.out.println("Main Menu\n\n" + 
                "1. Read\n" +
                "2. Add Entry\n" +
                "3. Remove Entry\n" +
                "4. Display\n" +
                "5. Write\n" +
                "0. Quit\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(QUIT, OPTION_5, prompt);

            switch (sel) {
                // User input specifies "Read"
                case 1:
                    hashTable = read();
                    break;
                // User input specifies "Add Entry"
                case 2:
                    addEntry(hashTable);
                    break;
                // User input specifies "Remove Entry"
                case 3:
                    removeEntry(hashTable);
                    break;
                // User input specifies "Display"
                case 4:
                    display(hashTable);
                    break;
                // User input specifies "Write"
                case 5:
                    write(hashTable);
                    break;
            }
        }
        while (sel != QUIT);
    }

    /*
     * NAME: read
     * IMPORT(S): NONE
     * EXPORT(S): hashTable (DSAHashTable)
     * PURPOSE: Make hash tables using CSV files
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static DSAHashTable read() {
        int sel;
        do {
            System.out.println("\nRead\n\n" + 
                "1. RandomNames10.csv\n" + 
                "2. RandomNames100.csv\n" + 
                "3. RandomNames1000.csv\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(OPTION_1, OPTION_3, prompt);
            System.out.println();
        }
        while (sel < OPTION_1 || sel > OPTION_3);

        DSAHashTable hashTable;
        // User input specifies "RandomNames10.csv"
        if (sel == OPTION_1) {
            hashTable = new DSAHashTable(10);
            hashTable.read("RandomNames10.csv");
        }
        // User input specifies "RandomNames100.csv"
        else if (sel == OPTION_2) {
            hashTable = new DSAHashTable(100);
            hashTable.read("RandomNames100.csv");
        }
        // User input specifies "RandomNames1000.csv"
        else {
            hashTable = new DSAHashTable(1000);
            hashTable.read("RandomNames1000.csv");
        }

        return hashTable;
    }

    /*
     * NAME: addEntry
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Add entries into hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void addEntry(DSAHashTable hashTable) {
        System.out.println("\nAdd Entry\n");

        String prompt = "Key: ";
        String key = UserInterface.userInput(prompt);
        prompt = "Value: ";
        String value = UserInterface.userInput(prompt);
        hashTable.addEntry(key, value);
        System.out.println();
    }

    /*
     * NAME: removeEntry
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Remove entries from hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void removeEntry(DSAHashTable hashTable) {
        System.out.println("\nRemove Entry\n");

        String prompt = "Key: ";
        String key = UserInterface.userInput(prompt);
        hashTable.removeEntry(key);
        System.out.println();
    }

    /*
     * NAME: display
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Print hash table entries
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void display(DSAHashTable hashTable) {
        System.out.println("\nDisplay\n");

        hashTable.display();

        System.out.println("\nCount: " + hashTable.getCount());
        System.out.println("Capacity: " + hashTable.getCapacity());
        System.out.println("Load Factor: " + hashTable.getLoad() + "\n");
    }

    /*
     * NAME: write
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Save hash tables CSV to files
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void write(DSAHashTable hashTable) {
        System.out.println("\nWrite\n");

        String prompt = "Filename: ";
        String filename = UserInterface.userInput(prompt);
        System.out.println();
        
        hashTable.write(filename);
    }
}
