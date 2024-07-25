#include <stdio.h>
#include <stdlib.h>

#ifndef FILEREADER_H
#define FILEREADER_H
#include "filereader.h"
#endif

/*
 * NAME: readFile
 * IMPORT(S): filename (char*)
 * EXPORT(S): fileContents (char**)
 * PURPOSE: Read the contents of a .c or .txt file into an array
 * CREATION: 01/10/2020
 * LAST MODIFICATION: 03/10/2020
 */

/* Adapted from Lecture 6: Input and Output */
/* Accessed 01/10/2020 */
char **readFile(char *filename, int lineCount, int lineLength) {
    FILE *filePtr = fopen(filename, "r");
    char ch;
    int in;
    char **fileContents;
    int i, j;

    fileContents = calloc(lineCount, sizeof(char*));
    for (i = 0; i < lineCount; i++) {
        fileContents[i] = calloc(lineLength + 1, sizeof(char));
    }

    if (filePtr == NULL) {
        perror("Error opening file (readFile)");
    }
    else {
        i = 0;
        j = 0;
        do {
            in = fgetc(filePtr);
            if (in != EOF) {
                ch = (char) in;
                if (ch == '\n') {
                    i++;
                    j = 0;
                }
                else {
                    fileContents[i][j] = ch;
                    j++;
                }
            }
        }
        while (in != EOF);
    }

    return fileContents;
}
/* End of code adapted from Lecture 6: Input and Output */

/*
 * NAME: countLines
 * IMPORT(S): filename (char*)
 * EXPORT(S): lineCount (int)
 * PURPOSE: Count the number of lines in a .c or .txt file
 * CREATION: 01/10/2020
 * LAST MODIFICATION: 03/10/2020
 */

/* Adapted from Lecture 6: Input and Output */
/* Accessed 01/10/2020 */
int countLines(char *filename) {
    FILE *filePtr = fopen(filename, "r");
    char ch;
    int in;
    int lineCount;

    lineCount = -1;
    if (filePtr == NULL) {
        perror("Error opening file (countLines)");
    }
    else {
        lineCount = 0;
        do {
            in = fgetc(filePtr);
            if (in != EOF) {
                ch = (char) in;
                if (ch == '\n') {
                    lineCount++;
                }
            }
        }
        while (in != EOF);

        if (ferror(filePtr)) {
            perror("Error reading file");
        }
        fclose(filePtr);
    }

    return lineCount;
}
/* End of code adapted from Lecture 6: Input and Output */

/*
 * NAME: findLongestLine
 * IMPORT(S): filename (char*)
 * EXPORT(S): longestLineLength (int)
 * PURPOSE: Get the length of the longest line of a .c or .txt file
 * CREATION: 03/10/2020
 * LAST MODIFICATION: 03/10/2020
 */

/* Adapted from Lecture 6: Input and Output */
/* Accessed 03/10/2020 */
int findLongestLine(char *filename) {
    FILE *file = fopen(filename, "r");
    char ch;
    int in;
    int currentLineLength, longestLineLength;

    currentLineLength = -1;
    longestLineLength = -1;
    if (file == NULL) {
        perror("Error opening file (findLongestLine)");
    }
    else {
        currentLineLength = 0;
        longestLineLength = 0;
        do {
            in = fgetc(file);
            if (in != EOF) {
                ch = (char) in;
                if (ch == '\n') {
                    /* Prepares to count length of next line */
                    currentLineLength = 0;
                }
                else {
                    currentLineLength++;
                    /* Updates length of longest line found so far */
                    if (currentLineLength > longestLineLength) {
                        longestLineLength = currentLineLength;
                    }
                }
            }
        }
        while (in != EOF);

        if (ferror(file)) {
            perror("Error reading file");
        }
        fclose(file);
    }

    return longestLineLength;
}
/* End of code adapted from Lecture 6: Input and Output */
