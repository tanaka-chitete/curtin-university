// Obtained from Tanaka Chitete
// (accessed 22/10/2020)

/*
 * NAME: UnitTestDSAHashSet
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Perform various operations in regards to hash set
 * CREATION: 28/09/2020
 * LAST MODIFICATION: 22/10/2020
 */

public class UnitTestDSAHashSet {
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;
    public static final int OPTION_3 = 3;
    public static final int OPTION_6 = 6;
    public static final String RANDOM_NAMES_10 = "RandomNames10.csv";
    public static final String RANDOM_NAMES_100 = "RandomNames100.csv";
    public static final String RANDOM_NAMES_1000 = "RandomNames1000.csv";

    public static void main(String[] args) {
        int sel;
        DSAHashSet hashSet = null;
        do {
            System.out.println("Main Menu\n\n" + 
                "1. Read\n" +
                "2. Add Entry\n" +
                "3. Remove Entry\n" +
                "4. Contains\n" +
                "5. Display\n" +
                "6. Write\n" +
                "0. Quit\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(QUIT, OPTION_6, prompt);

            switch (sel) {
                // User input specifies "Read"
                case 1:
                    hashSet = read();
                    break;
                // User input specifies "Add Entry"
                case 2:
                    add(hashSet);
                    break;
                // User input specifies "Remove Entry"
                case 3:
                    remove(hashSet);
                    break;
                // User input specifies "Contains"
                case 4:
                    contains(hashSet);
                    break;
                // User input specifies "Display"
                case 5:
                    display(hashSet);
                    break;
                // User input specifies "Write"
                case 6:
                    write(hashSet);
                    break;
            }
        }
        while (sel != QUIT);
    }

    /*
     * NAME: read
     * IMPORT(S): NONE
     * EXPORT(S): hashSet (DSAHashSet)
     * PURPOSE: Make hash tables using CSV files
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static DSAHashSet read() {
        DSAHashSet hashSet = new DSAHashSet(100); 
        hashSet.read(RANDOM_NAMES_100);

        return hashSet;
    }

    /*
     * NAME: addEntry
     * IMPORT(S): hashSet (DSAHashSet)
     * EXPORT(S): NONE
     * PURPOSE: Add entries into hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 22/10/2020
     */

    private static void add(DSAHashSet hashSet) {
        System.out.println("\nAdd Entry\n");

        try {
            String prompt = "Value: ";
            String value = UserInterface.userInput(prompt);
            hashSet.add(value);
            System.out.println();
        }
        catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage() + "\n");
        }

    }

    /*
     * NAME: removeEntry
     * IMPORT(S): hashSet (DSAHashSet)
     * EXPORT(S): NONE
     * PURPOSE: Remove entries from hash tables
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 22/10/2020
     */

    private static void remove(DSAHashSet hashSet) {
        System.out.println("\nRemove Entry\n");

        String prompt = "Value: ";
        String value = UserInterface.userInput(prompt);
        hashSet.remove(value);
        System.out.println();
    }

    /*
     * NAME: contains
     * IMPORT(S): hashSet (DSAHashSet)
     * EXPORT(S): NONE
     * PURPOSE: Checks if an entry is present in a hash set
     * CREATION: 22/10/2020
     * LAST MODIFICATION: 22/10/2020
     */

    private static void contains(DSAHashSet hashSet) {
        System.out.println("\nContains\n");

        String prompt = "Value: ";
        String value = UserInterface.userInput(prompt);
        System.out.println();

        boolean contains = hashSet.contains(value);
        if (contains) {
            System.out.println(value + " is present in the hash set\n");
        }
        else {
            System.out.println(value + " is not present in the hash set\n");
        }
    }

    /*
     * NAME: display
     * IMPORT(S): hashSet (DSAHashSet)
     * EXPORT(S): NONE
     * PURPOSE: Print hash table entries
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void display(DSAHashSet hashSet) {
        System.out.println("\nDisplay\n");

        System.out.println(hashSet);
        System.out.println("\nCount: " + hashSet.getCount());
        System.out.println("Load Factor: " + hashSet.getLoad() + "\n");
    }

    /*
     * NAME: write
     * IMPORT(S): hashSet (DSAHashSet)
     * EXPORT(S): NONE
     * PURPOSE: Save hash tables CSV to files
     * CREATION: 28/09/2020
     * LAST MODIFICATION: 28/09/2020
     */

    private static void write(DSAHashSet hashSet) {
        System.out.println("\nWrite\n");

        String prompt = "Filename: ";
        String filename = UserInterface.userInput(prompt);
        System.out.println();
        
        hashSet.write(filename);
    }
}
