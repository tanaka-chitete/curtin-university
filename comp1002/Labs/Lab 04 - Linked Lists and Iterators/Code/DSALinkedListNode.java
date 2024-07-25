/*
 * NAME: DSALinkedListNode
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement nodes for DSALinkedList
 * CREATION: 31/08/2020
 * LAST MODIFICATION: 31/08/2020
 */

import java.io.Serializable;

public class DSALinkedListNode implements Serializable {
    // PRIVATE CLASS CONSTANTS

    // NONE

    // PROTECTED CLASS CONSTANTS

    // NONE

    // PRIVATE CLASS FIELDS

    private Object element;
    private DSALinkedListNode previousNode = null;
    private DSALinkedListNode nextNode = null;

    // PROTECTED CLASS FIELDS

    // NONE

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     */

    // NONE

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
    }

    /*
     * COPY CONSTRUCTOR
     */

    // NONE

    // GETTERS (ACCESSORS)

    public Object getElement() {
        return element;
    }

    public DSALinkedListNode getPrevious() {
        return previousNode;
    }

    public DSALinkedListNode getNext() {
        return nextNode;
    }

    // SETTERS (MUTATORS)

    public void setElement(Object newElement) {
        element = newElement;
    }

    public void setPrevious(DSALinkedListNode newPreviousNode) {
        previousNode = newPreviousNode;
    }

    public void setNext(DSALinkedListNode newNextNode) {
        nextNode = newNextNode;
    }

    // OPERATORS

    // NONE

    // PRIVATE SUBMODULES

    // NONE

    // PROTECTED SUBMODULES 

    // NONE
}