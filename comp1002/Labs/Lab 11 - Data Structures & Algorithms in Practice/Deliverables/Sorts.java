/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{
    // bubble sort
    public static void bubbleSort(int[] A)
    {
        int pass = 0;
        boolean sorted; // Assume sorted, find out if otherwise
        do
        {
            sorted = true; // SORTED needs to be inside do-while, else error
            for (int i = 0; i < (A.length - 1) - pass; i++)
            {
                if (A[i] > A[i + 1]) // Swap out-of-order elements I and I + 1 
                {
                    int temp = A[i];
                    A[i] = A[i + 1];
                    A[i + 1] = temp;
                    sorted = false; // Still need to continue sorting
                }
            }
            pass++; // Next pass
        }
        while (!sorted); // Stop sorting when A is sorted
    }//bubbleSort()

    // selection sort
    public static void selectionSort(int[] A)
    {
        for (int n = 0; n < A.length - 1; n++) 
        {
            int minI = n;
            // Ignore N - it is already MIN_I
            for (int j = n + 1; j < A.length; j++)
            {
                if (A[j] < A[minI])
                    minI = j; // Update newly-found minimum val position
            }
            int temp = A[minI]; // Now, swap the values
            A[minI] = A[n];
            A[n] = temp;
        }
    }// selectionSort()

    // insertion sort
    public static void insertionSort(int[] A)
    {
        for (int j = 1; j < A.length; j++) // Start inserting at J = 1
        {
            int key = A[j];
            // Insert into sub-array to left of N 
            int i = j - 1; // Start from the last item and go backwords
            while ((i >= 0) && (A[i] > key))
            {
                A[i + 1] = A[i]; // Shuffle until correct location
                i--;
            }
            A[i + 1] = key;
        }
    }// insertionSort()

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {
        int leftIdx = 0;
        int rightIdx = A.length - 1;
        mergeSortRecurse(A, new int[A.length], leftIdx, rightIdx);
    }//mergeSort()
    private static void mergeSortRecurse(int[] A, int[] temp, int leftStartIdx, int rightEndIdx)
    {
        if (leftStartIdx < rightEndIdx) {
            int midIdx = (leftStartIdx + rightEndIdx) / 2;

            // Sorts LHS of current sub-array
            mergeSortRecurse(A, temp, leftStartIdx, midIdx);
            // Sorts RHS of current sub-array
            mergeSortRecurse(A, temp, midIdx + 1, rightEndIdx);

            merge(A, temp, leftStartIdx, rightEndIdx);
        }
    }//mergeSortRecurse()
    private static void merge(int[] A, int[] temp, int leftStartIdx, int rightEndIdx)
    {
        int leftEndIdx = (rightEndIdx + leftStartIdx) / 2;
        int rightStartIdx = leftEndIdx + 1;
        int size = rightEndIdx - leftStartIdx + 1;

        int leftIdx = leftStartIdx;
        int rightIdx = rightStartIdx;
        int i = leftStartIdx;

        // Merges sub-arrays into temporary array
        while (leftIdx <= leftEndIdx && rightIdx <= rightEndIdx) {
            // Using '<=' allows for a stable sort
            if (A[leftIdx] <= A[rightIdx]) {
                // Takes from LHS sub-array
                temp[i] = A[leftIdx];
                leftIdx++;
            }
            else {
                // Takes from RHS sub-array
                temp[i] = A[leftIdx];
                rightIdx++;
            }
            i++;
        }

        // Flushes remaining elements from LHS sub-array
        System.arraycopy(A, leftIdx, temp, i, leftEndIdx - leftIdx + 1);
        // Flushes remaining elements from RHS sub-array
        System.arraycopy(A, rightIdx, temp, i, rightEndIdx - rightIdx + 1);
        
        System.arraycopy(temp, leftStartIdx, A, leftStartIdx, size);
    }//merge()

    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {
        int leftIdx = 0;
        int rightIdx = A.length - 1;
        quickSortRecurse(A, leftIdx, rightIdx);
    }//quickSort()
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
        // Checks if sub-array is greater than one element in size
        if (leftIdx < rightIdx) {
            // Pivot-selection strategy: middle element
            int pivotIdx = (leftIdx + rightIdx) / 2;
            int newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);

            // Sorts LHS partition
            quickSortRecurse(A, leftIdx, newPivotIdx - 1);
            // Sorts RHS partition
            quickSortRecurse(A, newPivotIdx + 1, rightIdx);
        }
    }//quickSortRecurse()
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, 
                                      int pivotIdx)
    {
        // Swaps pivot value with right-most element
        int pivotVal = A[pivotIdx];
        A[pivotIdx] = A[rightIdx];
        A[rightIdx] = pivotVal;

        // Finds all values that are smaller than the pivot and transfers them
        // to the LHS of array
        int currIdx = leftIdx;
        for (int i = leftIdx; i < rightIdx; i++) {
            // Finds next value that should go in the LHS
            if (A[i] < pivotVal) {
                // Places current value on the LHS
                int temp = A[i];
                A[i] = A[currIdx];
                A[currIdx] = temp;

                currIdx++;
            }
        }
        // Puts pivot value into its rightful place
        int newPivotIdx = currIdx;
        A[rightIdx] = A[newPivotIdx];
        A[newPivotIdx] = pivotVal;

        return newPivotIdx;
    }//doPartitioning

    // quickSortM3 - front-end for kick-starting the recursive algorithm
    public static void quickSortM3(int[] A)
    {
        int leftIdx = 0;
        int rightIdx = A.length - 1;
        quickSortRecurseM3(A, leftIdx, rightIdx);
    }//quickSortM3()
    private static void quickSortRecurseM3(int[] A, int leftIdx, int rightIdx)
    {
        // Checks if sub-array is greater than one element in size
        if (leftIdx < rightIdx) {
            // Pivot-selection strategy: median of three
            int pivotIdx = M3(A, leftIdx, rightIdx);
            int newPivotIdx = doPartitioningM3(A, leftIdx, rightIdx, pivotIdx);

            // Sorts LHS partition
            quickSortRecurseM3(A, leftIdx, newPivotIdx - 1);
            // Sorts RHS partition
            quickSortRecurseM3(A, newPivotIdx + 1, rightIdx);
        }
    }//quickSortRecurseM3()
    private static int doPartitioningM3(int[] A, int leftIdx, int rightIdx, 
                                      int pivotIdx)
    {
        // Swaps pivot value with right-most element
        int pivotVal = A[pivotIdx];
        A[pivotIdx] = A[rightIdx];
        A[rightIdx] = pivotVal;

        // Finds all values that are smaller than the pivot and transfers them
        // to the LHS of array
        int currIdx = leftIdx;
        for (int i = leftIdx; i < rightIdx; i++) {
            // Finds next value that should go in the LHS
            if (A[i] < pivotVal) {
                // Places current value on the LHS
                int temp = A[i];
                A[i] = A[currIdx];
                A[currIdx] = temp;

                currIdx++;
            }
        }
        // Puts pivot value into its rightful place
        int newPivotIdx = currIdx;
        A[rightIdx] = A[newPivotIdx];
        A[newPivotIdx] = pivotVal;

        return newPivotIdx;
    }//doPartitioning
    private static int M3(int[] A, int leftIdx, int rightIdx) {
        int midIdx = (leftIdx + rightIdx) / 2;

        // Orders left and middle
        if (A[leftIdx] > A[midIdx]) {
            swap(A, leftIdx, midIdx);
        }
        // Orders left and right
        if (A[leftIdx] > A[rightIdx]) {
            swap(A, leftIdx, rightIdx);
        }
        // Orders middle and right
        if (A[midIdx] > A[rightIdx]) {
            swap(A, leftIdx, rightIdx);
        }

        swap(A, midIdx, rightIdx - 1);

        int newPivotIdx = rightIdx - 1;

        return newPivotIdx;
    }//M3
    private static void swap(int[] A, int x, int y) {
        int temp = A[x];
        A[x] = A[y];
        A[y] = temp;
    }//swap()

    // quickSort3W - front-end for kick-starting the recursive algorithm
    public static void quickSort3W(int[] A)
    {
        int leftIdx = 0;
        int rightIdx = A.length - 1;
        quickSortRecurse3W(A, leftIdx, rightIdx);
    }//quickSort3w()
    private static void quickSortRecurse3W(int[] A, int leftIdx, int rightIdx)
    {
        // Checks if sub-array is greater than one element in size
        if (leftIdx < rightIdx) {

            int lowerIdx = leftIdx;
            int higherIdx = rightIdx;
            int element = A[leftIdx];
            int i = leftIdx + 1;

            while (i <= higherIdx) {
                if (A[i] < element) {
                    swap(A, lowerIdx++, i++);
                }
                else if (A[i] > element) {
                    swap(A, i, higherIdx--);
                }
                else {
                    i++;
                }
            }

            quickSortRecurse3W(A, leftIdx, lowerIdx - 1);
            quickSortRecurse3W(A, higherIdx + 1, rightIdx);
        }
    }//quickSortRecurse3W()

    // shellSort
    // Adapted from GeeksforGeeks
    // https://www.geeksforgeeks.org/shellsort/
    // Accessed 03/11/2020
    public static void shellSort(int[] A) 
    {
        int n = A.length;

        // Starts with a big gap, and halve it after each iteration
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // Does a gapped insertion sort at current gap size
            // The first gap elements A[0...gap-1] are already in gapped order
            // Keeps adding one more element until the entire array is gap 
            // sorted
            for (int i = gap; i < n; i++) {
                // Adds A[i] to all the elements that have been gap sorted,
                // saves A[i] in temp and makes an opening at position i 
                int temp = A[i];

                // Shifts earlier gap-sorted elements up until the correct
                // location for A[i] is found
                int j;
                for (j = i; j >= gap && A[j - gap] > temp; j -= gap) {
                    A[j] = A[j - gap];
                }

                // Puts temp (the original A[i]) in its correct location
                A[j] = temp;
            }
        }
    }
    // End of code adapted from GeeksforGeeks

    // Adapted from Programiz
    // https://www.programiz.com/dsa/counting-sort#code
    // Accessed 03/11/2020
    public static void countingSort(int[] A) {
        int[] B = new int[A.length + 1];

        // Finds largest element of A
        int max = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }

        // Stores the count of each element
        int[] counts = new int[max + 1]; // int[] are initialised with 0s
        for (int i = 0; i < A.length; i++) {
            counts[A[i]] += 1;
        }

        // Stores the cummulative count of each array
        for (int i = 1; i <= max; i++) {
            counts[i] += counts[i - 1];
        }

        // Finds index of each element of A and places them in B
        for (int i = A.length - 1; i >= 0; i--) {
            B[counts[A[i]] - 1] = A[i];
            counts[A[i]]--;
        }

        // Copies the sorted elements into the original array
        for (int i = 0; i < A.length; i++) {
            A[i] = B[i];
        }
    }
    // End of code adatped from Programiz

    // Adapted from Programiz
    // https://www.programiz.com/dsa/radix-sort#code
    // Accessed 03/11/2020
    public static void radixSort(int[] A) {
        // Finds largest element of A
        int max = A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i] > max) {
                max = A[i];
            }
        }

        for (int place = 1; (max / place) > 0; place *= 10) {
            countingSort(A, A.length, place);
        }
    }

    private static void countingSort(int A[], int size, int place) {
        int[] output = new int[size + 1];
        int max = A[0];
        for (int i = 1; i < size; i++) {
          if (A[i] > max)
            max = A[i];
        }
        int[] counts = new int[max + 1];
    
        for (int i = 0; i < max; ++i)
            counts[i] = 0;
    
        // Calculate count of elements
        for (int i = 0; i < size; i++)
            counts[(A[i] / place) % 10]++;
    
        // Calculate cummulative count
        for (int i = 1; i < 10; i++)
            counts[i] += counts[i - 1];
    
        // Place the elements in sorted order
        for (int i = size - 1; i >= 0; i--) {
            output[counts[(A[i] / place) % 10] - 1] = A[i];
            counts[(A[i] / place) % 10]--;
        }
    
        for (int i = 0; i < size; i++)
        A[i] = output[i];
      }
    // End of code adatped from Programiz

}//end Sorts calss
