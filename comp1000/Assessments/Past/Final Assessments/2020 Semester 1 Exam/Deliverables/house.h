/* STRUCTURE */

typedef struct House {
    int bedroomCount;
    int bathroomCount;
    int price;
    const char *address;
} House;

/* CONSTRUCTORS */

House *makeHouse(int inBedroomCount, int inBathroomCount, int inPrice, 
    const char *inAddress);

