/******************************************************************************
 * NAME: UserInterface                                                        *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1007                                                             *
 * PURPOSE: Implement various input-related submodules                        *
 * CREATION: 07/05/2020                                                       *
 * LAST MODIFICATION: 08/05/2020                                              *
 ******************************************************************************/

/* This file comprises externally-obtained code. */

// Obtained from Chitete, T.,
// (Accessed 23/05/2020)
import java.util.*;

public class UserInterface
{
    // CLASS CONSTANTS

    public static final double TOL = 0.00_00_01;
    public static final int STR_MIN_LEN = 2;

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
     * IMPORT: min (long), max (long), prompt (String)                        *
     * EXPORT: input (long)                                                   *
     * PURPOSE: Validate long input as being within min and max bounds        *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static long userInput(long min, long max, String prompt)
    {
        Scanner sc = new Scanner(System.in);        

        long trigger, input;
        String error, out;

        // Calculates number of options available for user input
        trigger = max - min;
        // Default error message (Number of available options exceeds two)
        error = "Input must be between " + min + " and " + max + " inclusive";
        // Executes if number of availabel options is two
        if (trigger == 1l)
        {
            error = "Input must be either " + min + " or " + max;
        }

        input = min - 1l;
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            // Attempts to retreive long input
            try
            {
                System.out.print(out);
                input = sc.nextLong();
                // Executes if input received is out of bounds
                if (input < min || input > max)
                {
                    printError(error);
                }
            }
            // Executes if input received is not a long
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
     * IMPORT: min (float), max (float), prompt (String)                      *
     * EXPORT: input (float)                                                  *
     * PURPOSE: Validate float input as being within min and max bounds       *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static float userInput(float min, float max, String prompt)
    {
        Scanner sc = new Scanner(System.in);        

        float input;
        String error, out;

        error = "Input must be between " + min + " and " + max + " inclusive";
        input = min - 1.0f;
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            // Attempts to retreive float input
            try
            {
                System.out.print(out);
                input = sc.nextFloat();
                // Executes if input received is out of bounds
                if (input < min || input > max)
                {
                    printError(error);
                }
            }
            // Executes if input received is not a float
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
     * IMPORT: min (double), max (double), prompt (String)                    *
     * EXPORT: input (double)                                                 *
     * PURPOSE: Validate double input as being within min and max bounds      *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static double userInput(double min, double max, String prompt)
    {
        Scanner sc = new Scanner(System.in);        

        double input;
        String error, out;

        error = "Input must be between " + min + " and " + max + " inclusive";
        input = min - 1.0;
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            // Attempts to retreive double input
            try
            {
                System.out.print(out);
                input = sc.nextDouble();
                // Executes if input received is out of bounds
                if (input < min || input > max)
                {
                    printError(error);
                }
            }
            // Executes if input received is not a double
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
     * IMPORT: min (char), max (char), prompt (String)                        *
     * EXPORT: input (char)                                                   *
     * PURPOSE: Validate char input as being within min and max bounds        *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 07/05/2020                                          *
     **************************************************************************/

    public static char userInput(char min, char max, String prompt)
    {
        Scanner sc = new Scanner(System.in);        

        char input;
        int trigger;
        String error, out;

        // Calculates number of options available for user input
        trigger = (int)max - (int)min;
        // Default error message (Number of available options exceeds two)
        error = "Input must be between '" + min + "' and '" + max + 
                "' inclusive";
        // Executes if number of availabel options is two
        if (trigger == 1)
        {
            error = "Input must be either '" + min + "' or '" + max + "'";
        }

        input = (char)(min - (char)1);
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            // Attempts to retreive char input
            try
            {
                System.out.print(out);
                input = sc.next().charAt(0);
                // Executes if input received is out of bounds
                if (input < min || input > max)
                {
                    printError(error);
                }
            }
            // Executes if input received is not a character
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
     * LAST MODIFICATION: 27/05/2020                                          *
     **************************************************************************/

    public static String userInput(String prompt)
    {
        Scanner sc = new Scanner(System.in);

        boolean validString;
        String error, out, input;

        error = "Input must be at least two characters in length";
        out = prompt;
        // Begins execution of input reception and validation
        do
        {
            System.out.print(out);
            input = sc.nextLine();
            // Executes if input received contains a single character 
            validString = validateString(input);
            if (!validString)
            {
                printError(error);
            }
        }
        while (!validString);
        return input;
    }
    
    /**************************************************************************
     * NAME: printMainMenu                                                    *
     * IMPORT: none                                                           *
     * EXPORT: none                                                           *
     * PURPOSE: Print 'Main Menu' menu                                        *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static void printMainMenu()
    {
        System.out.println("Main Menu\n\n" +

                           "1. Import Image\n" +
                           "2. Import Kernel\n" +
                           "3. Convolute Image\n" +
                           "4. Smoothen Image\n" +
                           "5. Smoothen Convoluted Image\n" + 
                           "6. Export Image\n" +
                           "7. Export Convoluted Image\n" + 
                           "0. End Program\n");
    }

    /**************************************************************************
     * NAME: printImportImageSourceMenu                                       *
     * IMPORT: none                                                           *
     * EXPORT: none                                                           *
     * PURPOSE: Print 'Import Image: Source' menu                             *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static void printImportImageSourceMenu()
    {
        System.out.println("Import Image: Source\n\n" +
        
                           "1. File\n" +
                           "2. User Input\n");
    }

    /**************************************************************************
     * NAME: printImportKernelSourceMenu                                      *
     * IMPORT: none                                                           *
     * EXPORT: none                                                           *
     * PURPOSE: Print 'Import Kernel: Source' menu                            *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static void printImportKernelSourceMenu()
    {
        System.out.println("Import Kernel: Source\n\n" +
        
                           "1. File\n" +
                           "2. User Input\n");
    }

    /**************************************************************************
     * NAME: printExportImageOrConvoluteFormatMenu                            *
     * IMPORT: none                                                           *
     * EXPORT: none                                                           *
     * PURPOSE: Print 'Export Image: Format' menu                             *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static void printExportImageOrConvoluteFormatMenu()
    {
        System.out.println("Export Image: Format\n\n" +
        
                           "1. CSV\n" +
                           "2. PNG\n");
    }
    
    /**************************************************************************
     * NAME: printMessage                                                     *
     * IMPORT: message (String)                                               *
     * EXPORT: none                                                           *
     * PURPOSE: Print message                                                 *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 27/05/2020                                          *
     **************************************************************************/

    public static void printMessage(String message)
    {
        System.out.println(message);
    }

    /**************************************************************************
     * NAME: printError                                                       *
     * IMPORT: error (String)                                                 *
     * EXPORT: none                                                           *
     * PURPOSE: Print error                                                   *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 27/05/2020                                          *
     **************************************************************************/

    public static void printError(String error)
    {
        System.out.println(error);
    }

    // PRIVATE SUBMODULES

    /**************************************************************************
     * NAME: validateString                                                   *
     * IMPORT: input (String)                                                 *
     * EXPORT: validString (boolean)                                          *
     * PURPOSE: Validate input as having two or more characters               *
     * CREATION: 07/05/2020                                                   *
     * LAST MODIFICATION: 11/05/2020                                          *
     **************************************************************************/

    private static boolean validateString(String input)
    {
        boolean validString;

        validString = false;
        if (input.length() >= STR_MIN_LEN)
        {
            validString = true;
        }
        return validString;
    }
}
// End of code obtained from Chitete, T. 
