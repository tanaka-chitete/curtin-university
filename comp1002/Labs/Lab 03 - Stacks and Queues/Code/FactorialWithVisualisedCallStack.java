/******************************************************************************
 * NAME: FactorialWithVisualisedCallStack                                     *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the factorial of a given integer              *
 * CREATION: 12/08/2020                                                       *
 * LAST MODIFICATION: 15/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class FactorialWithVisualisedCallStack
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
                DSAStack stack = new DSAStack();
                stack.push("SUBMODULE: main, PARAMETER(S): void");

                System.out.println("Computing 3!\n");
                long factOf3 = fact(3, stack); 
                System.out.println(3 + " factorial is: " + factOf3 + "\n");
                stack = new DSAStack();

                stack.push("SUBMODULE: main, PARAMETER(S): void");

                System.out.println("Computing (3!)!\n");
                long factOfFactOf3 = fact((int) fact(3, stack), stack); 
                System.out.println("Factorial of " + 3 + " factorial is: " + 
                                   factOfFactOf3 + "\n");
                stack = new DSAStack();
            }
        }
        while (menuSel != QUIT);    
    }

    /**************************************************************************
     * NAME: fact                                                             *
     * IMPORT: n (int)                                                        *
     * EXPORT: factPrivate(n) (long), stack (DSAStack)                        *
     * PURPOSE: Act as wrapper function for factPrivate                       * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 12/08/2020                                          *
     **************************************************************************/

    public static long fact(int n, DSAStack stack)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException("Input cannot be less than 0");
        }

        return factPrivate(n, stack);    
    }
    
    /**************************************************************************
     * NAME: factPrivate                                                      *
     * IMPORT: n (int), stack (DSAStack)                                      *
     * EXPORT: value (long)                                                   *
     * PURPOSE: Compute the factorial of N                                    *
     * CREATION: 12/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static long factPrivate(int n, DSAStack stack)
    {
        stack.push("SUBMODULE: factPrivate, PARAMETER(S): " + n + ", stack");
        System.out.println(stack.toString());
        System.out.println();

        long value;

        if (n == 0 || n == 1)
        {
            value = 1;
        }
        else
        {
            value = n * fact(n - 1, stack);
        }

        stack.pop();
        System.out.println(stack.toString());
        System.out.println();

        return value;
    }
}
