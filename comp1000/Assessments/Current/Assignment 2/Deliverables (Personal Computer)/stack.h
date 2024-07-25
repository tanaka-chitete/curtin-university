#include "linkedlist.h"
#include "linkedlistnode.h"

/* STRUCTURE */

/* CONSTRUCTORS */

LinkedList *makeStack();

/* SETTERS (MUTATORS) */

void push(LinkedList *stack, void *inValue);

/* GETTERS (ACCESSORS) */

void *peek(LinkedList *stack);
void pop(LinkedList *stack);

/* OPERATORS */

int isEmptyStack(LinkedList *stack);
void printStack(LinkedList *stack);

/* CLEANERS */

void freeStack(LinkedList *stack);
