#include <stdlib.h>

/* STRUCTURE */

#ifndef LINKED_LIST_NODE_H
#define LINKED_LIST_NODE_H

typedef struct LinkedListNode {
    void *value;
    struct LinkedListNode *next;
} LinkedListNode;

#endif

/* CONSTRUCTORS */

LinkedListNode *makeLinkedListNode();

/* SETTERS (MUTATORS) */

void setValue(void *inValue);

/* GETTERS (ACCESSORS) */

void *getValue();

/* CLEANERS */

void freeLinkedListNode(LinkedListNode *node);
