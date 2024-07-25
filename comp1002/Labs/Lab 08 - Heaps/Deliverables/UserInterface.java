/******************************************************************************
 * NAME: UserInterface                                                        *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Implement various input-related submodules                        *
 * CREATION: 07/05/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              *
 ******************************************************************************/

import java.util.*;

// Obtained from Chitete, T
// Accessed 10/08/2020

public class UserInterface
{
    // CLASS CONSTANTS

    public static final double TOL = 0.01;

    // PUBLIC SUBMODULES

    /**************************************************************************
     * NAME: userInput                                                        *
     * IMPORT: min (int), max (int), prompt (String)                          *
     * EXPORT: input (int)                                                    *
     * PURPOSE: Validate int input as being within min and max bounds         *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static int userInput(int min, int max, String prompt)
    {
        Scanner sc = new Scanner(System.in);        

        int trigger, input;
        String error, out;

        // Calculates number of options available for user input
        trigger = max - min;
        // Default error message (Number of available options exceeds two)
        error = "Input must be between " + min + " and " + max + " inclusive";
        // Executes if number of availabel options is two
        if (trigger == 1)
        {
            error = "Input must be either " + min + " or " + max;
        }

        input = min - 1;
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            // Attempts to retreive int input
            try
            {
                System.out.print(out);
                input = sc.nextInt();
                // Executes if input received is out of bounds
                if (input < min || input > max)
                {
                    printError(error);
                }
            }
            // Executes if input received is not an int
            catch(InputMismatchException e)
            {
                printError(error);
                sc.nextLine(); 
            } 
        }
        // Ends execution of input reception and validation if input is within
        // min and max, continues if otherwise
        while (input < min || input > max);
        return input;       
    }

    /**************************************************************************
     * NAME: userInput                                                        *
     * IMPORT: prompt (String)                                                *
     * EXPORT: input (String)                                                 *
     * PURPOSE: Validate input as being a string                              *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 01/09/2020                                          *
     **************************************************************************/

    public static String userInput(String prompt)
    {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.print(prompt);
        input = sc.nextLine();

        return input;
    }

    /**************************************************************************
     * NAME: printError                                                       *
     * IMPORT: error (String)                                                 *
     * EXPORT: none                                                           *
     * PURPOSE: Print error message                                           *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static void printError(String error)
    {
        System.out.println(error);
    }
}
