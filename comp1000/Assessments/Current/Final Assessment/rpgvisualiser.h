#ifndef RPGVISUALISER_H
#define RPGVISUALISER_H

#include "enemy.h"
#include "hero.h"
#include "stack.h"

void introduceHero(Hero *hero);
void introduceEnemies(LinkedList *stack);
void playGame(Hero *hero, LinkedList *stack, float sleepTime);
int playTurn(Hero *hero, Enemy *currentEnemy, int turn);
void getReward(Hero *hero);

#endif

