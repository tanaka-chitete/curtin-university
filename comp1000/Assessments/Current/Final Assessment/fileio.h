#ifndef FILEIO_H
#define FILEIO_H

#include "hero.h"
#include "linkedlist.h"

Hero *readHeroFile(char *filename);
LinkedList *readEnemiesFile(char *filename);
const char *getField(char *line, int iField);

#endif

