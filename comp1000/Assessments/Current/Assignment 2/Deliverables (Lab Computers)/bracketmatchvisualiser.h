#include "linkedlist.h"

void printLinesBeforeCursor(char **fileContents, int iEndLine);
void printCharsUpToCursor(char **fileContents, int iLine, int jEndChar, 
    LinkedList *bracketStack);
void printCharsAfterCursor(char **fileContents, int iLine, int jStartChar);
void printCursor(char **fileContents, int iLine, int jEndChar, 
    LinkedList *bracketStack, int bracketMatchStatus);
void printLinesAfterCursor(char **fileContents, int iStartLine, int iEndLine);
void printFinalMessage(LinkedList *bracketStack, int reachedEOF);
