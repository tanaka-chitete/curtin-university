#include <stdlib.h>

#include "stack.h"

#include "linkedlist.h"
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

LinkedList *makeStack() {
    return makeLinkedList();
}

/* SETTERS (MUTATORS) */

void push(LinkedList *stack, void *inValue) {
    insertFirst(stack, inValue);
}

/* GETTERS (ACCESSORS) */

void *peek(LinkedList *stack) {
    return peekFirst(stack);
}

void pop(LinkedList *stack) {
    removeFirst(stack);
}

/* OPERATORS */

int isEmptyStack(LinkedList *stack) {   
    return isEmptyLinkedList(stack);
}

void printStack(LinkedList *stack) {
    printLinkedList(stack);
}

/* CLEANERS */

void freeStack(LinkedList *stack) {
    freeLinkedList(stack);
}
