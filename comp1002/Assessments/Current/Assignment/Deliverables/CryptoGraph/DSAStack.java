// Adapted from Tanaka Chitete
// Adapted 23/10/2020

/*
 * NAME: DSAStack
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a general-purpose stack
 * CREATION: 07/09/2020
 * LAST MODIFICATION: 07/10/2020
 */

import java.util.*;

public class DSAStack implements Iterable {
    // PRIVATE CLASS FIELDS

    private DSALinkedList stack;
    private int numElements;

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
        numElements = 0;
    }

    // MUTATORS

    public void push(Object pushElement) {
        stack.insertFirst(pushElement);
        numElements += 1;
    }

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an element of an empty stack");
        }
        else {
            peekedElement = stack.peekFirst();
        }

        return peekedElement;
    }

    public Object pop() {
        Object poppedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an element of an empty stack");
        }
        else {
            poppedElement = stack.removeFirst();
            numElements -= 1;
        }

        return poppedElement;
    }

    public int getNumElements() {
        return numElements;
    }

    // OPERATORS

    public boolean isEmpty() {
        boolean isEmpty;

        if (numElements == 0) {
            isEmpty = true;
        }
        else {
            isEmpty = false;
        }

        return isEmpty;
    }

    public String toString() {
        String stackString = "";
        Iterator stackIter = stack.iterator();
        while (stackIter.hasNext()) {
            Object currObject = stackIter.next();
            stackString = currObject + " " + stackString;
        }

        return stackString;
    }

    // ITERATOR

    public Iterator iterator() {
        Iterator stackIter = stack.iterator();

        return stackIter;
    }
}
