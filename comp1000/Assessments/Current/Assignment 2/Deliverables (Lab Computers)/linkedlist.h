#include "linkedlistnode.h"

/* STRUCTURE */

#ifndef LINKED_LIST_H
#define LINKED_LIST_H

typedef struct {
    LinkedListNode *head;
    int count;
} LinkedList;

#endif

/* CONSTRUCTORS */

LinkedList *makeLinkedList();

/* SETTERS (MUTATORS) */

void insertFirst(LinkedList *list, void *inValue);

/* GETTERS (ACCESSORS) */

void *peekFirst(LinkedList *list);
void removeFirst(LinkedList *list);

/* OPERATORS */

int isEmptyLinkedList(LinkedList *list);
void printList(LinkedList *list);

/* CLEANERS */

void freeLinkedList(LinkedList *list);
