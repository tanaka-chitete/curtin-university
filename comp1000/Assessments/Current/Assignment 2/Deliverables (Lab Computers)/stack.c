#include <stdlib.h>

#ifndef STACK_H
#define STACK_H
#include "stack.h"
#endif

#ifndef BOOL_H
#define BOOL_H
#include "bool.h"
#endif

#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include "linkedlist.h"
#endif

#ifndef LINKEDLISTNODE_H
#define LINKEDLISTNODE_H
#include "linkedlistnode.h"
#endif

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
    printList(stack);
}

/* CLEANERS */

void freeStack(LinkedList *stack) {
    freeLinkedList(stack);
}
