#include <stdlib.h>
#include <string.h>

#include "house.h"

extern char *strdup(const char *s);

/* CONSTRUCTORS */

House *makeHouse(int inBedroomCount, int inBathroomCount, int inPrice, 
const char *inAddress) {
    House *newHouse;

    newHouse = (House*) malloc(sizeof(House));

    (*newHouse).bedroomCount = inBedroomCount;
    (*newHouse).bathroomCount = inBathroomCount;
    (*newHouse).price = inPrice;
    (*newHouse).address = strdup(inAddress);
    
    return newHouse;
}

