/*
 * NAME: DSALinkedList
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement a general-purpose linked list
 * CREATION: 31/08/2020
 * LAST MODIFICATION: 31/08/2020
 */

import java.io.Serializable;
import java.util.*;

public class DSALinkedList implements Iterable, Serializable {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // NONE

    // PRIVATE CLASS FIELDS

    private DSALinkedListNode firstNode;
    private DSALinkedListNode lastNode;

    // PROTECTED CLASS FIELDS

    // NONE

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
    }

    /*
     * ALTERNATE CONSTRUCTOR
     */

    // NONE

    /*
     * COPY CONSTRUCTOR
     */

    // NONE

    // GETTERS (ACCESSORS)

    public Object peekFirst() {
        Object peekedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the first node " + 
                                            "element of an empty linked list");
        }
        else {
            peekedFirstNodeElement = firstNode.getElement();
        }

        return peekedFirstNodeElement;
    }

    public Object peekLast() {
        Object peekedLastNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the last node " + 
                                            "element of an empty linked list");
        }
        else {
            peekedLastNodeElement = lastNode.getElement();
        }

        return peekedLastNodeElement;
    }

    public Object removeFirst() {
        Object removedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove the first node " +
                                            "element of an empty linked list");
        }
        else {
            removedFirstNodeElement = firstNode.getElement();
            firstNode = firstNode.getNext();
        }

        return removedFirstNodeElement;
    }

    public Object removeLast() {
        Object removedLastNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove the last node " +
                                            "element of an empty linked list");
        }
        else if (firstNode.getNext() == null) {
            removedLastNodeElement = firstNode.getElement();
            firstNode = null;
        }
        else {
            removedLastNodeElement = lastNode.getElement();
            lastNode = lastNode.getPrevious();
            lastNode.setNext(null);
        }

        return removedLastNodeElement;
    }

    // SETTERS (MUTATORS)

    public void insertFirst(Object newFirstNodeElement) {
        DSALinkedListNode newFirstNode = 
            new DSALinkedListNode(newFirstNodeElement);

        if (isEmpty()) {
            firstNode = newFirstNode;
            lastNode = newFirstNode;
        }
        else {
            newFirstNode.setNext(firstNode);
            firstNode = newFirstNode;
        }
    }

    public void insertLast(Object newLastNodeElement) {
        DSALinkedListNode newLastNode = 
            new DSALinkedListNode(newLastNodeElement);

        if (isEmpty()) {
            lastNode = newLastNode;
            firstNode = newLastNode;
        }
        else {
            lastNode.setNext(newLastNode);
            lastNode = newLastNode;
        }
    }

    // OPERATORS

    public boolean isEmpty() {
        boolean empty;

        if (firstNode == null) {
            empty = true;
        }
        else {
            empty = false;
        }

        return empty;
    }

    // PRIVATE SUBMODULES

    // NONE

    // PROTECTED SUBMODULES 

    // NONE

    // ITERATOR

    public Iterator iterator() { // Iterator of type DSALinkedListIterator
        // Hook iterator to this DSALinkedList object
        return new DSALinkedListIterator(this);
    }

    private class DSALinkedListIterator implements Iterator {
        private DSALinkedListNode iterNext;

        public DSALinkedListIterator(DSALinkedList inDSALinkedList) {
            iterNext = inDSALinkedList.firstNode;
        }

        public boolean hasNext() {
            boolean doesHaveNext;

            if (iterNext == null) {
                doesHaveNext = false;
            }
            else {
                doesHaveNext = true;
            }

            return doesHaveNext;
        }

        public Object next() {
            Object element;

            if (iterNext == null) {
                element = null;
            }
            else {
                element = iterNext.getElement();
                iterNext = iterNext.getNext();
            }

            return element;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
