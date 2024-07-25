import java.util.*;

public class DSAStack implements Iterable {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // NONE

    // PRIVATE CLASS FIELDS

    private DSALinkedList stack;
    private int numElements = 0;

    // PROTECTED CLASS FIELDS

    // NONE

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAStack object
     * PURPOSE: Construct new DSAStack with maxNumElements = 100
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public DSAStack() {
        stack = new DSALinkedList();
    }

    /*
     * ALTERNATE CONSTRUCTOR
     */

    // NONE

    /* 
     * COPY CONSTRUCTOR
     */

    // NONE

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        // Throw an error if the stack is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an element of an " + 
                                            "empty stack");
        }
        // Return element at the top of the stack (without "removing" it)
        else {
            peekedElement = stack.peekFirst();
        }

        return peekedElement;
    }

    public Object pop() {
        Object poppedElement;

        // Throw an error if the stack is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an element of an " + 
                                            "empty stack");
        }
        // Return element at the top of the stack (and "remove" it)
        else {
            poppedElement = stack.removeFirst();
            numElements -= 1; // Simulates removing the element
        }

        return poppedElement;
    }

    public int getNumElements() {
        return numElements;
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

    // MUTATORS

    public void push(Object pushElement) {
        stack.insertFirst(pushElement);
        numElements += 1;
    }

    // OPERATORS

    public String toString() {
        String stackString = "";
        for (int i = 0; i < numElements; i++) {
            if (i == (numElements - 1)) { // Don't append a NL to the string for last element
                stackString += stack.removeFirst();
            }
            else {
                stackString += stack.removeFirst() + "\n";
            }
        }

        return stackString;
    }

    // PRIVATE SUBMODULES

    // NONE

    // PROTECTED SUBMODULES

    // NONE

    // ITERATOR

    public Iterator iterator() {
        return stack.iterator();
    }
}
