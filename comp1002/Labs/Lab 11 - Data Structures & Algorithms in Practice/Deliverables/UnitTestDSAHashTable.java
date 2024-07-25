/*
 * NAME: UnitTestDSAHashTable
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Perform various operations in regards to hash tables
 * CREATION: 28/09/2020
 * LAST MODIFICATION: 04/11/2020
 */

public class UnitTestDSAHashTable {
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    public static final int OPTION_5 = 5;

    public static void main(String[] args) {
        int sel;
        DSAHashTable<String, Object> hashTable = new DSAHashTable<String, Object>();
        do {
            System.out.println("Main Menu\n\n" + 
                "1. Add\n" +
                "2. Remove\n" +
                "3. Display\n" +
                "0. Quit\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(QUIT, OPTION_5, prompt);

            switch (sel) {
                // User input specifies "Add"
                case 1:
                    put(hashTable);
                    break;
                // User input specifies "Remove"
                case 2:
                    remove(hashTable);
                    break;
                // User input specifies "Display"
                case 3:
                    display(hashTable);
                    break;
            }
        }
        while (sel != QUIT);
    }

    /*
     * NAME: addEntry
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Add entries into hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void put(DSAHashTable<String, Object> hashTable) {
        System.out.println("\nAdd Entry\n");

        String prompt = "Key: ";
        String key = UserInterface.userInput(prompt);

        prompt = "Value: ";
        String value = UserInterface.userInput(prompt);
        System.out.println();

        hashTable.put(key, value);
    }

    /*
     * NAME: removeEntry
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Remove entries from hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void remove(DSAHashTable<String, Object> hashTable) {
        System.out.println("\nRemove Entry\n");

        String prompt = "Key: ";
        String key = UserInterface.userInput(prompt);
        System.out.println();

        hashTable.remove(key);
    }

    /*
     * NAME: display
     * IMPORT(S): hashTable (DSAHashTable)
     * EXPORT(S): NONE
     * PURPOSE: Print hash table entries
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void display(DSAHashTable<String, Object> hashTable) {
        System.out.println("\nDisplay\n");

        System.out.println(hashTable);

        System.out.println("\nCount: " + hashTable.getCount());
        System.out.println("Capacity: " + hashTable.getCapacity());
        System.out.println("Load Factor: " + hashTable.getLoad() + "\n");
    }
}
