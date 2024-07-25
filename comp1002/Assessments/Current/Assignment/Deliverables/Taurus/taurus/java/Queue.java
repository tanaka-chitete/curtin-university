/*
 * NAME: Queue
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Implement a generic queue
 * CREATION: 03/11/2020
 * LAST MODIFICATION: 09/11/2020
 */

import java.util.*;

public class Queue<T> extends AbstractQueue<T> {
    // PRIVATE CLASS FIELDS

    private LinkedList<T> queue;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new Queue
     * PURPOSE: Create new Queue
     * CREATION: 03/11/2020
     * LAST MODIFICATION: 03/11/2020
     */

    public Queue() {
        queue = new LinkedList<T>();
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
        T peekedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the first element of an empty queue");
        }
        else {
            peekedElement = queue.peekFirst();
        }

        return peekedElement;
    }

    @Override
    public T poll() {
        T polledElement;
        
        if (isEmpty()) {
            throw new IllegalStateException("Cannot poll at the first element of an empty queue");
        }
        else {
            polledElement = queue.removeFirst();
        }

        return polledElement;
    }

    // OPERATORS

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String toString() {
        return queue.toString();
    }

    // ITERATOR

    @Override
    public Iterator<T> iterator() {
        Iterator<T> queueIterator = queue.iterator();

        return queueIterator;
    }
}
