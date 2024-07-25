#include <stdlib.h>
#include <string.h>

#include "enemy.h"

/* CONSTRUCTOR */

Enemy *makeEnemy(int inCurrentHitPoints, int inAttackPoints,
int inDefencePoints, const char *inName) {
    Enemy *newEnemy;
    int i;

    newEnemy = (Enemy*) malloc(sizeof(Enemy));

    (*newEnemy).currentHitPoints = inCurrentHitPoints;
    (*newEnemy).attackPoints = inAttackPoints;
    (*newEnemy).defencePoints = inDefencePoints;
    for (i = 0; i < strlen(inName); i++) {
        (*newEnemy).name[i] = inName[i];
    }
    
    return newEnemy;
}

