#ifndef LINKED_LIST_NODE_H
#define LINKED_LIST_NODE_H

#include <stdlib.h>

/* STRUCTURE */

typedef struct LinkedListNode {
    void *value;
    struct LinkedListNode *next;
} LinkedListNode;

/* CONSTRUCTORS */

LinkedListNode *makeLinkedListNode();

/* CLEANERS */

void freeLinkedListNode(LinkedListNode *node);

#endif

