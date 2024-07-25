#include "linkedlist.h"

LinkedList *readFile(char *filename);
void writeFile(LinkedList *list, char *filename);
const char *getField(char *line, int iField);
void writeFileRec(LinkedListNode *node, FILE *filePtr);

