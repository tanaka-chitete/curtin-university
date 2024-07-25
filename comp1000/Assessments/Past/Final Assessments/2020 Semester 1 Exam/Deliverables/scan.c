#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "scan.h"

extern char *strdup(const char *s);

/* Obtained from COMP1000: Worksheet 1: Basics, UNIX & C Programming */
/* Accessed 07/08/2020 */
int scanInt(void) {
    int input;

    printf("Enter an integer: ");
    scanf("%d", &input);

    return input;
}
/* End of code obtained from above source */

/* Adapted from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
double scanDouble(void) {
    double input;

    printf("Enter a real: ");
    scanf("%lf", &input);

    return input;
}
/* End of code adapted from above source */

/* Adapted from COMP1000: Worksheet 1: Basics, UNIX & C Programming */
/* Accessed 07/08/2020 */
char scanChar(void) {
    char input;

    printf("Enter a character: ");
    scanf("%c", &input);

    return input;
}
/* End of code adapted from above source */

/* Adapted from COMP1000: Worksheet 1: Basics, UNIX & C Programming */ 
/* Accessed 07/08/2020 */
const char *scanString(void) {
    char *temp;
    const char *input;
    
    temp = calloc(16, sizeof(char));

    printf("Enter a string: ");
    scanf("%s", temp);

    input = strdup(temp);
 
    free(temp);
   
    return input;
}
/* End of code adapted from above source */ 

