/******************************************************************************
 * NAME: Fibonacci                                                            *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the fibonacci number at a given integer index * 
 * CREATION: 12/08/2020                                                       *
 * LAST MODIFICATION: 15/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class Fibonacci
{
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;

    public static void main(String[] args)
    {
        int menuSel;
        do
        {
            System.out.println("Main Menu\n\n" + 
        
                               "1. Compute\n" + 
                               "0. Quit\n");
            String prompt = "Selection: ";
            menuSel = UserInterface.userInput(QUIT, OPTION_1, prompt);
            System.out.println();

            // MENUSEL specifies 'Compute'
            if (menuSel == OPTION_1)
            {
                prompt = "Index of Fibonacci number to compute (0-based): ";
                int n = UserInterface.userInput(Integer.MIN_VALUE, 
                                                Integer.MAX_VALUE, prompt);
                long nthFibNum = fib(n);
                System.out.println("Fibonacci number " + n + " is: " + 
                                   nthFibNum + "\n");
            }
        }
        while (menuSel != QUIT);
    }

    /**************************************************************************
     * NAME: fib                                                              *
     * IMPORT: n (int)                                                        *
     * EXPORT: fibPrivate(n) (long)                                           *
     * PURPOSE: Act as a wrapper function for fibPrivate                      * 
     * CREATION: 12/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    public static long fib(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException("Input cannot be negative");
        }

        return fibPrivate(n);
    }

    /**************************************************************************
     * NAME: fib                                                              *
     * IMPORT: n (int)                                                        *
     * EXPORT: value (long)                                                   *
     * PURPOSE: Compute the Nth Fibonacci number                              * 
     * CREATION: 12/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static long fibPrivate(int n)
    {
        long value;
        
        if (n == 0)
        {
            value = 0;
        }
        else if (n == 0 || n == 1)
        {
            value = 1;
        }
        else
        {
            value = fib(n - 2) + fib(n - 1);
        }
        return value;
    }
}
