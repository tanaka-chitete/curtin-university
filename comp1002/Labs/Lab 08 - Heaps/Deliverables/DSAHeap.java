/*
 * NAME: DSAHeap
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a general purpose heap
 * CREATION: 16/10/2020
 * LAST MODIFICATION: 16/10/2020
 */

public class DSAHeap {
    // PRIVATE CLASS CONSTANTS

    private static final int ROOT_INDEX = 0;

    // PRIVATE CLASS FIELDS

    DSAHeapEntry[] heap;
    int count;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): inCapacity (int)
     * EXPORT(S): Address of new DSAHeap
     * PURPOSE: Create new DSAHeap in default state
     * CREATION: 20/10/2020
     * LAST MODIFICATION: 20/10/2020
     */

    public DSAHeap() {
        heap = null;
        count = 0;
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inCapacity (int)
     * EXPORT(S): Address of new DSAHeap
     * PURPOSE: Create new DSAHeap in alternate state
     * CREATION: 16/10/2020
     * LAST MODIFICATION: 16/10/2020
     */

    public DSAHeap(int inCapacity) {
        if (!(inCapacity > 0)) {
            throw new IllegalArgumentException("Cannot initialise a heap with a capacity less " + 
                "than or equal to 0");
        }
        else {
            heap = new DSAHeapEntry[inCapacity];
            count = 0;
        }
    }

    // SETTERS (MUTATORS)

    public void add(int inPriority, Object inValue) {
        if (isFull()) {
            throw new IllegalStateException("Cannot call add on a full heap");
        }
        else {
            DSAHeapEntry newEntry = new DSAHeapEntry(inPriority, inValue);
            int iLastEntry = count;
            heap[iLastEntry] = newEntry;
            count++;
    
            _trickleUp(iLastEntry);    
        }
    }

    // GETTERS (ACCESSORS)

    public void remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call remove on an empty heap");
        }
        else {
            int iLastEntry = count - 1;
            DSAHeapEntry temp = heap[iLastEntry];
            heap[ROOT_INDEX] = temp;
            count--;

            _trickleDown(ROOT_INDEX, heap.length);
        }
    }

    public boolean isEmpty() {
        boolean isEmpty;
        if (count == 0) {
            isEmpty = true;
        }
        else {
            isEmpty = false;
        }

        return isEmpty;
    }

    public boolean isFull() {
        boolean isFull;
        if (count == heap.length) {
            isFull = true;
        }
        else {
            isFull = false;
        }

        return isFull;
    }

    // OPERATORS

    public void heapSort(DSAHeapEntry[] values) {
        // Turns values into a heap
        _heapify(values);

        // Removes elements from the heap one at a time and puts them back into the array
        for (int i = values.length - 1; i >= 1; i--) {
            _swap(0, i);
            // Prevents value at current last sorting position from being swapped again
            count--;
            _trickleDown(0, i);
        }
        // Restores count
        count = values.length;
    }

    public String toString() {
        String heapString = "[";
        int iLast = count - 1;
        for (int i = 0; i < count; i++) {
            int currentPriority = heap[i].getPriority();

            if (i == iLast) {
                heapString += currentPriority;
            }
            else {
                heapString += currentPriority + ", ";
            }
        }
        heapString += "]";

        return heapString;
    }

    // PRIVATE SUBMODULES

    private void _trickleUp(int iCurrent) {
        int iParent = (iCurrent - 1) / 2;
        if (iCurrent > 0) {
            if (heap[iCurrent].getPriority() > heap[iParent].getPriority()) {
                _swap(iParent, iCurrent);
                _trickleUp(iParent);
            }
        }
    }

    private void _trickleDown(int iCurrent, int currentLength) {
        int iLeftChild = (2 * iCurrent) + 1;
        int iRightChild = iLeftChild + 1;
        int iLargerChild;

        // Current left "child" is a left child
        if (iLeftChild < count) {
            // Marks left child as the larger child (possibly temporarily)
            iLargerChild = iLeftChild;
            // Current right "child" is a right child
            if (iRightChild < count) {
                // Right child is greater than left child
                if (heap[iRightChild].getPriority() > heap[iLeftChild].getPriority()) {
                    // Marks right child as the larger child
                    iLargerChild = iRightChild;
                }
            }
            
            if (heap[iLargerChild].getPriority() > heap[iCurrent].getPriority()) {
                _swap(iCurrent, iLargerChild);
                _trickleDown(iLargerChild, currentLength);
            }
        }
    }

    private void _heapify(DSAHeapEntry[] inValues) {
        heap = inValues;
        count = inValues.length;

        // Iterates backwards starting from last non-leaf value
        for (int i = (count / 2) - 1; i >= 0; i--) {
            // Puts value in correct place in heap
            _trickleDown(i, count);
        }
    }

    private void _swap(int x, int y) {
        DSAHeapEntry temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }
}
