/******************************************************************************
 * NAME: GreatestCommonDenominator                                            *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the greatest common divisor of two given      *
 *          integers                                                          * 
 * CREATION: 15/08/2020                                                       *
 * LAST MODIFICATION: 15/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class GreatestCommonDenominator
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
                prompt = "First number: ";
                int x = UserInterface.userInput(Integer.MIN_VALUE, 
                                                Integer.MAX_VALUE, prompt);
                
                prompt = "Second number: ";
                int y = UserInterface.userInput(Integer.MIN_VALUE, 
                                                Integer.MAX_VALUE, prompt);

                int gcdOfXandY = gcd(x, y); 

                System.out.println("GCD of " + x + " and " + y + " is: " +
                                   gcdOfXandY + "\n");
            }
        }
        while (menuSel != QUIT);
    }
    
    /**************************************************************************
     * NAME: gcd                                                              *
     * IMPORT: x (int), y (int)                                               *
     * EXPORT: gcdPrivate(x, y, Math.min(x, y) - 1) (int)                     *
     * PURPOSE: Act as wrapper function for gcdPrivate                        * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    public static int gcd(int x, int y)
    {
        if (x <= 0 || y <= 0)
        {
            throw new IllegalArgumentException("Inputs must be greater than 0");
        }        

        return gcdPrivate(x, y, Math.min(x, y));
    }

    /**************************************************************************
     * NAME: gcdPrivate                                                       *
     * IMPORT: x (int), y (int), i (int)                                      *
     * EXPORT: value (int)                                                    *
     * PURPOSE: Compute the greatest common divisor of X and Y                * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static int gcdPrivate(int x, int y, int i)
    {
        int value;
        if (x == 1 || y == 1)
        {
            value = 1;
        }    
        else if (x % i == 0 && y % i == 0)
        {
            value = i;
        }
        else
        {
            value = gcdPrivate(x, y, i - 1);
        }
        return value;
    }
}
