import java.util.*;

public class DSAShufflingQueue extends DSAQueue {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // INHERITED FROM DSAQueue

    // PRIVATE CLASS FIELDS

    // NONE

    // PROTECTED CLASS FIELDS

    // INHERITED FROM DSAQueue

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAShufflingQueue object
     * PURPOSE: Create new DSAShufflingQueue object with queue = new Object[100] 
     * and numElements = 0
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public DSAShufflingQueue() {
        makeDSAQueue(DEFAULT_MAX_NUM_ELEMENTS);
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inMaxNumElements (int)
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object if inMaxNumElements >= 1
     * CREATION: 21/08/2020
     * LAST MODIFICATION: 23/08/2020
     */

    public DSAShufflingQueue(int inMaxNumElements) {
        makeDSAQueue(inMaxNumElements);
    }

    /*
     * COPY CONSTRUCTOR
     */

    // NONE

    // ACCESSORS

    public Object peek() {
        Object peekedElement;

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot peak at an element from " + 
                                            "an empty shuffling queue");
        }
        // Return element at the front of the queue (without "removing" it)
        else {
            peekedElement = queue[numElements - 1];
        }

        return peekedElement;
    }

    public Object dequeue() {
        Object dequeuedElement;

        // Throw an error if the queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Cannot dequeue an element from " + 
                                            "an empty queue");
        }
        // Return element at the front of the queue (and "removes" it)
        else {
            dequeuedElement = queue[numElements - 1];
            numElements -= 1; // Simulates removing the element
        }

        return dequeuedElement;
    }

    // MUTATORS

    public void enqueue(Object queueElement) {
        // Throw an error if the queue is full
        if (isFull()) {
            throw new IllegalStateException("Cannot enqueue an element into " + 
                                            "a full queue");
        }
        // Insert element at the back of the queue
        else {
            enqueueShuffle(); // Shuffle elements forward
            queue[0] = queueElement;
            numElements += 1;
        }
    }

    // PRIVATE SUBMODULES

    private void enqueueShuffle() {
        for (int i = numElements - 1; i >= 0; i--) {
            queue[i + 1] = queue[i]; // Shuffle element "forward"
        }
    }

    // PROTECTED SUBMODULES

    // NONE
}
