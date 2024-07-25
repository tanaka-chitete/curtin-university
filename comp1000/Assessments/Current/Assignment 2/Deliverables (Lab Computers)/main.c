/*
 * NAME: main
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Act as the entry point for bracketmatch
 * CREATION: 01/10/2020
 * LAST MODIFICATION: 01/10/2020
 */

#include <stdio.h>
#include <stdlib.h>

#include "bracketmatchlogic.h"
#include "filereader.h"

int main(int argc, char *argv[]) {
    int exitStatus;
    const int REQ_ARGC = 2;

    char *filename;
    char **fileContents = NULL;
    int lineCount, lineLength;

    /* User has not specified a filename */
    if (argc != REQ_ARGC) {
        printf("usage: ./bracketmatch <filename>.c\n");
        printf("or\n");
        printf("usage: ./bracketmatch <filename>.txt\n");
        exitStatus = 1;
    }
    /* Tries to run program */
    else {
        filename = argv[1];
        lineCount = countLines(filename);
        lineLength = findLongestLine(filename);
        fileContents = readFile(filename, lineCount, lineLength);
        exitStatus = bracketMatchEntry(fileContents, lineCount);
    }

    return exitStatus;
}
