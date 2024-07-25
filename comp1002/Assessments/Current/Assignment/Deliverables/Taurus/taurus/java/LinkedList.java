/*
 * NAME: LinkedList
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Implement a generic Linked List
 * CREATION: 31/08/2020
 * LAST MODIFICATION: 09/11/2020
 */

import java.io.Serializable;
import java.util.*;

public class LinkedList<T> extends AbstractSequentialList<T> implements Serializable {
    // NESTED CLASSES

    /*
     * NAME: Node
     * CREATOR: Tanaka Chitete
     * PURPOSE: Implement nodes for LinkedList
     * CREATION: 31/08/2020
     * LAST MODIFICATION: 03/11/2020
     */

    private class Node<A> implements Serializable {
        // PRIVATE CLASS FIELDS
    
        private A element;
        private Node<A> previousNode;
        private Node<A> nextNode;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inElement (Object)
         * EXPORT(S): Address of new Node
         * PURPOSE: Create new Node in alternate state
         * CREATION: 31/08/2020
         * LAST MODIFICATION: 03/11/2020
         */
    
        private Node(A inElement) {
            element = inElement;
            previousNode = null;
            nextNode = null;
        }
    }

    // PRIVATE CLASS FIELDS

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int count;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new LinkedList
     * PURPOSE: Create new LinkedList in default state
     * CREATION: 31/08/2020
     * LAST MODIFICATION: 3/11/2020
     */

    public LinkedList() {
        firstNode = null;
        lastNode = null;
        count = 0;
    }

    // SETTERS (MUTATORS)

    public void insertFirst(T newFirstNodeElement) {
        Node<T> newFirstNode = new Node<T>(newFirstNodeElement);

        if (newFirstNodeElement == null) {
            throw new IllegalArgumentException("Cannot call insertFirst with a null element");
        }
        else {
            if (isEmpty()) {
                firstNode = newFirstNode;
                lastNode = newFirstNode;
            }
            else {
                newFirstNode.nextNode = firstNode;
                firstNode = newFirstNode;
            }
            count++;
        }
    }

    public void insertLast(T newLastNodeElement) {
        Node<T> newLastNode = new Node<T>(newLastNodeElement);

        if (newLastNodeElement == null) {
            throw new IllegalArgumentException("Cannot call insertLast with a null element");
        }
        else {
            if (isEmpty()) {
                lastNode = newLastNode;
                firstNode = newLastNode;
            }
            else {
                lastNode.nextNode = newLastNode;
                lastNode = newLastNode;
            }
            count++;
        }
    }

    // GETTERS (ACCESSORS)

    public T peekFirst() {
        T peekedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the first node element of an empty " + 
                "linked list");
        }
        else {
            peekedFirstNodeElement = firstNode.element;
        }

        return peekedFirstNodeElement;
    }

    public T peekLast() {
        T peekedLastNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the last node element of an empty " + 
                "linked list");
        }
        else {
            peekedLastNodeElement = lastNode.element;
        }

        return peekedLastNodeElement;
    }

    public T removeFirst() {
        T removedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove the first node element of an empty " + 
                "linked list");
        }
        else {
            removedFirstNodeElement = firstNode.element;
            firstNode = firstNode.nextNode;
            count--;
        }

        return removedFirstNodeElement;
    }

    public T removeLast() {
        T removedLastNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove the last node element of an empty " + 
                "linked list");
        }
        else {
            if (firstNode.nextNode == null) {
                removedLastNodeElement = firstNode.element;
                firstNode = null;
            }
            else {
                removedLastNodeElement = lastNode.element;
                lastNode = lastNode.previousNode;
                lastNode.nextNode = null;
            }
            count--;
        }

        return removedLastNodeElement;
    }

    // OPERATORS

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        Iterator<T> linkedListIterator = iterator();
        int remainingElements = size();
        while (remainingElements > 0) {
            T currentElement = linkedListIterator.next();
            remainingElements--;
            if (remainingElements == 0) {
                stringBuilder.append(currentElement.toString());
            }
            else {
                stringBuilder.append(currentElement.toString() + ", ");
            }
        }
        stringBuilder.append("]");

        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return count;
    }   
    
    // ITERATOR

    @Override 
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }
    
    public Iterator<T> iterator() {
        // Hooks iterator to this LinkedList object
        return new LinkedListIterator(this);
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> iterNext;

        public LinkedListIterator(LinkedList<T> inDSALinkedList) {
            iterNext = inDSALinkedList.firstNode;
        }

        @Override
        public boolean hasNext() {
            boolean hasNext;

            if (iterNext == null) {
                hasNext = false;
            }
            else {
                hasNext = true;
            }

            return hasNext;
        }

        @Override
        public T next() {
            T element;

            if (iterNext == null) {
                element = null;
            }
            else {
                element = iterNext.element;
                iterNext = iterNext.nextNode;
            }

            return element;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
