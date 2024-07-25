/*
 * NAME: powers
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Compute successive powers of 2 using static local variables
 * CREATION: 19/08/2020
 * LAST MODIFICATION: 19/08/2020
 */

#include <stdio.h>

#include "powers.h"

/*
 * NAME: next_power
 * IMPORT(S): none
 * EXPORT(S): curr (long) 
 * PURPOSE: Compute the next power of 2 using static local variables
 * CREATION: 19/08/2020
 * LAST MODIFICATION: 27/08/2020
 */

long next_power(void)
{
    static long start = 2; /* Initialised when function is first called */
    long curr = start;
    start *= 2;
    return curr; 
}

/*
 * NAME: next_power_auto
 * IMPORT(S): exp (int)
 * EXPORT(S): two_to_the_power_of_exp (long) 
 * PURPOSE: Compute 2 to the power of EXP by repeatedly calling NEXT_POWER
 * CREATION: 27/08/2020
 * LAST MODIFICATION: 27/08/2020
 */

long next_power_auto(int exp)
{
    int two_to_power_of_exp;
    int i;

    i = 0;
    for (i = 0; i < exp; i++)
    {
        two_to_power_of_exp = next_power();      
    }

    return two_to_power_of_exp;
}
