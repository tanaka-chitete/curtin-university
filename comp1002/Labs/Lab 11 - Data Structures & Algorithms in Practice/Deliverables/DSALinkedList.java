// Adapted from Tanaka Chitete
// Accessed 03/11/2020

import java.io.Serializable;
import java.util.*;

public class DSALinkedList<T> extends AbstractSequentialList<T> {
    // NESTED CLASSES

    /*
     * NAME: DSALinkedListNode
     * CREATOR: Tanaka Chitete
     * STUDENT_ID: 20169321
     * UNIT: COMP1002
     * PURPOSE: Implement nodes for DSALinkedList
     * CREATION: 31/08/2020
     * LAST MODIFICATION: 03/11/2020
     */

    public class DSALinkedListNode<U> implements Serializable {
        // PRIVATE CLASS FIELDS
    
        private U element;
        private DSALinkedListNode<U> previousNode;
        private DSALinkedListNode<U> nextNode;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inElement (Object)
         * EXPORT(S): Address of new DSALinkedListNode
         * PURPOSE: Create new DSALinkedListNode in alternate state
         * CREATION: 31/08/2020
         * LAST MODIFICATION: 31/08/2020
         */
    
        public DSALinkedListNode(U inElement) {
            element = inElement;
            previousNode = null;
            nextNode = null;
        }
    }

    // PRIVATE CLASS FIELDS

    private DSALinkedListNode<T> firstNode;
    private DSALinkedListNode<T> lastNode;
    private int count;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSALinkedList
     * PURPOSE: Create new DSALinkedList in default state
     * CREATION: 31/08/2020
     * LAST MODIFICATION: 31/08/2020
     */

    public DSALinkedList() {
        firstNode = null;
        lastNode = null;
        count = 0;
    }

    // SETTERS (MUTATORS)

    public void insertFirst(T newFirstNodeElement) {
        DSALinkedListNode<T> newFirstNode = new DSALinkedListNode<T>(newFirstNodeElement);

        if (isEmpty()) {
            firstNode = newFirstNode;
            lastNode = newFirstNode;
        }
        else {
            newFirstNode.nextNode = firstNode;
            firstNode = newFirstNode;
            count++;
        }
    }

    public void insertLast(T newLastNodeElement) {
        DSALinkedListNode<T> newLastNode = new DSALinkedListNode<T>(newLastNodeElement);

        if (isEmpty()) {
            lastNode = newLastNode;
            firstNode = newLastNode;
        }
        else {
            lastNode.nextNode = newLastNode;
            lastNode = newLastNode;
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
        else if (firstNode.nextNode == null) {
            removedLastNodeElement = firstNode.element;
            firstNode = null;
        }
        else {
            removedLastNodeElement = lastNode.element;
            lastNode = lastNode.previousNode;
            lastNode.nextNode = null;
            count--;
        }

        return removedLastNodeElement;
    }

    // OPERATORS

    public boolean isEmpty() {
        boolean isEmpty;

        if (firstNode == null) {
            isEmpty = true;
        }
        else {
            isEmpty = false;
        }

        return isEmpty;
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
        // Hooks iterator to this DSALinkedList object
        return new DSALinkedListIterator(this);
    }

    private class DSALinkedListIterator implements Iterator<T> {
        private DSALinkedListNode<T> iterNext;

        public DSALinkedListIterator(DSALinkedList<T> inDSALinkedList) {
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

