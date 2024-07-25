/*
 * NAME: DSAQueue
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a generic queue
 * CREATION: 03/11/2020
 * LAST MODIFICATION: 03/11/2020
 */

import java.util.*;

public class DSAQueue<T> extends AbstractQueue<T> {
    // PRIVATE CLASS FIELDS

    private DSALinkedList<T> queue;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAQueue object
     * PURPOSE: Create new DSAQueue object
     * CREATION: 03/11/2020
     * LAST MODIFICATION: 03/11/2020
     */

    public DSAQueue() {
        queue = new DSALinkedList<T>();
    }

    // SETTERS (MUTATORS)

    @Override
    public boolean offer(T element) {
        boolean success;
        if (element == null) {
            success = false;
        }
        else {
            queue.insertLast(element);
            success = true;
        }

        return success;
    }

    // GETTERS (ACCESSORS)

    @Override
    public T peek() {
        T peekedElement = queue.peekFirst();

        return peekedElement;
    }

    @Override
    public T poll() {
        T polledElement = queue.removeFirst();

        return polledElement;
    }

    // OPERATORS

    @Override
    public int size() {
        return queue.size();
    }

    // ITERATOR

    @Override
    public Iterator<T> iterator() {
        Iterator<T> queueIterator = queue.iterator();

        return queueIterator;
    }
}
