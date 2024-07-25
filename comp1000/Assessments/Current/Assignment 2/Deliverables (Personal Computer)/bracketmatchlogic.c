#include <stdio.h>
#include <string.h>
#include <unistd.h>

#ifndef BRACKETMATCHLOGIC_H
#define BRACKETMATCHLOGIC_H
#include "bracketmatchlogic.h"
#endif

#ifndef BOOL_H
#define BOOL_H
#include "bool.h"
#endif

#ifndef BRACKETMATCHSCENARIOS_H
#define BRACKETMATCHSCENARIOS_H
#include "bracketmatchscenarios.h"
#endif

#ifndef BRACKETMATCHVISUALISER_H
#define BRACKETMATCHVISUALISER_H
#include "bracketmatchvisualiser.h"
#endif

#ifndef BRACKETS_H
#define BRACKETS_H
#include "brackets.h"
#endif

#ifndef STACK_H
#define STACK_H
#include "stack.h"
#endif

/*
 * NAME: bracketMatchEntry
 * IMPORT(S): fileContents (char**), lineCount (int)
 * EXPORT(S): exitStatus (int)
 * PURPOSE: Act as the entry point for bracketMatch
 * CREATION: 10/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

int bracketMatchEntry(char **fileContents, int lineCount) {
    int exitStatus;

    if (fileContents == NULL) {
        exitStatus = 1;
    }
    else {
        bracketMatch(fileContents, lineCount);
        exitStatus = 0;
    }

    return exitStatus;
}

/*
 * NAME: bracketMatch
 * IMPORT(S): fileContents (char**), lineCount (int)
 * EXPORT(S): NONE
 * PURPOSE: Bracket match .c and .txt files
 * CREATION: 03/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

void bracketMatch(char **fileContents, int lineCount) {
    LinkedList *bracketStack;
    int bracketMatchStatus, currentLineLength, i, j, iLast, jLast, reachedEOF, 
        shouldStop;
    char *currentCharPtr;

    bracketStack = makeStack();
    shouldStop = FALSE;
    reachedEOF = FALSE;

    i = 0;
    iLast = lineCount - 1;
    while (!shouldStop && i < lineCount) {
        currentLineLength = strlen(fileContents[i]) - 1;
        j = 0;
        jLast = currentLineLength - 1;
        while (!shouldStop && j < currentLineLength) {
            system("clear");

            /* Uses char* instead of char for use with generic linked list */
            currentCharPtr = &(fileContents[i][j]);
            bracketMatchStatus = updateStatus(bracketStack, currentCharPtr);

            /* Prints file contents and cursor with appropriate message */
            printLinesBeforeCursor(fileContents, i - 1);
            printCharsUpToCursor(fileContents, i, j, bracketStack);
            printCharsAfterCursor(fileContents, i, j + 1);
            printCursor(fileContents, i, j, bracketStack, bracketMatchStatus);
            printLinesAfterCursor(fileContents, i + 1, lineCount);

            if (bracketMatchStatus == FS1 || bracketMatchStatus == FS2) {
                shouldStop = TRUE;
            }

            if (i == iLast && j == jLast) {
                reachedEOF = TRUE;
            }

            printf("\n");
            /* Prints bracket stack if program is compiled using make STACK */
            #ifdef STACK
                printStack(bracketStack);
            #endif

            usleep(200000); /* Sleeps for 0.2 seconds */

            j++;
        }

        i++;
    }
    printFinalMessage(bracketStack, reachedEOF);
}

/*
 * NAME: updateStatus
 * IMPORT(S): bracketStack (LinkedList*), charPtr (char*)
 * EXPORT(S): bracketMatchStatus (int)
 * PURPOSE: Update bracket match status
 * CREATION: 10/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

int updateStatus(LinkedList *bracketStack, char *charPtr) {
    int bracketMatchStatus;
    char closedBracket;
    char *openBracketPtr;
 
    /* Character is an open bracket */
    if (isOpenBracket(*charPtr)) {
        /* Prepares to bracket match current bracket */
        push(bracketStack, charPtr);
        bracketMatchStatus = NFS2;
    }
    else if (isClosedBracket(*charPtr)) {
        /* Closed bracket is found before an open bracket */
        if (isEmptyStack(bracketStack)) {
            bracketMatchStatus = FS2;
        }
        else {
            /* Gets closed bracket counterpart of open bracket */
            openBracketPtr = peek(bracketStack);
            closedBracket = toClosedBracket(*openBracketPtr);

            /* Most recent open bracket doesn't match closed bracket */
            if (closedBracket != *charPtr) {
                bracketMatchStatus = FS1;
            }
            /* Most recent open bracket matches closed bracket */
            else {
                bracketMatchStatus = NFS1;
                /* Prepares to bracket match next bracket on stack */
                pop(bracketStack);
            }
        }
    }
    /* Character is a non-bracket character */
    else {
        bracketMatchStatus = NFS3;
    }

    return bracketMatchStatus;
}

/*
 * NAME: isBracket
 * IMPORT(S): character (char)
 * EXPORT(S): isBracket (int)
 * PURPOSE: Checks if a character is a bracket
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

int isBracket(char character) {
    int isBracket;
    
    isBracket = FALSE;
    switch (character) {
        case OPEN_ROUND:
            isBracket = TRUE;
            break;
        case CLOSED_ROUND:
            isBracket = TRUE;
            break;
        case OPEN_CURLY:
            isBracket = TRUE;
            break;
        case CLOSED_CURLY:
            isBracket = TRUE;
            break;
        case OPEN_SQUARE:
            isBracket = TRUE;
            break;
        case CLOSED_SQUARE:
            isBracket = TRUE;
            break; 
        case OPEN_ANGLE:
            isBracket = TRUE;
            break;
        case CLOSED_ANGLE:
            isBracket = TRUE;
            break;   
    }

    return isBracket;
}

/*
 * NAME: isOpenBracket
 * IMPORT(S): character (char)
 * EXPORT(S): isOpenBracket (int)
 * PURPOSE: Checks if a character is an open bracket
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

int isOpenBracket(char character) {
    int isOpenBracket;
    
    isOpenBracket = FALSE;
    switch (character) {
        case OPEN_ROUND:
            isOpenBracket = TRUE;
            break;
        case OPEN_CURLY:
            isOpenBracket = TRUE;
            break;
        case OPEN_SQUARE:
            isOpenBracket = TRUE;
            break; 
        case OPEN_ANGLE:
            isOpenBracket = TRUE;
            break;   
    }

    return isOpenBracket;
}

/*
 * NAME: isClosedBracket
 * IMPORT(S): character (char)
 * EXPORT(S): isClosedBracket (int)
 * PURPOSE: Checks if a character is a closed bracket
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

int isClosedBracket(char character) {
    int isClosedBracket;
    
    isClosedBracket = FALSE;
    switch (character) {
        case CLOSED_ROUND:
            isClosedBracket = TRUE;
            break;
        case CLOSED_CURLY:
            isClosedBracket = TRUE;
            break;
        case CLOSED_SQUARE:
            isClosedBracket = TRUE;
            break; 
        case CLOSED_ANGLE:
            isClosedBracket = TRUE;
            break;   
    }

    return isClosedBracket;
}

/*
 * NAME: toOpenBracket
 * IMPORT(S): closedBracket (char)
 * EXPORT(S): openBracket (char)
 * PURPOSE: Converts an open bracket into its closed bracket counterpart
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

char toOpenBracket(char closedBracket) {
    char openBracket;

    switch (closedBracket) {
        case CLOSED_ROUND:
            openBracket = OPEN_ROUND;
            break;
        case CLOSED_CURLY:
            openBracket = OPEN_CURLY;
            break;
        case CLOSED_SQUARE:
            openBracket = OPEN_SQUARE;
            break; 
        case CLOSED_ANGLE:
            openBracket = OPEN_ANGLE;
            break;   
    }

    return openBracket;
}

/*
 * NAME: toClosedBracket
 * IMPORT(S): openBracket (char)
 * EXPORT(S): closedBracket (char)
 * PURPOSE: Converts an closed bracket into its open bracket counterpart
 * CREATION: 09/10/2020
 * LAST MODIFICATION: 10/10/2020
 */

char toClosedBracket(char openBracket) {
    char closedBracket;

    switch (openBracket) {
        case OPEN_ROUND:
            closedBracket = CLOSED_ROUND;
            break;
        case OPEN_CURLY:
            closedBracket = CLOSED_CURLY;
            break;
        case OPEN_SQUARE:
            closedBracket = CLOSED_SQUARE;
            break; 
        case OPEN_ANGLE:
            closedBracket = CLOSED_ANGLE;
            break;   
    }

    return closedBracket;
}
