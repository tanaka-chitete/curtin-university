#include <stdlib.h>

#ifndef LINKEDLISTNODE_H
#define LINKEDLISTNODE_H
#include "linkedlistnode.h"
#endif

/* CONSTRUCTORS */

/* Adapted from Lecture 6: Structs */
/* Accessed 05/10/2020 */
LinkedListNode *makeLinkedListNode() {
    LinkedListNode *newNode;

    newNode = (LinkedListNode*) malloc(sizeof(LinkedListNode));
    (*newNode).next = NULL;

    return newNode;
}
/* End of code obtained from Lecture 6: Structs */

/* CLEANERS */

/* Adapted from Lecture 6: Structs */
/* Accessed 05/10/2020 */
void freeLinkedListNode(LinkedListNode *node) {
    if (node != NULL) {
        freeLinkedListNode((*node).next);
        free(node);
    }
}
/* End of code obtained from Lecture 6: Structs */
