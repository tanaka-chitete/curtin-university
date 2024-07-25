import java.util.*;

public class DSAStack {
    // PRIVATE CLASS CONSTANTS

    private static final int DEFAULT_MAX_NUM_ELEMENTS = 100;

    // PROTECTED CLASS CONSTANTS

    // NONE

    // PRIVATE CLASS FIELDS

    private Object[] stack;
    private int numElements = 0;
    private int maxNumElements;

    // PROTECTED CLASS FIELDS

    // NONE

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAStack object
     * PURPOSE: Construct new DSAStack with maxNumElements = 100
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public DSAStack() {
        makeDSAStack(DEFAULT_MAX_NUM_ELEMENTS);
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inMaxNumElements (int)
     * EXPORT(S): Address of new DSAStack object
     * PURPOSE: Construct new DSAStack with maxNumElements = inMaxNumElements
     *          if inMaxNumElements is >= 1
     * LAST MODIFICATION: 23/08/2020
     */

    public DSAStack(int inMaxNumElements) {
        makeDSAStack(inMaxNumElements);
    }

    /* 
     * COPY CONSTRUCTOR
     */

    // NONE

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        // Throw an error if the stack is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an element from " + 
                                            "an empty stack");
        }
        // Return element at the top of the stack (without "removing" it)
        else {
            peekedElement = stack[numElements - 1];
        }

        return peekedElement;
    }

    public Object pop() {
        Object poppedElement;

        // Throw an error if the stack is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an element from an " +
                                            "empty stack");
        }
        // Return element at the top of the stack (and "remove" it)
        else {
            poppedElement = stack[numElements - 1];
            numElements -= 1; // Simulates removing the element
        }

        return poppedElement;
    }

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

    public void push(Object pushElement) {
        // Throw an error if the stack is full
        if (isFull()) {
            throw new IllegalStateException("Cannot push an element onto a " + 
                                            "full stack");
        }
        // Insert element at the top of the stack
        else {
            stack[numElements] = pushElement;
            numElements += 1;
        }
    }

    // OPERATORS

    public String toString() {
        String stackString = "";
        for (int i = numElements - 1; i >= 0; i--) {
            if (i == 0) { // Don't append a NL to the string for last element
                stackString += stack[i];
            }
            else {
                stackString += stack[i] + "\n";
            }
        }

        return stackString;
    }

    // PRIVATE SUBMODULES

    private void makeDSAStack(int inMaxNumElements) {
        // Throw an error if maxNumElements is invalid
        if (!isValidInMaxNumElements(inMaxNumElements)) {
            throw new IllegalStateException("Cannot make a stack with " +
                                            inMaxNumElements + " maximum " + 
                                            "element capacity");
        }
        // Create a new DSAStack object with maxNumElements element capacity
        {
            stack = new Object[inMaxNumElements];
            maxNumElements = inMaxNumElements;
        }
    }

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

    // PRIVATE SUBMODULES

    // NONE
}
