/* Parts of this file comprise externally-adapted code */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "fileio.h"

#include "enemy.h"
#include "hero.h"
#include "stack.h"

extern char *strndup(const char *s, size_t n);

Hero *readHeroFile(char *filename) {
    FILE *file = fopen(filename, "r");
    Hero *newHero;

    const char *inName;
    char buffer[128]; 
    char *bufferCopy1, *bufferCopy2, *bufferCopy3, *bufferCopy4, *bufferCopy5,
        *bufferCopy6;
    int inCurrentHitPoints, inMaxHitPoints, inAttackPoints, inDefencePoints,
        inMedicineCount; 

    if (file == NULL) {
	    newHero = NULL;
    }
    else {
	    /* Adapted from sehe */
	    /* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
    	/* Accessed 14/11/2020 */
	    fgets(buffer, 128, file);

        bufferCopy1 = strndup(buffer, 128 * sizeof(char));
        bufferCopy2 = strndup(buffer, 128 * sizeof(char));
        bufferCopy3 = strndup(buffer, 128 * sizeof(char));
        bufferCopy4 = strndup(buffer, 128 * sizeof(char));
        bufferCopy5 = strndup(buffer, 128 * sizeof(char));
        bufferCopy6 = strndup(buffer, 128 * sizeof(char));
     
	    inCurrentHitPoints = atoi(getField(bufferCopy1, 1));
	    inMaxHitPoints = atoi(getField(bufferCopy2, 1));
	    inAttackPoints = atoi(getField(bufferCopy3, 2));
	    inDefencePoints = atoi(getField(bufferCopy4, 3));
        inMedicineCount = atoi(getField(bufferCopy5, 4));
        inName = getField(bufferCopy6, 5);

        newHero = makeHero(inCurrentHitPoints, inMaxHitPoints, inAttackPoints, 
            inDefencePoints, inMedicineCount, inName);
        
        free(bufferCopy1);
        free(bufferCopy2);
        free(bufferCopy3);
        free(bufferCopy4);
        free(bufferCopy5);
        free(bufferCopy6);
        
        fclose(file);
	    /* End of code adapted from sehe */
	}

    return newHero;
}

LinkedList *readEnemiesFile(char *filename) {
    FILE *file = fopen(filename, "r");
    LinkedList *stack;
    Enemy *newEnemy;

    const char *inName;
    char buffer[128]; 
    char *bufferCopy1, *bufferCopy2, *bufferCopy3, *bufferCopy4;
    int inCurrentHitPoints, inAttackPoints, inDefencePoints;

    if (file == NULL) {
	    stack = NULL;
    }
    else {
	    stack = makeStack();
	    /* Adapted from sehe */
	    /* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
    	/* Accessed 14/11/2020 */
	    while (fgets(buffer, 128, file)) {
            bufferCopy1 = strndup(buffer, 128 * sizeof(char));
            bufferCopy2 = strndup(buffer, 128 * sizeof(char));
            bufferCopy3 = strndup(buffer, 128 * sizeof(char));
            bufferCopy4 = strndup(buffer, 128 * sizeof(char));
     
	        inCurrentHitPoints = atoi(getField(bufferCopy1, 1));
	        inAttackPoints = atoi(getField(bufferCopy2, 2));
	        inDefencePoints = atoi(getField(bufferCopy3, 3));
            inName = getField(bufferCopy4, 4);

            newEnemy = makeEnemy(inCurrentHitPoints, inAttackPoints,
                inDefencePoints, inName);

	        push(stack, newEnemy);
        
            free(bufferCopy1);
            free(bufferCopy2);
            free(bufferCopy3);
            free(bufferCopy4);
        }
        fclose(file);
	    /* End of code adapted from sehe */
	}

    return stack;
}

/* Adapted from sehe */
/* https://stackoverflow.com/questions/12911299/read-csv-file-in-c */
/* Accessed 14/11/2020 */
const char *getField(char *line, int iField) {
    const char *tok;

    for (tok = strtok(line, ","); tok && *tok; tok = strtok(NULL, ",\n")) {
        if (!--iField) {
	        return tok;
        }
    }

    return NULL;
}
/* End of code adapted from sehe */

