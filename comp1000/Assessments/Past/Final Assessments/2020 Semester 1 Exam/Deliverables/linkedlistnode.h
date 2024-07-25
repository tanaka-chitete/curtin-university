/* #include <stdlib.h> */

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

/* CLEANERS */

void freeLinkedListNode(LinkedListNode *node);

