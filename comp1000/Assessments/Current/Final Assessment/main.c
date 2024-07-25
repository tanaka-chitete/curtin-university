#include <stdio.h>
#include <stdlib.h>

#include "fileio.h"
#include "hero.h"
#include "linkedlist.h"
#include "rpgvisualiser.h"
#include "newsleep.h"

#define REQ_ARG_C 4
#define SUCCESS 0
#define FAILURE !SUCCESS

int main(int argc, char *argv[]) {
    int exitStatus;

    char *heroFilename;
    char *enemiesFilename;
    float sleepTime;

    Hero *hero;
    LinkedList *stack;

    if (argc != REQ_ARG_C) {
        exitStatus = FAILURE;
        printf("usage: ./rpg <hero filename> <enemies filename> " 
            "<sleep time>\n");
    }
    else {
    	heroFilename = argv[1];
    	enemiesFilename = argv[2];
        sleepTime = atof(argv[3]);

        hero = readHeroFile(heroFilename);
        stack = readEnemiesFile(enemiesFilename);
        if (hero == NULL) {
            exitStatus = FAILURE;
            printf("Could not open %s\n", heroFilename);
        }

        if (stack == NULL) {
	        exitStatus = FAILURE;
            printf("Could not open %s\n", enemiesFilename);
        }

        if (sleepTime < 1.0) {
            exitStatus = FAILURE;
            printf("Sleep time cannot be less than 1 second\n");
        }

        if (hero != NULL && stack != NULL && sleepTime >= 1.0) {
            system("clear");

            printf("Welcome to RPG.\n");
            printf("\n");

            introduceHero(hero);
            introduceEnemies(stack);

            newSleep(sleepTime);
            system("clear");                     

            playGame(hero, stack, sleepTime); 
        }
    }

    freeStack(stack);

    return exitStatus;
}

