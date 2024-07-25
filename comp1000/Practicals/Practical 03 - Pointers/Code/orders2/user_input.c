#include <stdio.h>

#include "user_input.h"

/* Obtained from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
int read_int(void)
{
    int input;
    printf("Enter an integer: ");
    scanf("%d", &input);
    return input;
}
/* End of code obtained from above source */

void read_ints(int* pointer1, int* pointer2, int* pointer3, char* pointer4)
{
    printf("Enter three ints, one after the other, then either "
           "\'A\' or \'D\': ");
    scanf("%d %d %d %c", pointer1, pointer2, pointer3, pointer4);
}
