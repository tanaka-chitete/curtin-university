/******************************************************************************
 * NAME: question3.c                                                          *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1000                                                             *
 * PURPOSE: Calculate the factorial of a given number                         *
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

#include <stdio.h>

long readLong(void);
long fact(long n);

int main(void)
{
    int sel;
    do
    {
        printf("Main Menu\n\n" 

               "1. Factorial\n" 
               "0. End\n\n");
        sel = readLong();
        printf("\n");        

        /* 'Factorial' option */ 
        if (sel == 1)
        {
            long n = readLong();
            if (n >= 0)
            { 
                printf("%ld!: %ld\n", n, fact(n));
            }
            else
            {
                printf("Input must be greater than or equal to 0\n");
            }
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
 * NAME: readLong                                                             *
 * IMPORT(S): none                                                            *
 * EXPORT(S): none                                                            *
 * PURPOSE: Read long input from the keyboard                                 *
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

/* Obtained from Worksheet 1: Basics, UNIX & C Programming (COMP1000) */
/* Accessed 07/08/2020 */
long readLong(void)
{
    long input;
    printf("Enter an integer: ");
    scanf("%ld", &input);
    return input;
}
/* End of code obtained from above source */

/******************************************************************************
 * NAME: fact                                                                 *
 * IMPORT(S): n (long)                                                        *
 * EXPORT(S): long                                                            *
 * PURPOSE: Calculate the factorial of N                                      *
 * CREATION: 07/08/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

long fact(long n)
{
    if (n == 0 || n == 1)
    {
        return n;
    }
    else
    {   
        return n * fact(n - 1);
    }
}
