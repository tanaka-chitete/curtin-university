/******************************************************************************
 * NAME: Convolutions                                                         *
 * CREATOR: Tanaka Chitete                                                    *
 * STUDENT_ID: 20169321                                                       *
 * UNIT: COMP1007                                                             *
 * PURPOSE: Perform convolution operations given an input matrix and kernel   *
 * CREATION: 22/04/2020                                                       *
 * LATEST MODIFICATION: 22/04/2020                                            *
 ******************************************************************************/

import java.util.*;

public class Convolutions
{
    public static final int OPTION_EXIT = 0;
    public static final int OPTION_ONE = 1;
    public static final int OPTION_TWO = 2;
    public static final int OPTION_THREE = 3;
    public static final int OPTION_FOUR = 4;

    public static void main(String[] args)
    {
        // intInMinMax argument variables
        int minInt, maxInt;
        String prompt;

        // intInMinMax return assignment varaibles
        int menuSel, conSel, kerSel;

        do
        {
            System.out.println("Worksheet 5: Modularity\n\n" +
        
                               "Program: Convolutions\n\n" +

                               "Main Menu\n\n" +

                               "1. Convolute\n" +
                               "0. End Convolutions\n");    
            prompt = "Selection: ";
            minInt = OPTION_EXIT;
            maxInt = OPTION_ONE;
            menuSel = intInMinMax(prompt, minInt, maxInt); 

            // Prints newline to better format output
            System.out.println();

            if(menuSel == OPTION_ONE)
            {
                System.out.println("Convolute Selection\n\n" +

                                   "1. Convolute A\n" +
                                   "2. Convolute B\n" +
                                   "3. Convolute C\n" +
                                   "4. Convolute D\n");
                prompt = "Selection: "; 
                minInt = OPTION_ONE;
                maxInt = OPTION_FOUR;
                conSel = intInMinMax(prompt, minInt, maxInt);

                // Prints newline to better format output
                System.out.println();

                System.out.println("Kernel Selection\n\n" + 
                    
                                   "1. Vertical Kernel\n" +
                                   "2. Horizontal Kernel\n");
                prompt = "Selection: ";
                minInt = OPTION_ONE;
                maxInt = OPTION_TWO;
                kerSel = intInMinMax(prompt, minInt, maxInt);

                // Prints newline to better format output
                System.out.println();

                switch(conSel)
                {
                    case OPTION_ONE:
                        if(kerSel == OPTION_ONE)
                        {
                            calcRes(Convolute.MATRIX_A, Kernel.VERTICAL);
                        }   
                        else
                        {
                            calcRes(Convolute.MATRIX_A, Kernel.HORIZONTAL);
                        }
                        break;
                    case OPTION_TWO:
                        if(kerSel == OPTION_ONE)
                        {
                            calcRes(Convolute.MATRIX_B, Kernel.VERTICAL);
                        }   
                        else
                        {
                            calcRes(Convolute.MATRIX_B, Kernel.HORIZONTAL);
                        }
                        break;
                    case OPTION_THREE:
                        if(kerSel == OPTION_ONE)
                        {
                            calcRes(Convolute.MATRIX_C, Kernel.VERTICAL);
                        }   
                        else
                        {
                            calcRes(Convolute.MATRIX_C, Kernel.HORIZONTAL);
                        }
                        break;
                    case OPTION_FOUR:
                        if(kerSel == OPTION_ONE)
                        {
                            calcRes(Convolute.MATRIX_D, Kernel.VERTICAL);
                        }   
                        else
                        {
                            calcRes(Convolute.MATRIX_D, Kernel.HORIZONTAL);
                        }
                        break;
                }
            }
            else
            {
                System.out.println("Convolutions Ended");
            }
        }   
        while(menuSel != 0);
    }

    /**************************************************************************
     * NAME: calcRes                                                          *
     * IMPORT: convolute (int[][]), kernel (int[][])                          *
     * EXPORT: none                                                           *
     * PURPOSE: Perform convolution on convolute and kernel                   *
     * CREATION: 22/04/2020                                                   *
     * LATEST MODIFICATION: 22/04/2020                                        *
     **************************************************************************/

    public static void calcRes(int[][] convolute, int[][] kernel)
    {
        // calcRes variables
        int resRowCount, resColCount, sum;

        // validate return assignment variables
        boolean valid;

        valid = false;
        // Validates whether or not convolute is larger than kernel
        valid = validate(valid, convolute, kernel);

        if(valid == true)
        {
            resRowCount = convolute.length - kernel.length + 1;
            resColCount = convolute[0].length - kernel[0].length + 1;

            int[][] result = new int[resRowCount][resColCount];

            sum = 0;
            // Iterates through result rows
            for(int i = 0; i < result.length; i++)
            {
                // Iterates through result columns
                for(int j = 0; j < result[0].length; j++)
                {
                    // Iterates through kernel rows
                    for(int k = 0; k < kernel.length; k++)
                    {
                        // Iterates through kernel columns
                        for(int l = 0; l < kernel[0].length; l++)
                        {
                            sum = sum + (convolute[i + k][j + l] *
                                         kernel[k][l]);
                        }
                    }
                    result[i][j] = sum;
                    sum = 0;
                }
            }

            System.out.println("Convolute");
            output(convolute);

            System.out.println("Kernel");
            output(kernel);

            System.out.println("Result");
            output(result);
        }
        else
        {
            System.out.println("Convolute dimensions must be equal to or " + 
                               "greater than Kernel dimensions\n");
        }
    }
        
    /**************************************************************************
     * NAME: validate                                                         *
     * IMPORT: valid (boolean), convolute (int[][]), kernel (int[][])         *
     * EXPORT: valid (boolean)                                                *
     * PURPOSE: Validate convolute dimentions as being greater than or equal  *
     *          to kernel dimensions                                          *
     * CREATION: 22/04/2020                                                   *
     * LATEST MODIFICATION: 22/04/2020                                        *
     **************************************************************************/

    public static boolean validate(boolean valid, int[][] convolute, 
                                   int[][] kernel)
    {
        int conRowCount, conColCount;
        int kerRowCount, kerColCount;

        conRowCount = convolute.length;
        conColCount = convolute[0].length;
        kerRowCount = kernel.length;
        kerColCount = kernel[0].length;

        if((conRowCount >= kerRowCount) && (conColCount >= kerColCount))
        {
            valid = true;
        }

        return valid;
    }

    /**************************************************************************
     * NAME: output                                                           *
     * IMPORT: matrix (int[][])                                               *
     * EXPORT: none                                                           *
     * PURPOSE: Output contents of matrix                                     *
     * CREATION: 22/04/2020                                                   *
     * LATEST MODIFICATION: 22/04/2020                                        *
     **************************************************************************/

    public static void output(int[][] matrix)
    {
        // Iterates through matrix rows
        for(int i = 0; i < matrix.length; i++)
        {
            // Iterates through matrix columns
            for(int j = 0; j < matrix[0].length; j++)
            {
                System.out.print("| " + matrix[i][j] + " ");
            }
            // Prints newline to prepare printing next row
            System.out.println("|");
        }
        // Prints newline to better formate output
        System.out.println();
    }

    /**************************************************************************
     * NAME: intInMinMax                                                      *
     * IMPORT: prompt (String), min (int), max (int)                          *
     * EXPORT: input (int)                                                    *
     * PURPOSE: Receive and validate integer input as being within min and    *
     *          max bounds                                                    *
     * CREATION: 22/04/2020                                                   *
     * LATEST MODIFICATION: 22/04/2020                                        *
     **************************************************************************/

    public static int intInMinMax(String prompt, int min, int max)
    {
        Scanner sc = new Scanner(System.in);

        int optCountCheck, input;
        String error, out;
    
        optCountCheck = max - min;
        // If there are only two options available for selection
        if(optCountCheck == 1)
        {
            error = "Must be either " + min + " or " + max;
        }
        // If there are more than two options available for selection
        else
        {
            error = "Must be " + min + " to " + max + " inclusive";
        }

        out = prompt;
        do
        {
            System.out.print(out);
            input = sc.nextInt();
            out = error + '\n' + prompt;
        }
        while((input < min) || (input > max));

        return input;
    }
}
