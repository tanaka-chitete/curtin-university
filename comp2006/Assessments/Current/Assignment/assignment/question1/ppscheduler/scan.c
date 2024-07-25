/* Obtained from Tanaka Chitete */
/* Final Assessment */
/* Unix & C Programming (COMP1000) */
/* Accessed 03/05/2021 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "scan.h"

extern char *strdup(const char *s);

const char *scanString() {
    char *temp;
    const char *input;
    
    temp = calloc(16, sizeof(char));

    printf("PP simulation: ");
    scanf("%s", temp);

    input = strdup(temp);
 
    free(temp);
   
    return input;
}

