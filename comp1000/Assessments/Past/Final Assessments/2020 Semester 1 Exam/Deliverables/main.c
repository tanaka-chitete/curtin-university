#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#ifndef FILEIO_H
#define FILEIO_H
#include "fileio.h"
#endif

#ifndef HOUSES_H
#define HOUSES_H
#include "houses.h" 
#endif

#ifndef SCAN_H
#define SCAN_H
#include "scan.h"
#endif

#define REQ_ARG_C 3
#define SUCCESS 0
#define FAILURE !SUCCESS
#define FLASH_SALE 1
#define FILTER_BY_PRICE 1

int main(int argc, char *argv[]) {
    int exitStatus;

    char *inputFilename;
    char *outputFilename;

    int outcome, price, selection;
    const char *suburb;

    LinkedList *list;

    if (argc != REQ_ARG_C) {
        exitStatus = FAILURE;
        printf("usage: ./houses <input filename> <output filename>\n");
    }
    else {
    	inputFilename = argv[1];
    	outputFilename = argv[2];

        list = readFile(inputFilename);
        if (list == NULL) {
	        exitStatus = FAILURE;
            printf("Could not open %s\n", inputFilename);
        }
        else {
            printf("Filter Menu\n\n"
                "1. Filter by Price\n"
                "2. Filter by Suburb\n\n");
            selection = scanInt();
            printf("\n");
    
            if (selection == FILTER_BY_PRICE) {
                printf("Price\n\n");
                price = scanInt();
                printf("\n");

                filterByPrice(list, price);
            }
            else {
                printf("Suburb\n\n");
                suburb = scanString();
                printf("\n");
       
                filterBySuburb(list, suburb);
            
                free((char*) suburb);
            }

            srand(time(NULL));
            outcome = rand() % 4 + 1;
            if (outcome != FLASH_SALE) {
                printf("Flash Sale: No\n\n");
            }
            else {
                printf("Flash Sale: Yes\n\n");
                flashSale(list);
            }

            writeFile(list, outputFilename);

            freeLinkedList(list);

            exitStatus = SUCCESS;
        }
    }

    return exitStatus;
}

