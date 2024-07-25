/******************************************************************************
 * NAME: Factorial                                                            *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the factorial of a given integer              *
 * CREATION: 12/08/2020                                                       *
 * LAST MODIFICATION: 15/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class Factorial
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
                prompt = "Number to compute factorial for: ";
                int n = UserInterface.userInput(Integer.MIN_VALUE,
                                                Integer.MAX_VALUE, prompt);
                long factOfN = fact(n); 
                System.out.println(n + " factorial is: " + factOfN + "\n"); 
            }
        }
        while (menuSel != QUIT);    
    }

    /**************************************************************************
     * NAME: fact                                                             *
     * IMPORT: n (int)                                                        *
     * EXPORT: factPrivate(n) (long)                                          *
     * PURPOSE: Act as wrapper function for factPrivate                       * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 12/08/2020                                          *
     **************************************************************************/

    public static long fact(int n)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException("Input cannot be less than 0");
        }

        return factPrivate(n);    
    }
    
    /**************************************************************************
     * NAME: factPrivate                                                      *
     * IMPORT: n (int)                                                        *
     * EXPORT: value (long)                                                   *
     * PURPOSE: Compute the factorial of N                                    *
     * CREATION: 12/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static long factPrivate(int n)
    {
        long value;

        if (n == 0 || n == 1)
        {
            value = 1;
        }
        else
        {
            value = n * fact(n - 1);
        }
        return value;
    }
}
