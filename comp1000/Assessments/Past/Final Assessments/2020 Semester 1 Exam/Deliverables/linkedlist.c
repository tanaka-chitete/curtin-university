/* Parts of this file comprise externally-obtained code */

#include <stdio.h>
#include <stdlib.h>

#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include "linkedlist.h"
#endif

#ifndef LINKEDLISTNODE_H
#define LINKEDLISTNODE_H
#include "linkedlistnode.h"
#endif

/* CONSTRUCTORS */

/* Obtained from Lecture 6: Structs */
/* Accessed 05/10/2020 */
LinkedList *makeLinkedList() {
    LinkedList *list;

    /* Initialises head */
    list = (LinkedList*) malloc(sizeof(LinkedList));
    (*list).head = NULL;
    (*list).count = 0;

    return list;
}
/* End of code obtained from Lecture 6: Structs */

/* SETTERS (MUTATORS) */

/* Obtained from Lecture 6: Structs */
/* Accessed 05/10/2020 */
void insertFirst(LinkedList *list, void *inValue) {
    LinkedListNode *newNode;

    /* Initialises a new node */
    newNode = makeLinkedListNode();

    /* Points value field to inValue */
    (*newNode).value = inValue;

    /* Points next field to existing first node */
    (*newNode).next = (*list).head;

    /* Points head to new node */
    (*list).head = newNode;

    (*list).count++;
}
/* End of code obtained from Lecture 6: Structs */

/* GETTERS (ACCESSORS) */

void *peekFirst(LinkedList *list) {
    LinkedListNode *head;
    void *peekedValue;

    head = (*list).head;
    peekedValue = (*head).value;

    return peekedValue;
}

/* Obtained from Lecture 6: Structs */
/* Accessed 05/10/2020 */
void removeFirst(LinkedList *list) {
    LinkedListNode *newHead;
    LinkedListNode *oldHead;

    oldHead = (*list).head;
    newHead = (*oldHead).next;

    (*list).head = newHead;

    free(oldHead);

    (*list).count--;
}
/* End of code obtained from Lecture 6: Structs */

/* OPERATORS */

int isEmptyLinkedList(LinkedList *list) {
    return (*list).count == 0;
}

void printList(LinkedList *list) {
    int i;
    int iLast;
    LinkedListNode *currentNode;
    char *currentNodeValuePtr;
    char currentNodeValue;

    if (isEmptyLinkedList(list)) {
        printf("[]\n\n");
    }
    else {
        i = 0;
        iLast = (*list).count - 1;
        currentNode = (*list).head;
        printf("[");
        while (currentNode != NULL) {
            currentNodeValuePtr = (*currentNode).value;
            currentNodeValue = *currentNodeValuePtr;
            if (i == iLast) {
                printf("\'%c\']\n", currentNodeValue);
            }
            else {
                printf("\'%c\', ", currentNodeValue);
            }

            currentNode = (*currentNode).next;
            i++;
        }
        printf("\n");
    }
}

/* CLEANERS */

/* Obtained from Lecture 6: Structs */
/* Accessed 05/10/2020 */
void freeLinkedList(LinkedList *list) {
    freeLinkedListNode((*list).head);
    free(list);
}
/* End of code obtained from Lecture 6: Structs */
