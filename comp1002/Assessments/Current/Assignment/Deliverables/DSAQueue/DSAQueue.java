// Adapted from Tanaka Chitete
// Adapted 23/10/2020

import java.util.*;

/*
 * NAME: DSAQueue
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a general-purpose queue
 * CREATION: 07/09/2020
 * LAST MODIFICATION: 01/11/2020
 */

public class DSAQueue implements Iterable {
    // PRIVATE CLASS FIELDS

    private DSALinkedList queue; 
    private int numElements;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public DSAQueue() {
        queue = new DSALinkedList();
        numElements = 0;
    }

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an element of an empty queue");
        }
        else {
            peekedElement = queue.peekFirst();
        }

        return peekedElement;
    }

    public Object dequeue() {
        Object dequeuedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an element from an empty queue");
        }
        else {
            dequeuedElement = queue.removeFirst();
            numElements -= 1;
        }

        return dequeuedElement;
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

    public void enqueue(Object queueElement) {
        queue.insertLast(queueElement);;
        numElements += 1;
    }

    // OPERATORS

    public String toString() {
        String queueStr = "";
        Iterator queueIter = queue.iterator();
        while (queueIter.hasNext()) {
            Object currObj = queueIter.next();
            queueStr = currObj + " " + queueStr;
        }

        return queueStr;
    }

    // ITERATOR

    public Iterator iterator() {
        return queue.iterator();
    }
}
