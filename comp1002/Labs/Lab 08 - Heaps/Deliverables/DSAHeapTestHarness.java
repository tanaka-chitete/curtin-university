/*
 * NAME: DSAHeapTestHarness
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Test DSAHeap functionality
 * CREATION: 20/10/2020
 * LAST MODIFICATION: 20/10/2020
 */

public class DSAHeapTestHarness {
    private static final int QUIT = 0;
    private static final int OPTION_1 = 1;
    private static final int OPTION_2 = 2;
    private static final int OPTION_3 = 3;
    private static final int OPTION_5 = 5;
    private static final String RANDOM_NAMES_10 = "RandomNames10.csv";
    private static final String RANDOM_NAMES_100 = "RandomNames100.csv";
    private static final String RANDOM_NAMES_1000 = "RandomNames1000.csv";

    public static void main(String[] args) {
        int sel;
        DSAHeap heap = null;
        do {
            System.out.println("Main Menu\n\n" + 
                "1. Heap Sort\n" +
                "2. Initialise\n" +
                "3. Add\n" +
                "4. Remove\n" +
                "5. Display\n" +
                "0. Quit\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(QUIT, OPTION_5, prompt);
            System.out.println();

            switch (sel) {
                // User input specifies "Heap Sort"
                case 1:
                    heap = heapSort();
                    break;
                // User input specified "Initialise"
                case 2:
                    heap = initialise();
                    break;
                // User input specifies "Add"
                case 3:
                    add(heap);
                    break;
                // User input specifies "Remove"
                case 4:
                    remove(heap);
                    break;
                // User input specifies "Display"
                case 5:
                    display(heap);
                    break;
            }
        }
        while (sel != QUIT);
    }
    
    /*
     * NAME: heapSort
     * IMPORT(S): NONE
     * EXPORT(S): heap (DSAHeap)
     * PURPOSE: Sorts arrays using Heapsort
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    private static DSAHeap heapSort() {
        int sel;
        do {
            System.out.println("Heap Sort\n\n" + 
                "1. RandomNames10.csv\n" + 
                "2. RandomNames100.csv\n" + 
                "3. RandomNames1000.csv\n");
            String prompt = "Selection: ";
            sel = UserInterface.userInput(OPTION_1, OPTION_3, prompt);
            System.out.println();
        }
        while (sel < OPTION_1 || sel > OPTION_3);

        DSAHeap heap = new DSAHeap();
        DSAHeapEntry[] values;
        // User input specifies "RandomNames10.csv"
        if (sel == OPTION_1) {
            values = FileIO.read(RANDOM_NAMES_10);
            heap.heapSort(values);
        }
        // User input specifies "RandomNames100.csv"
        else if (sel == OPTION_2) {
            values = FileIO.read(RANDOM_NAMES_100);
            heap.heapSort(values);
        }
        // User input specifies "RandomNames1000.csv"
        else {
            values = FileIO.read(RANDOM_NAMES_1000);
            heap.heapSort(values);
        }

        return heap;
    }

    /*
     * NAME: initialise
     * IMPORT(S): NONE
     * EXPORT(S): heap (DSAHeap)
     * PURPOSE: Inititialises heaps using user-specified capacity
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    private static DSAHeap initialise() {
        System.out.println("Initialise\n");

        String prompt = "Capacity: ";
        int capacity = UserInterface.userInput(0, Integer.MAX_VALUE, prompt);
        System.out.println();

        DSAHeap heap = new DSAHeap(capacity);

        return heap;
    }

    /*
     * NAME: add
     * IMPORT(S): heap (DSAHeap)
     * EXPORT(S): NONE
     * PURPOSE: Add values to heaps
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    private static void add(DSAHeap heap) {
        System.out.println("Add\n");

        String prompt = "Value: ";
        int priority = UserInterface.userInput(Integer.MIN_VALUE, Integer.MAX_VALUE, prompt);
        Object value = Integer.valueOf(priority);
        System.out.println();

        heap.add(priority, value);
    }

    /*
     * NAME: remove
     * IMPORT(S): heap (DSAHeap)
     * EXPORT(S): NONE
     * PURPOSE: Removes value of highest priority from heaps
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */    

    private static void remove(DSAHeap heap) {
        System.out.println("Remove\n");

        heap.remove();

        System.out.println("Highest priority value removed\n");
    }

    /*
     * NAME: display
     * IMPORT(S): heap (DSAHeap)
     * EXPORT(S): NONE
     * PURPOSE: Prints contents of heaps
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    private static void display(DSAHeap heap) {
        System.out.println("Display\n");

        // Heap hasn't been initialised yet
        if (heap == null) {
            System.out.println("Cannot call display on an unitialised heap\n");
        }
        // Heap has been initialised
        else {
            System.out.println(heap + "\n");
        }
    }
}
