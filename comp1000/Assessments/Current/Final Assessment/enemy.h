#ifndef ENEMY_H
#define ENEMY_H

/* STRUCTURE */

typedef struct Enemy {
    int currentHitPoints;
    int attackPoints;
    int defencePoints;
    char name[50];
} Enemy;

/* CONSTRUCTOR */

Enemy *makeEnemy(int inCurrentHitPoints, int inAttackPoints, 
    int inDefencePoints, const char *inName);

#endif

