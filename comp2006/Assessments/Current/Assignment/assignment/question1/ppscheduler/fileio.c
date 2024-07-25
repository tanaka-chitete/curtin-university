/* Parts of this file comprise externally-adapted code */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef FILEIO_H
#define FILEIO_H
#include "fileio.h"
#endif

#ifndef PROCESS_H
#define PROCESS_H
#include "process.h"
#endif

extern char *strndup(const char *s, size_t n);

int read(const char *filename, struct Process processes[100], int burstsRemaining[100]) {
    FILE *filePtr = fopen(filename, "r");
    int i;

    char buffer[128]; 
    char *bufferCopy0 = NULL, *bufferCopy1 = NULL, 
        *bufferCopy2 = NULL;
    int inArrivalTime, inBurstTime, inPriority; 

    printf("\"%s\"\n", filename);

    i = 0;
    if (filePtr != NULL) {
	    /* Adapted from sehe */
	    /* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
    	/* Accessed 14/11/2020 */
	    while (fgets(buffer, 128, filePtr)) {
            bufferCopy0 = strndup(buffer, 128 * sizeof(int));
            bufferCopy1 = strndup(buffer, 128 * sizeof(int));
            bufferCopy2 = strndup(buffer, 128 * sizeof(int));
 
	        inArrivalTime = atoi(getField(bufferCopy0, 1));
	        inBurstTime = atoi(getField(bufferCopy1, 2));
	        inPriority = atoi(getField(bufferCopy2, 3));

            processes[i].pid = i + 1;
            processes[i].arrivalTime = inArrivalTime;
            processes[i].burstTime = inBurstTime;
            processes[i].priority = inPriority;
            burstsRemaining[i] = processes[i].burstTime;

            free(bufferCopy0);
            free(bufferCopy1);
            free(bufferCopy2);

            i++;
        }

        fclose(filePtr); 
	    /* End of code adapted from sehe */
	}
    else {
        printf("error: filename is invalid\n");
    }

    return i;
}

/* Adapted from sehe */
/* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
/* Accessed 14/11/2020 */
const char *getField(char *line, int iField) {
    const char *tok;

    for (tok = strtok(line, " "); tok && *tok; tok = strtok(NULL, " \n")) {
        if (!--iField) {
	        return tok;
        }
    }

    return NULL;
}
/* End of code adapted from sehe */
