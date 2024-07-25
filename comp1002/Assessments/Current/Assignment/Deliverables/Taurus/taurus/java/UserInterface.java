// Obtained from Chitete, T
// Accessed 26/08/2020

/*
 * NAME: UserInterface
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Get various types of user input
 * CREATION: 07/05/2020
 * LAST MODIFICATION: 30/09/2020
 */

import java.util.*;

public class UserInterface {
    // PUBLIC SUBMODULES

    /*
     * NAME: userInput
     * IMPORT: min (int), max (int), prompt (String)
     * EXPORT: input (int)
     * PURPOSE: Get int user input between min and max inclusive
     * CREATION: 07/05/2020
     * LAST MODIFICATION: 30/09/2020
     */

    public static int userInput(int min, int max, String prompt) {
        Scanner sc = new Scanner(System.in);        

        int trigger, input;
        String error, out;

        // Calculates number of options available for user input
        trigger = max - min;
        // Default error message (Number of available options exceeds two)
        error = "Selection must be between " + min + " and " + max + " inclusive";
        // Executes if number of available options is two
        if (trigger == 1) {
            error = "Selection must be either " + min + " or " + max;
        }

        input = min - 1;
        out = prompt;
        // Begins execution of input reception and validation
        do {
            try {
                // Attempts to retreive int input
                System.out.print(out);
                input = sc.nextInt();
                // Executes if input received is out of bounds
                if (input < min || input > max) {
                    _printError(error);
                }
            }
            // Executes if input received is not an int
            catch(InputMismatchException e) {
                _printError(error);
                sc.nextLine(); 
            } 
        }
        while (input < min || input > max);

        return input;       
    }

    /*
     * NAME: userInput
     * IMPORT: prompt (String)
     * EXPORT: input (String)
     * PURPOSE: Validate input as being a string
     * CREATION: 07/05/2020
     * LAST MODIFICATION: 01/09/2020
     */

    public static String userInput(String prompt) {
        Scanner sc = new Scanner(System.in);
        String input;

        System.out.print(prompt);
        input = sc.nextLine();

        return input;
    }

    /*
     * NAME: printError
     * IMPORT: error (String)
     * EXPORT: NONE
     * PURPOSE: Print error messages
     * CREATION: 07/05/2020
     * LAST MODIFICATION: 30/09/2020
     */

    private static void _printError(String error) {
        System.out.println(error);
    }
}
