/******************************************************************************
 * NAME: TowersOfHanoi                                                        *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1002                                                             *
 * PURPOSE: Recursively compute the solution to a game of Towers of Hanoi,    *
 *          given a number of disks, a source peg and a destination peg       *
 * CREATION: 16/08/2020                                                       *
 * LAST MODIFICATION: 16/08/2020                                              *
 ******************************************************************************/

import java.util.*;

public class TowersOfHanoi
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
                prompt = "Number of disks: ";
                int n = UserInterface.userInput(Integer.MIN_VALUE,
                                                Integer.MAX_VALUE, 
                                                prompt);
                prompt = "Source peg: ";
                int src = UserInterface.userInput(Integer.MIN_VALUE,
                                                  Integer.MAX_VALUE,
                                                  prompt);

                prompt = "Destination peg: ";
                int dest = UserInterface.userInput(Integer.MIN_VALUE, 
                                                   Integer.MAX_VALUE,
                                                   prompt);
                System.out.println("\nSolution\n");
                towers(n, src, dest);
                System.out.println();    
            }
        }
        while (menuSel != QUIT);    
    }

    /**************************************************************************
     * NAME: towers                                                           *
     * IMPORT: n (int), src (int), dest (int)                                 *
     * EXPORT: towersPrivate(n, src, dest) (null)                             *
     * PURPOSE: Act as wrapper function for towersPrivate                     * 
     * CREATION: 16/08/2020                                                   *
     * LAST MODIFICATION: 16/08/2020                                          *
     **************************************************************************/

    public static void towers(int n, int src, int dest)
    {
        if (n < 0)
        {
            throw new IllegalArgumentException("Number of disks cannot be " +
                                               "less than 0");
        }
        if (src < 1 || src > 3)
        {
            throw new IllegalArgumentException("Source peg must be between " +
                                               "1 and 3"); 
        }
        if (dest < 1 || dest > 3)
        {
            throw new IllegalArgumentException("Destination peg must be " +
                                               "between 1 and 3"); 
        }
        if (src == dest)
        {
            throw new IllegalArgumentException("Source and destination pegs " + 
                                               "cannot be the same");
        }
    
        String level = "";
        towersPrivate(n, src, dest, level);    
    }
    
    /**************************************************************************
     * NAME: towersPrivate                                                    *
     * IMPORT: n (int), src (int), dest (int)                                 *
     * EXPORT: (null)                                                         *
     * PURPOSE: Recursively compute the solution to a game of Towers of Hanoi *
     *          given n, src and dest                                         *
     * CREATION: 12/08/2020                                                   *
     * LAST MODIFICATION: 15/08/2020                                          *
     **************************************************************************/

    private static void towersPrivate(int n, int src, int dest, String level)
    {
        if (n == 1)
        {
            moveDisk(src, dest, level); // Base case - moving a single disk
        }
        else
        {
            int temp = 6 - src - dest; // Other non-target peg
            // Move all but bottom disk to temp
            towersPrivate(n - 1, src, temp, level + "    ");
            // Move bottom disk to target peg dest 
            moveDisk(src, dest, level);
            // Move the remaining disks from temp to dest 
            towersPrivate(n - 1, temp, dest, level + "    "); 
        }
    }

    private static void moveDisk(int src, int dest, String level)
    {
        System.out.println(level + "Moving the top disk from " + src + " to " + 
                           dest);
    }
}
