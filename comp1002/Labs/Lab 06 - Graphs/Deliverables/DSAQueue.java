import java.util.*;

public class DSAQueue implements Iterable {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS 
    // (Would be inherited by DSAShufflingQueue and DSACircularqueue)

    // NONE

    // PRIVATE CLASS FIELDS

    private DSALinkedList queue; 
    private int numElements = 0;

    // PROTECTED CLASS FIELDS

    // NONE

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

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at an element of an " + 
                                            "empty queue");
        }
        // Return element at the front of the queue (without "removing" it)
        else {
            peekedElement = queue.peekFirst();
        }

        return peekedElement;
    }

    public Object dequeue() {
        Object dequeuedElement;

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot pop an element from an " + 
                                            "empty queue");
        }
        // Return element at the front of the queue (and "removes" it)
        else {
            dequeuedElement = queue.removeFirst();
            numElements -= 1; // Simulates removing the element
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
        String queueString = "";
        for (int i = 0; i < numElements; i++) {
            if (i == (numElements - 1)) { // Don't append a NL to the string for last element
                queueString += queue.removeFirst();
            }
            else {
                queueString += queue.removeFirst() + "\n";
            }
        }

        return queueString;
    }

    // PRIVATE SUBMODULES

    // NONE

    // PROTECTED SUBMODULES

    // NONE

    // ITERATOR

    public Iterator iterator() {
        return queue.iterator();
    }
}
