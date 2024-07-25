// Obtained from Tanaka Chitete
// (accessed 30/09/2020)

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
    // NESTED CLASSES

    /*
     * NAME: DSALinkedListNode
     * CREATOR: Tanaka Chitete
     * STUDENT_ID: 20169321
     * UNIT: COMP1002
     * PURPOSE: Implement nodes for DSALinkedList
     * CREATION: 31/08/2020
     * LAST MODIFICATION: 31/10/2020
     */

    public class DSALinkedListNode implements Serializable {
        // PRIVATE CLASS FIELDS
    
        private Object element;
        private DSALinkedListNode previousNode;
        private DSALinkedListNode nextNode;
    
        // CONSTRUCTORS
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inElement (Object)
         * EXPORT(S): Address of new DSALinkedListNode
         * PURPOSE: Create new DSALinkedListNode in alternate state
         * CREATION: 31/08/2020
         * LAST MODIFICATION: 31/08/2020
         */
    
        public DSALinkedListNode(Object inElement) {
            element = inElement;
            previousNode = null;
            nextNode = null;
        }
    }

    // PRIVATE CLASS FIELDS

    private DSALinkedListNode firstNode;
    private DSALinkedListNode lastNode;

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

    // SETTERS (MUTATORS)

    public void insertFirst(Object newFirstNodeElement) {
        DSALinkedListNode newFirstNode = new DSALinkedListNode(newFirstNodeElement);

        if (isEmpty()) {
            firstNode = newFirstNode;
            lastNode = newFirstNode;
        }
        else {
            newFirstNode.nextNode = firstNode;
            firstNode = newFirstNode;
        }
    }

    public void insertLast(Object newLastNodeElement) {
        DSALinkedListNode newLastNode = new DSALinkedListNode(newLastNodeElement);

        if (isEmpty()) {
            lastNode = newLastNode;
            firstNode = newLastNode;
        }
        else {
            lastNode.nextNode = newLastNode;
            lastNode = newLastNode;
        }
    }

    // GETTERS (ACCESSORS)

    public Object peekFirst() {
        Object peekedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the first node element of an empty " + 
                "linked list");
        }
        else {
            peekedFirstNodeElement = firstNode.element;
        }

        return peekedFirstNodeElement;
    }

    public Object peekLast() {
        Object peekedLastNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot peek at the last node element of an empty " + 
                "linked list");
        }
        else {
            peekedLastNodeElement = lastNode.element;
        }

        return peekedLastNodeElement;
    }

    public Object removeFirst() {
        Object removedFirstNodeElement;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot remove the first node element of an empty " + 
                "linked list");
        }
        else {
            removedFirstNodeElement = firstNode.element;
            firstNode = firstNode.nextNode;
        }

        return removedFirstNodeElement;
    }

    public Object removeLast() {
        Object removedLastNodeElement;

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

    // ITERATOR

    public Iterator iterator() {
        // Hooks iterator to this DSALinkedList object
        return new DSALinkedListIterator(this);
    }

    private class DSALinkedListIterator implements Iterator {
        private DSALinkedListNode iterNext;

        public DSALinkedListIterator(DSALinkedList inDSALinkedList) {
            iterNext = inDSALinkedList.firstNode;
        }

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

        public Object next() {
            Object element;

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
