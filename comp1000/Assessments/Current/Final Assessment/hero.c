#include <stdlib.h>
#include <string.h>

#include "hero.h"

/* CONSTRUCTOR */

Hero *makeHero(int inCurrentHitPoints, int inMaxHitPoints, int inAttackPoints,
int inDefencePoints, int inMedicineCount, const char *inName) {
    Hero *newHero;

    int i;

    newHero = (Hero*) malloc(sizeof(Hero));

    (*newHero).currentHitPoints = inCurrentHitPoints;
    (*newHero).maxHitPoints = inMaxHitPoints;
    (*newHero).attackPoints = inAttackPoints;
    (*newHero).defencePoints = inDefencePoints;
    (*newHero).medicineCount = inMedicineCount;
    for (i = 0; i < strlen(inName); i++) {
        (*newHero).name[i] = inName[i];
    }

    return newHero;
}

