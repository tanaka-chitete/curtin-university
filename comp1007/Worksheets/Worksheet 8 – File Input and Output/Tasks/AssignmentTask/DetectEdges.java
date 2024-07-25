/******************************************************************************
 * NAME: DetectEdges                                                          *
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1007                                                             * 
 * PURPOSE: Perform convolution operation on an image using a given kernel    *                                                
 * CREATION: 17/05/2020                                                       *
 * LAST MODIFICATION: 17/05/2020                                              *         
 ******************************************************************************/

import java.util.*;

public class DetectEdges
{
    public static final int OPTION_0 = 0;
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;

    public static final int MIN_HEIGHT = 3;
    public static final int MIN_WIDTH = 3;

    public static void main(String[] args)
    {
        int min, max;
        String prompt;

        int execution, imageFormat;
        String nameOfKernel, nameOfImage;

        do
        {
            // Receives input specifying mode of execution
            min = OPTION_0;
            max = OPTION_1;
            System.out.println("Main Menu\n\n" +
    
                               "1. Convolute\n" +
                               "0. Exit\n");

            prompt = "Selection: ";
            execution = UserInterface.userInput(min, max, prompt);
            System.out.println();

            // Executes if input specifies 'Convolute'
            if (execution == OPTION_1)
            {
                // Receives input specifying name of kernel
                prompt = "Name of kernel: ";
                nameOfKernel = UserInterface.userInput(prompt);
                System.out.println();

                // Receives input specifying format of image
                min = OPTION_1;
                max = OPTION_2;
                System.out.println("Image Format Selection\n\n" +

                                   "1. CSV\n" +
                                   "2. PNG\n");

                prompt = "Selection: ";
                imageFormat = UserInterface.userInput(min, max, prompt);
                System.out.println();

                // Receives input specifying name of image
                prompt = "Name of image: ";
                nameOfImage = UserInterface.userInput(prompt);
                System.out.println();

                executeOption1(nameOfKernel, imageFormat, nameOfImage);
            }
            // Executes if input specifies 'Exit'
            else
            {
                System.out.println("Program Ended\n");
            }
        }
        while (execution != OPTION_0);
    } 

    /**************************************************************************
     * NAME: executeOption1                                                   *
     * IMPORT: nameOfKernel (String), imageFormat (int), nameOfImage (String) *
     * EXPORT: none                                                           *
     * PURPOSE: Execute Option 1 (Convolute) and its related processes        *
     * CREATION: 17/05/2020                                                   *
     * LAST MODIFICATION: 17/05/2020                                          *
     **************************************************************************/

    public static void executeOption1(String nameOfKernel, 
                                      int imageFormat, 
                                      String nameOfImage)
    {
        int[][] kernelArray = new int[][] {{0}};
        // Reads kernel from CSV file into array
        kernelArray = FileIO.readCSV(nameOfKernel);

        int[][] imageArray = new int[][] {{0}};
        // Executes if input specifies 'CSV'
        if (imageFormat == OPTION_1)
        {
            // Reads image from CSV file into array
            imageArray = FileIO.readCSV(nameOfImage);
        }
        // Executes if input specifies 'PNG'
        else
        {
            // Reads image from PNG file into array
            imageArray = FileIO.readPNG(nameOfImage);
        }

        boolean validArrays = validateArrays(kernelArray, imageArray);
        // Executes if arrays are valid
        if (validArrays)
        {
            int[][] convolutionArray = new int[][] {{0}};
            // Performs convolution
            convolutionArray = convolute(kernelArray, imageArray);

            String[] nameOfImageSplit = nameOfImage.split("\\.");
            String nameOfImageNoExt = nameOfImageSplit[0];
            String nameOfConvolution = nameOfImageNoExt + "_Converted.png";
            // Writes result of convolution to PNG file
            FileIO.writePNG(nameOfConvolution, convolutionArray);
            System.out.println(nameOfImage + " successfully converted as " + 
                               nameOfConvolution + "\n");
        }
        // Executes if arrays are invalid
        else
        {
            UserInterface.printError("Image dimensions must be greater than " +
                                     "or equal to kernel dimensions\n");
        }
    }

    /**************************************************************************
     * NAME: validateArrays                                                   *
     * IMPORT: kernelArray (int[][]), imageArray (int[][])                    *
     * EXPORT: validArrays (boolean)                                          *
     * PURPOSE: Validate whether dimensions of both arrays are greater than   *
     *          or equal to minimum dimensions and if imageArray dimensions   *
     *          greater than or equal to kernelArray dimensions               *
     * CREATION: 17/05/2020                                                   *
     * LAST MODIFICATION: 17/05/2020                                          *
     **************************************************************************/

    public static boolean validateArrays(int[][] kernelArray, 
                                         int[][] imageArray)
    {   
        int kernelHeight = kernelArray.length;
        int kernelWidth = kernelArray[0].length;

        int imageHeight = imageArray.length;
        int imageWidth = imageArray[0].length;

        boolean validKernel = false;
        // Executes if kernel dimensions are greater than or equal to minimums
        if ((kernelHeight >= MIN_HEIGHT) && (kernelWidth >= MIN_WIDTH))
        {
            validKernel = true;
        }

        boolean validImage = false;
        // Executes if kernel dimensions are greater than or equal to minimums
        if (validKernel)
        {
            // Executes if image dimensions are greater than or equal to kernels
            if ((imageHeight >= kernelHeight) && (imageWidth >= kernelWidth))
            {
                validImage = true;
            }
        }

        boolean validArrays = validKernel && validImage;
        return validArrays;
    }   

    /**************************************************************************
     * NAME: convolute                                                        *
     * IMPORT: kernelArray (int[][]), imageArray (int[][])                    *
     * EXPORT: convolutionArray (int[][])                                     *
     * PURPOSE: Perform convolution on imageArray using kernelArray           *
     * CREATION: 17/05/2020                                                   *
     * LAST MODIFICATION: 17/05/2020                                          *
     **************************************************************************/

    public static int[][] convolute(int[][] kernelArray, int[][] imageArray)
    {
        int kernelHeight = kernelArray.length;
        int kernelWidth = kernelArray[0].length;

        int imageHeight = imageArray.length;
        int imageWidth = imageArray[0].length;

        int convolutionHeight = imageHeight - kernelHeight + 1;
        int convolutionWidth = imageWidth - kernelWidth + 1;
        int[][] convolutionArray = new int[convolutionHeight][convolutionWidth];

        int sum = 0;
        // Loops through each row of convolutionArray
        for (int i = 0; i < convolutionHeight; i++)
        {
            // Loops through each column of convolutionArray
            for (int j = 0; j < convolutionWidth; j++)
            {
                // Loops through each row of kernelArray
                for (int k = 0; k < kernelHeight; k++)
                {
                    // Loops through each column of kernelArray
                    for (int l = 0; l < kernelWidth; l++)
                    {
                        // Performs convolution on current pixel, adds to sum
                        sum += (imageArray[i + k][j + l] * kernelArray[k][l]);
                    }
                }
                // Assigns convolution on current pixel to convolutionArray
                convolutionArray[i][j] = sum;
                sum = 0; 
            }
        }
        return convolutionArray;
    } 
}
