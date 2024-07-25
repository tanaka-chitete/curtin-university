#include "stack.h"

int bracketMatchEntry(char **fileContents, int lineCount);
void bracketMatch(char **fileContents, int lineCount);
int updateStatus(LinkedList *bracketStack, char *charPtr);
int isBracket(char character);
int isOpenBracket(char character);
int isClosedBracket(char character);
char toOpenBracket(char closedBracket);
char toClosedBracket(char openBracket);
