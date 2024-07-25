#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef HOUSES_H
#define HOUSES_H
#include "houses.h"
#endif

#ifndef BOOL_H
#define BOOL_H
#include "bool.h"
#endif

#ifndef HOUSE_H
#define HOUSE_H
#include "house.h"
#endif

#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include "linkedlist.h"
#endif

void filterByPrice(LinkedList *list, int price) {
    LinkedListNode *prevNode;
    LinkedListNode *currNode;
    House *house;

    if (price < 0) {
        perror("Cannot filter by a negative price");
    }
    else {
        currNode = (*list).head;
        house = (*currNode).value;
	    while (currNode != NULL && (*house).price > price) {
	        (*list).head = (*currNode).next;

            free((char*) (*house).address);
	        free(house);
	        free(currNode);
	        currNode = (*list).head;
            if (currNode != NULL) {
                house = (*currNode).value;
            }
	    }

	    while (currNode != NULL) {
            house = (*currNode).value;
	        while (currNode != NULL && (*house).price <= price) {
	            prevNode = currNode;
		        currNode = (*currNode).next;
                if (currNode != NULL) {
                    house = (*currNode).value;
                }
	        }

	        if (currNode == NULL) {
                return;
	        }

	        (*prevNode).next = (*currNode).next;

	        free((char*) (*house).address);
            free(house);
	        free(currNode);

            currNode = (*prevNode).next;
            if (currNode != NULL) {
                house = (*currNode).value;
            }
        }
    }
}

void filterBySuburb(LinkedList *list, const char *suburb) {
    LinkedListNode *prevNode;
    LinkedListNode *currNode;
    House *house;

    if (suburb == NULL) {
        perror("Cannot filter by a null suburb");
    }
    else if (suburb[0] == '\0') {
        perror("Cannot filter by a blank suburb");
    }
    else {
        currNode = (*list).head;
        house = (*currNode).value;
        while (currNode != NULL && 
        haveDifferentSuburb((*house).address, suburb)) {
            (*list).head = (*currNode).next;
            free((char*) (*house).address);
            free(house);
            free(currNode);
            currNode = (*list).head;
            if (currNode != NULL) {
                house = (*currNode).value;
            }
        }

        while (currNode != NULL) {
            while (currNode != NULL && 
            !haveDifferentSuburb((*house).address, suburb)) {
                prevNode = currNode;
                currNode = (*currNode).next;
                if (currNode != NULL) {
                    house = (*currNode).value;
                }
            }

            if (currNode == NULL) {
                return;
            }

            (*prevNode).next = (*currNode).next;

            free((char*) (*house).address);
            free(house);
            free(currNode);

            currNode = (*prevNode).next;
            if (currNode != NULL) {
                house = (*currNode).value;
            }
        }
    }
}

void flashSale(LinkedList *list) {
    flashSaleRec((*list).head);
}

int haveDifferentSuburb(const char *address, const char *suburb) {
    int haveDifferentSuburb, iAddress, iSuburb;

    haveDifferentSuburb = FALSE;
    iAddress = strlen(address) - 1;
    iSuburb = strlen(suburb) - 1;   

    while (!haveDifferentSuburb && (iAddress >= 0 && iSuburb >= 0)) {
        if (address[iAddress] != toupper(suburb[iSuburb])) {
	        haveDifferentSuburb = TRUE;
	    }
        else {
	        iAddress--;
            iSuburb--;
        }
    }

    return haveDifferentSuburb;
}

void flashSaleRec(LinkedListNode *node) {
    House *house;

    if (node != NULL) {
        flashSaleRec((*node).next);
    
        house = (*node).value;
	    (*house).price = (*house).price >> 1;
    }
}

