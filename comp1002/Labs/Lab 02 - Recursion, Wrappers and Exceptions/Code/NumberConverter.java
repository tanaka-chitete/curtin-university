/******************************************************************************
 * NAME: NumberConverter                                                      *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the equivalent number in a given base of a    * 
 *          given decimal number                                              *
 * CREATION: 15/08/2020                                                       *
 * LAST MODIFICATION: 15/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class NumberConverter 
{
    public static final int QUIT = 0;
    public static final int OPTION_1 = 1;

    public static final char[] digits = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };

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
                prompt = "Decimal number to convert: ";
                int n = UserInterface.userInput(Integer.MIN_VALUE, 
                                                Integer.MAX_VALUE, prompt);
 
                prompt = "Base to convert number to: ";
                int b = UserInterface.userInput(Integer.MIN_VALUE, 
                                                Integer.MAX_VALUE, prompt);

                String nInBaseB = numberConverter(n, b); 
                System.out.println("Base " + b + " equivalent of " + n + 
                                   " is: " + nInBaseB + "\n");
            }
        }
        while (menuSel != QUIT);
    }

    /**************************************************************************
     * NAME: numberConverter                                                  *
     * IMPORT: n (int), b (int)                                               *
     * EXPORT: value (String)                                                 *
     * PURPOSE: Act as a wrapper function for numberConverter                 * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    public static String numberConverter(int n, int b)
    {
        if (b < 2 || b > 16)
        {
            throw new IllegalArgumentException("Base cannot cannot be less " +
                                               "than 2 or greater than 16");
        }

        // Ensure N is positive so NUMBERCONVERTER works as intended 
        boolean negative;         
        if (n < 0)
        {
            n = Math.abs(n);
            negative = true;
        }
        else
        {
            negative = false;
        }
        
        String value;
        if (negative)
        {
            value = "-" + numberConverterPrivate(n, b);
        }
        else
        {
            value = numberConverterPrivate(n, b);
        }

        return value;
    }

    /**************************************************************************
     * NAME: numberConverterPrivate                                           *
     * IMPORT: n (int), b (int)                                               *
     * EXPORT: value (String)                                                 *
     * PURPOSE: Compute the equivalent value of N in base B                   * 
     * CREATION: 15/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static String numberConverterPrivate(int n, int b)
    {
        String value;
        if (n == 0)
        {
            value = "";
        }
        else
        {
            int i = n % b;
            value = numberConverter(n / b, b) + digits[i]; 
        }
        return value;
    }
}
