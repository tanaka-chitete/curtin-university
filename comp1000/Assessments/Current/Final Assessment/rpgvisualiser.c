#include <stdio.h>
#include <time.h>

#include "rpgvisualiser.h"

#include "enemy.h"
#include "hero.h"
#include "newsleep.h"
#include "stack.h"

#define HERO 0
#define ENEMY !HERO

void introduceHero(Hero *hero) {
    printf("Hero:\n");
    printf("  %s\n", (*hero).name);
    printf("    HP:  %d\n", (*hero).currentHitPoints);
    printf("    ATK: %d\n", (*hero).attackPoints);
    printf("    DEF: %d\n", (*hero).defencePoints);
    printf("    MED: %d\n", (*hero).medicineCount);
    printf("\n");
}

void introduceEnemies(LinkedList *stack) {
    LinkedListNode *currentNode;
    Enemy *currentEnemy;

    printf("Enemies:\n");
    currentNode = (*stack).head;
    while (currentNode != NULL) {
        currentEnemy = (*currentNode).value;
        printf("  %s\n", (*currentEnemy).name);
        printf("    HP:  %d\n", (*currentEnemy).currentHitPoints);
        printf("    ATK: %d\n", (*currentEnemy).attackPoints);
        printf("    DEF: %d\n", (*currentEnemy).defencePoints);

        currentNode = (*currentNode).next;
    }
    printf("\n");
}

void playGame(Hero *hero, LinkedList *stack, float sleepTime) {
    LinkedListNode *currentNode;
    Enemy *currentEnemy;
    int turn;

    currentNode = (*stack).head;
    currentEnemy = (*currentNode).value;
   
    turn = HERO; 
    while ((*hero).currentHitPoints > 0 &&
           (*hero).attackPoints > ((*currentEnemy).attackPoints / 2)) {
        system("clear");

        printf("%s\n", (*hero).name);
        printf("  %d\n", (*hero).currentHitPoints);
        printf("  %d\n", (*hero).attackPoints);
        printf("  %d\n", (*hero).defencePoints);
        printf("  %d\n", (*hero).medicineCount);
        printf("\n");

        turn = playTurn(hero, currentEnemy, turn);

        printf("    %s\n", (*currentEnemy).name);
        printf("      %d\n", (*currentEnemy).currentHitPoints);
        printf("      %d\n", (*currentEnemy).attackPoints);
        printf("      %d\n", (*currentEnemy).defencePoints);
        printf("\n");

        if ((*currentEnemy).currentHitPoints <= 0) {
            printf("%s has been defeated!\n", (*currentEnemy).name);

            currentNode = (*currentNode).next;
            currentEnemy = (*currentNode).value;
        } 

        getReward(hero);

        newSleep(sleepTime);
        system("clear"); 
    }
    
    if (isEmptyStack(stack)) {
        printf("%s has defeated all enemies. The world is saved!\n", 
            (*hero).name);
    }
    else if ((*hero).currentHitPoints == 0) {
        printf("%s has been defeated. The world is doomed...\n",
            (*hero).name); 
    }
    else if ((*hero).attackPoints <= ((*currentEnemy).attackPoints / 2)) {
        printf("%s's attack strength is too low. The world is doomed...\n", 
            (*hero).name);
    }
}

int playTurn(Hero *hero, Enemy *currentEnemy, int turn) {
    int attackOutcome, damageDealt;    

    srand(time(NULL));
    
    attackOutcome = (rand() % 10) + 1;
    switch (attackOutcome) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
            damageDealt = (*hero).attackPoints - 
                (*currentEnemy).defencePoints;
            if (damageDealt < 0) {
                damageDealt = 0;
            }
    
            if (turn == HERO) {
                (*currentEnemy).currentHitPoints -= damageDealt;
                if ((*currentEnemy).currentHitPoints < 0) {
                    (*currentEnemy).currentHitPoints = 0;
                }

                printf("%s attacks %s, dealing %d damage!\n",
                    (*hero).name, (*currentEnemy).name, damageDealt);
            }
            else {
                (*hero).currentHitPoints -= damageDealt;
                if ((*hero).currentHitPoints < 0) {
                    (*hero).currentHitPoints = 0;
                }
                printf("    %s attacks %s, dealing %d damage!\n",
                    (*currentEnemy).name, (*hero).name, damageDealt);
            }
            break;
        case 8:
        case 9:
            damageDealt = ((*hero).attackPoints << 2) - 
                (*currentEnemy).defencePoints;
             
            if (turn == HERO) {
                (*currentEnemy).currentHitPoints -= damageDealt;
                printf("Critical Hit! %s attacks %s, dealing %d damage!\n",
                    (*hero).name, (*currentEnemy).name, damageDealt);
            }
            else {
                (*hero).currentHitPoints -= damageDealt;
                printf("    Critical Hit! %s attacks %s, dealing %d "
                    "damage!\n", (*currentEnemy).name, (*hero).name, 
                    damageDealt);
            }
            break;
        case 10:
            if (turn == HERO) {
                printf("Miss! %s fails to hit %s\n", (*hero).name,
                    (*currentEnemy).name);
            }
            else {
                printf("    Miss! %s fails to hit %s\n", (*currentEnemy).name,
                    (*hero).name);
            }
            break;
    }
    printf("\n");

    if (((*hero).currentHitPoints / (*hero).maxHitPoints) < 0.3 &&
        (*hero).medicineCount > 0) {
        (*hero).medicineCount -= 1;

        (*hero).currentHitPoints = (*hero).maxHitPoints;
 
        printf("    %s is badly injured, uses 1 medicine. Their HP is "
            "restored!\n", (*hero).name);
    }

    if (turn == HERO) {
        turn = ENEMY;
    }
    else {
        turn = HERO;
    }

    return turn;
}

void getReward(Hero *hero) {
    int rewardOutcome;
    
    srand(time(NULL));
    
    rewardOutcome = (rand() % 10) + 1;
    switch (rewardOutcome) {
        case 1:
        case 2:
        case 3:
            (*hero).attackPoints += (*hero).attackPoints / 2;
 
            printf("%s received a better sword. Their ATK is now %d\n", 
                    (*hero).name, (*hero).attackPoints);
            break;
        case 4:
        case 5:
        case 6:
            (*hero).defencePoints += (*hero).defencePoints / 2;
            
            printf("%s received better armour. Their DEF is now %d\n", 
                    (*hero).name, (*hero).defencePoints);
            break;
        case 7:
        case 8:
        case 9:
        case 10:
            (*hero).medicineCount++;

            printf("%s received 1 medicine. There medicine count is now %d\n",
                (*hero).name, (*hero).medicineCount);
            break;
    }
}

