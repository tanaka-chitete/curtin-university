/* Parts of this file comprise externally-adapted code */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef FILEIO_H
#define FILEIO_H
#include "fileio.h"
#endif

#ifndef HOUSE_H
#define HOUSE_H
#include "house.h"
#endif

#ifndef LINKEDLIST_H
#define LINKEDLIST_H
#include "linkedlist.h"
#endif

extern char *strndup(const char *s, size_t n);

LinkedList *readFile(char *filename) {
    FILE *filePtr = fopen(filename, "r");
    LinkedList *list;
    House *newHouse;

    const char *inAddress;
    char buffer[128]; 
    char *bufferCopy1 = NULL, *bufferCopy2 = NULL, 
        *bufferCopy3 = NULL, *bufferCopy4 = NULL;
    int inBedroomCount, inBathroomCount, inPrice; 

    if (filePtr == NULL) {
	    list = NULL;
    }
    else {
	    list = makeLinkedList();
	    /* Adapted from sehe */
	    /* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
    	/* Accessed 14/11/2020 */
	    while (fgets(buffer, 128, filePtr)) {
            bufferCopy1 = strndup(buffer, 128 * sizeof(char));
            bufferCopy2 = strndup(buffer, 128 * sizeof(char));
            bufferCopy3 = strndup(buffer, 128 * sizeof(char));
            bufferCopy4 = strndup(buffer, 128 * sizeof(char));
 
	        inBedroomCount = atoi(getField(bufferCopy1, 1));
	        inBathroomCount = atoi(getField(bufferCopy2, 2));
	        inPrice = atoi(getField(bufferCopy3, 3));
	        inAddress = getField(bufferCopy4, 4);

	        newHouse = makeHouse(inBedroomCount, inBathroomCount, inPrice, 
                inAddress);

	        insertFirst(list, newHouse);
        
            free(bufferCopy1);
            free(bufferCopy2);
            free(bufferCopy3);
            free(bufferCopy4);
        }

        fclose(filePtr); 
	    /* End of code adapted from sehe */
	}

    return list;
}

void writeFile(LinkedList *list, char *filename) {
    FILE *filePtr = fopen(filename, "w");
    LinkedListNode *node;

    if (filePtr == NULL) {
        perror("Could not create file");
    }	    
    else {
        node = (*list).head;
        writeFileRec(node, filePtr);
	    fclose(filePtr);
    }
}

/* Adapted from sehe */
/* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
/* Accessed 14/11/2020 */
const char *getField(char *line, int iField) {
    const char *tok;

    for (tok = strtok(line, ","); tok && *tok; tok = strtok(NULL, ",\n")) {
        if (!--iField) {
	        return tok;
        }
    }

    return NULL;
}
/* End of code adapted from sehe */

void writeFileRec(LinkedListNode *node, FILE *filePtr) {
    House *house;
    int bedroomCount, bathroomCount, price;
    const char *address;
    char buffer[128];

    if (node != NULL) {
        writeFileRec((*node).next, filePtr);
        
        house = (*node).value;

	    bedroomCount = (*house).bedroomCount;
	    bathroomCount = (*house).bathroomCount;
	    price = (*house).price;
	    address = (*house).address;
	
	    sprintf(buffer, "%d,%d,%d,%s\n", bedroomCount, bathroomCount, price, 
            address);
        
	    fputs(buffer, filePtr);
    }
}

