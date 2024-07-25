#include "linkedlist.h"

void filterByPrice(LinkedList *list, int price);
void filterBySuburb(LinkedList *list, const char *suburb);
void flashSale(LinkedList *list);
int haveDifferentSuburb(const char *address, const char *suburb);
void flashSaleRec(LinkedListNode *node);

