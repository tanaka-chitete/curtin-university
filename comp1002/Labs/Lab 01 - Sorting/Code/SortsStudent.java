/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class SortsStudent
{
    // bubble sort
    public static void bubbleSort(Student[] students)
    {
        int pass = 0;
        boolean sorted; // Assume sorted, find out if otherwise
        Student temp = new Student();

        do
        {
            sorted = true; // SORTED needs to be inside do-while, else error
            for (int i = 0; i < (students.length - 1) - pass; i++)
            {
                // Swap out-of-order elements I and I + 1
                if (students[i].getId() > students[i + 1].getId()) 
                {
                    temp = students[i];
                    students[i] = students[i + 1];
                    students[i + 1] = temp;
                    sorted = false; // Still need to continue sorting
                }
            }
            pass++; // Next pass
        }
        while (!sorted); // Stop sorting when A is sorted
    }//bubbleSort()

    // selection sort
    public static void selectionSort(Student[] students)
    {
        for (int n = 0; n < students.length - 1; n++) 
        {
            int minI = n;
            Student temp = new Student();

            // Ignore N - it is already MIN_I
            for (int j = n + 1; j < students.length; j++)
            {
                if (students[j].getId() < students[minI].getId())
                    minI = j; // Update newly-found minimum val position
            }
            temp = students[minI]; // Now, swap the values
            students[minI] = students[n];
            students[n] = temp;
        }
    }// selectionSort()

    // insertion sort
    public static void insertionSort(Student[] students)
    {
        for (int j = 1; j < students.length; j++) // Start inserting at J = 1
        {
            // Insert into sub-array to left of N 
            Student key = students[j];
            int i = j - 1; // Start from the last item and go backwords
            while (i >= 0 && (students[i].getId() > key.getId()))
            {
                students[i + 1] = students[i]; // Shuffle until correct location
                i--;
            }
            students[i + 1] = key;
        }
    }// insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {
    }//mergeSort()
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//mergeSortRecurse()
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
    }//merge()


    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {
    }//quickSort()
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
    }//quickSortRecurse()
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, 
                                      int pivotIdx)
    {
		return 0;	// TEMP - Replace this when you implement QuickSort
    }//doPartitioning


}//end Sorts calss
