#ifndef LINKED_LIST_H
#define LINKED_LIST_H

#include "linkedlistnode.h"

/* STRUCTURE */

typedef struct {
    LinkedListNode *head;
    int count;
} LinkedList;

/* CONSTRUCTORS */

LinkedList *makeLinkedList();

/* SETTERS (MUTATORS) */

void insertFirst(LinkedList *list, void *inValue);

/* GETTERS (ACCESSORS) */

void *peekFirst(LinkedList *list);
void removeFirst(LinkedList *list);

/* OPERATORS */

int isEmptyLinkedList(LinkedList *list);
void printLinkedList(LinkedList *list);

/* CLEANERS */

void freeLinkedList(LinkedList *list);

#endif
