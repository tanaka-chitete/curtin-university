/*
 * NAME: bounds
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1000
 * PURPOSE: Bound check values of various types
 * CREATION: 20/08/2020
 * LAST MODIFICATION: 25/08/2020
 */

#include <stdio.h>

#include "bounds.h"
#include "macros.h"
#include "powers.h"
#include "scan.h"

int main(void)
{
    int exp;

    exp = scan_int();
       
    if (!BETWEEN(1, exp, 31)) /* Ensure EXP doesn't overflow a long */
    {
        printf("Exponent should be between 1 and 31 (inclusive)\n\n");
    }
    else
    {
        /* Call power function EXP number of times */
        long two_to_power_of_exp = next_power_auto(exp);
        
        if (two_to_power_of_exp < 0)
        {
            two_to_power_of_exp *= -1;
        }

        printf("2 to the power of %d is: %ld\n", exp, two_to_power_of_exp); 
    }

    return 0;
}
