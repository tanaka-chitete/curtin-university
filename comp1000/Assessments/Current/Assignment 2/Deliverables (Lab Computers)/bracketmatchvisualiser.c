#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#ifndef BRACKETMATCHVISUALISER_H
#define BRACKETMATCHVISUALISER_H
#include "bracketmatchvisualiser.h"
#endif

#ifndef BOOL_H
#define BOOL_H
#include "bool.h"
#endif

#ifndef BRACKETMATCHLOGIC_H
#define BRACKETMATCHLOGIC_H
#include "bracketmatchlogic.h"
#endif

#ifndef BRACKETMATCHSCENARIOS_H
#define BRACKETMATCHSCENARIOS_H
#include "bracketmatchscenarios.h"
#endif 

#ifndef COLOURCODES_H
#define COLOURCODES_H
#include "colourcodes.h"
#endif

#ifndef COLOURLOGIC_H
#define COLOURLOGIC_H
#include "colourlogic.h"
#endif

#ifndef STACK_H
#define STACK_H
#include "stack.h"
#endif

/*
 * NAME: printLinesBeforeCursor
 * IMPORT(S): fileContents (char**), iEndLine (int)
 * EXPORT(S): NONE
 * PURPOSE: Print file contents up until line before cursor
 * CREATION: 05/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printLinesBeforeCursor(char **fileContents, int iEndLine) {
    int i, j;
    int currentLineLength;
    char currentChar;
    char *highlightCode;

    for (i = 0; i <= iEndLine; i++) {
        currentLineLength = strlen(fileContents[i]);
        for (j = 0; j < currentLineLength; j++) {
            currentChar = fileContents[i][j];
            if (isBracket(currentChar)) {
                highlightCode = toHighlightCode(currentChar);
                printf("%s%c" RESET, highlightCode, currentChar);
            }
            else {
                printf("%c", currentChar);
            }
        }
        printf("\n");
    }
}

/*
 * NAME: printCharsUpToCursor
 * IMPORT(S): fileContents (char**), iLine (int), jEndChar (int), 
 *            bracketStack (LinkedList*)
 * EXPORT(S): NONE
 * PURPOSE: Print file contents of line cursor is on until character cursor is
 *          pointed at
 * CREATION: 08/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printCharsUpToCursor(char **fileContents, int iLine, int jEndChar, 
    LinkedList *bracketStack) {
    int j;
    char currentChar;
    char *highlightCode;

    for (j = 0; j <= jEndChar; j++) {
        currentChar = fileContents[iLine][j];
        if (isBracket(currentChar)) {
            highlightCode = toHighlightCode(currentChar);
            printf("%s%c" RESET, highlightCode, currentChar);
        }
        else {
            printf("%c", currentChar);
        }
    }
}

/*
 * NAME: printCharsAfterCursor
 * IMPORT(S): fileContents (char**), iLine (int), jStartChar (int) 
 * EXPORT(S): NONE
 * PURPOSE: Print file contents of line cursor is on after character cursor is 
 *          pointed at 
 * CREATION: 08/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printCharsAfterCursor(char **fileContents, int iLine, int jStartChar) {
    int j;
    int lineLength;
    char currentChar;

    lineLength = strlen(fileContents[iLine]);
    for (j = jStartChar; j < lineLength; j++) {
        currentChar = fileContents[iLine][j];
        printf("%c", currentChar);
    }
    printf("\n");
}

/*
 * NAME: printCursor
 * IMPORT(S): fileContents (char**), i (int), j (int), 
 *            bracketStack (LinkedList*)
 * EXPORT(S): NONE
 * PURPOSE: Print cursor along with accompanying message 
 * CREATION: 05/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printCursor(char** fileContents, int i, int j, LinkedList *bracketStack, 
    int bracketMatchStatus) {
    int i1, j1;
    char *openBracketPtr;
    char openBracket;
    char closedBracket;

    /* Converts to one-based index and prepares to print cursor below line */
    i1 = i + 2;
    /* Converts to one-based index */
    j1 = j + 1;

    printf("\033[%d;%dH^\n", i1, j1);

    if (bracketMatchStatus == FS1) {
        openBracketPtr = peek(bracketStack);
        closedBracket = toClosedBracket(*openBracketPtr);
        printf(RED_HIGHLIGHT "\033[%d;%dH\'%c\' expected after \'%c\'\n" 
            RESET, i1 + 1, j1, closedBracket, *openBracketPtr);
    }
    else if (bracketMatchStatus == FS2) {
        closedBracket = fileContents[i][j];
        openBracket = toOpenBracket(closedBracket);
        printf(RED_HIGHLIGHT "\033[%d;%dH\'%c\' expected before \'%c\'\n"
            RESET, i1 + 1, j1, openBracket, closedBracket);
    }
}

/*
 * NAME: printLinesAfterCursor
 * IMPORT(S): fileContents (char**), iStartLine (int), iEndLine (int)
 * EXPORT(S): NONE
 * PURPOSE: Print file contents from line after cursor 
 * CREATION: 05/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printLinesAfterCursor(char **fileContents, int iStartLine, int iEndLine) {
    int i, j;
    int currentLineLength;

    for (i = iStartLine; i < iEndLine; i++) {
        currentLineLength = strlen(fileContents[i]);
        for (j = 0; j < currentLineLength; j++) {
            printf("%c", fileContents[i][j]);
        }
        printf("\n");
    }
}

/*
 * NAME: printFinalMessage
 * IMPORT(S): bracketStack (LinkedList*), reachedEOF (int) 
 * EXPORT(S): NONE
 * PURPOSE: Print final bracket-matching message 
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 27/10/2020
 */

void printFinalMessage(LinkedList *bracketStack, int reachedEOF) {
    char *currentOpenBracketPtr;
    char expectedCloseBracket;
    
    if (reachedEOF) {
        /* There is at least one unmatched open bracket */
        if (!isEmptyStack(bracketStack)) {
            currentOpenBracketPtr = peek(bracketStack);
            expectedCloseBracket = toClosedBracket(*currentOpenBracketPtr);
            printf(RED_HIGHLIGHT "Expected \'%c\' before End of File\n\n" 
                RESET, expectedCloseBracket);
        }
        /* All open brackets were matched */
        else {
            printf(GREEN_BODY "All Good\n\n" RESET);
        }
    }
}
