#ifndef HERO_H
#define HERO_H

/* STRUCTURE */

typedef struct Hero {
    int currentHitPoints;
    int maxHitPoints;
    int attackPoints;
    int defencePoints;
    int medicineCount;
    char name[50];
} Hero;

/* CONSTRUCTOR */

Hero *makeHero(int inCurrentHitPoints, int inMaxHitPoints, int inAttackPoints,
    int inDefencePoints, int inMedicineCount, const char *inName);

#endif
