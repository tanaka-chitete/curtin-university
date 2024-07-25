#include <stdio.h>

#include "scan.h"

/* Obtained from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
int scan_int(void)
{
    int input;
    printf("Enter an integer: ");
    scanf("%d", &input);
    return input;
}
/* End of code obtained from above source */

/* Adapted from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
char scan_char(void)
{
    char input;
    printf("Enter a character: ");
    scanf("%c", &input);
    return input;
}
/* End of code obtained from above source */

/* Adapted from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
double scan_double(void)
{
    double input;
    printf("Enter a real: ");
    scanf("%lf", &input);
    return input;
}
/* End of code obtained from above source */
