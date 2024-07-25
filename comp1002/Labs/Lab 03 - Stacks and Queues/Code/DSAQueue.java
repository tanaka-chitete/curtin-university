import java.util.*;

public class DSAQueue {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS 
    // Inherited by DSAShufflingQueue and DSACircularQueue

    protected static final int DEFAULT_MAX_NUM_ELEMENTS = 100;

    // PRIVATE CLASS FIELDS

    // NONE

    // PROTECTED CLASS FIELDS
    // Inherited by DSAShufflingQueue and DSACircularQueue

    protected Object[] queue;
    protected int numElements = 0;
    protected int maxNumElements;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object with queue = new Object[100] and 
     *          numElements = 0
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 21/08/2020
     */

    public DSAQueue() {
        makeDSAQueue(DEFAULT_MAX_NUM_ELEMENTS);
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inMaxNumElements (int)
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object if inMaxNumElements >= 1
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 21/08/2020
     */

    public DSAQueue(int inMaxNumElements) {
        makeDSAQueue(inMaxNumElements);
    }

    /*
     * COPY CONSTRUCTOR
     */

    // NONE

    // ACCESSORS

    public int getNumElements() {
        return numElements;
    }

    public int getMaxNumElements() {
        return maxNumElements;
    }

    public boolean isEmpty() {
        boolean empty;

        if (numElements == 0) {
            empty = true;
        }
        else {
            empty = false;
        }

        return empty;
    }

    public boolean isFull() {
        boolean full;

        if (numElements == maxNumElements) {
            full = true;
        }
        else {
            full = false;
        }

        return full;
    }

    // MUTATORS

    // NONE

    // OPERATORS

    public String toString() {
        String queueString = "";
        for (int i = 0; i < numElements; i++) {
            if (i == numElements - 1) { // Don't append a NL to the string for last element
                queueString += queue[i];
            }
            else {
                queueString += queue[i] + "\n";
            }
        }

        return queueString;
    }

    // PRIVATE SUBMODULES

    private boolean isValidInMaxNumElements(int inMaxNumElements) {
        boolean validInMaxNumElements;

        if (inMaxNumElements < 1) {
            validInMaxNumElements = false;
        }
        else {
            validInMaxNumElements = true;
        }

        return validInMaxNumElements;
    }

    // PROTECTED SUBMODULES

    protected void makeDSAQueue(int inMaxNumElements) {
        // Throw an error if maxNumElements is invalid
        if (!isValidInMaxNumElements(inMaxNumElements)) {
            throw new IllegalArgumentException("Maximum number of elements " +
                                               "is invalid");
        }
        // Create a new DSAQueue object with maxNumElements element capacity
        {
            queue = new Object[inMaxNumElements];
            maxNumElements = inMaxNumElements;
        }
    }
}
