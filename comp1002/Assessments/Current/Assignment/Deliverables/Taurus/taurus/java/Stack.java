/*
 * NAME: Stack
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Implement a generic stack
 * CREATION: 06/11/2020
 * LAST MODIFICATION: 09/11/2020
 */

import java.util.*;

public class Stack<E> extends Vector<E> {
    // PRIVATE CLASS FIELDS

    private LinkedList<E> stack;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new Stack object
     * PURPOSE: Create new Stack object
     * CREATION: 03/11/2020
     * LAST MODIFICATION: 09/11/2020
     */

    public Stack() {
        stack = new LinkedList<E>();
    }

    // SETTERS (MUTATORS)

    public boolean push(E element) {
        boolean success;
        if (element == null) {
            success = false;
        }
        else {
            stack.insertFirst(element);
            success = true;
        }

        return success;
    }

    // GETTERS (ACCESSORS)

    public E peek() {
        E peekedElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the first element of an empty stack");
        }
        else {
            peekedElement = stack.peekFirst();
        }

        return peekedElement;
    }

    public E pop() {
        E polledElement;
        
        if (isEmpty()) {
            throw new IllegalStateException("Cannot poll at the first element of an empty stack");
        }
        else {
            polledElement = stack.removeFirst();
        }

        return polledElement;
    }

    // OPERATORS

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<E> stackIterator = iterator();
        while (stackIterator.hasNext()) {
            E currentElement = stackIterator.next();
            stringBuilder.insert(0, currentElement.toString() + " -> ");                
        }

        // Removes trailing " -> " from StringBuilder
        stringBuilder.setLength(stringBuilder.length() - 4);

        return stringBuilder.toString();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return stack.size();
    }

    // ITERATOR

    public Iterator<E> iterator() {
        Iterator<E> stackIterator = stack.iterator();

        return stackIterator;
    }
}
