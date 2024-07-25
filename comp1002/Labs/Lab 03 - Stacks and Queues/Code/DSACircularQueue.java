import java.util.*;

public class DSACircularQueue extends DSAQueue {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // INHERITED FROM DSAQueue

    // PRIVATE CLASS FIELDS

    private int front = 0;
    private int back = 0;

    // PROTECTED CLASS CONSTANTS

    // INHERITED FROM DSAQueue

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSACircularQueue object
     * PURPOSE: Create new DSACircularQueue object in default state
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public DSACircularQueue() {
        makeDSAQueue(100);
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inMaxNumElements (int)
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object if inMaxNumElements >= 1
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public DSACircularQueue(int inMaxNumElements) {
        makeDSAQueue(inMaxNumElements);
    }

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        // Return element at the front of the queue (without "removing" it)
        else {
            peekedElement = queue[front];
        }

        return peekedElement;
    }

    public Object dequeue() {
        Object dequeuedElement;

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        // Return element at the front of the queue (and "removes" it)
        else {
            dequeuedElement = queue[front];
            numElements -= 1; // Simulates removing the element
            front = update(front);
        }

        return dequeuedElement;
    }

    // MUTATORS

    public void enqueue(Object queueElement) {
        // Throw an error if the queue is full
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        // Insert element at the back of the queue
        else {
            queue[numElements] = queueElement;
            numElements += 1;
            back = update(back);
        }
    }

    // PRIVATE SUBMODULES

    private int update(int index) {
        int potentialNextIndex = index + 1;
        int lastIndex = maxNumElements - 1;
        
        int nextIndex;

        // If potentialNextIndex needs to "wrap around" to start of the queue 
        if (potentialNextIndex > lastIndex) {
            nextIndex = 0; // "Wrap" back to first index
        }
        else {
            nextIndex = index + 1;
        }

        return nextIndex;
    }
}
