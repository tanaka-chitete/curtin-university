/******************************************************************************
 * NAME: question2.c                                                          * 
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1000                                                             *
 * PURPOSE: Implement a divisibility-checking algorithm using numbers x and y * 
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

#include <stdio.h>

int readInt(void);
void is_divisible(int x, int y);

int main(void)
{
    int sel;
    do
    {
        printf("Main Menu\n\n" 

               "1. Divisibility\n" 
               "0. End\n\n");
        sel = readInt();
        printf("\n");        

        /* 'Check Divisibility' option */ 
        if (sel == 1)
        {
            int x = readInt();
            int y = readInt();
            is_divisible(x, y);
        }
        /* 'End' option */
        else if (sel == 0)
        {
            printf("Ended\n");
        }
        /* Neither option */
        else
        {
            printf("Enter either 1 or 0\n");
        }
        printf("\n");
    }
    while (sel != 0);

    return 0;
}

/******************************************************************************
 * NAME: readInt                                                              *
 * IMPORT(S): none                                                            * 
 * EXPORT(S): none                                                            *  
 * PURPOSE: Read int input from the keyboard                                  *     
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              * 
 ******************************************************************************/

/* Obtained from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
int readInt(void)
{
    int input;
    printf("Enter an integer: ");
    scanf("%d", &input);
    return input;
}
/* End of code obtained from above source */

/******************************************************************************
 * NAME: is_divisible                                                         * 
 * IMPORT(S): x (int), y (int)                                                * 
 * EXPORT(S): none                                                            *  
 * PURPOSE: Check whether X is divisible by Y                                 *     
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

void is_divisible(int x, int y)
{
    if (y == 0)
    {
        printf("denominator cannot be 0\n");
    }
    else if (x % y == 0)
    {
        printf("divisible\n");
    }
    else
    {
        printf("not divisble\n");
    }
}
