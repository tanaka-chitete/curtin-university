/******************************************************************************
 * NAME: Menu                                                                 *
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1007                                                             * 
 * PURPOSE: Detect vertical and horizontal lines in CSV and PNG images        * 
 * CREATION: 27/05/2020                                                       *
 * LAST MODIFICATION: 31/05/2020                                              *
 ******************************************************************************/

import java.util.*;

public class Menu
{
    public static final int OPTION_1 = 1;
    public static final int OPTION_2 = 2;

    public static final int MIN_DIM_OF_IMG = 1;
    public static final int MAX_DIM_OF_IMG = Integer.MAX_VALUE;
    public static final int MIN_VAL_OF_IMG_PIX = 0;
    public static final int MAX_VAL_OF_IMG_PIX = 255;

    public static final int MIN_DIM_OF_KER = 1;
    public static final int MAX_DIM_OF_KER = Integer.MAX_VALUE;
    public static final int MIN_VAL_OF_KER_PIX = Integer.MIN_VALUE;
    public static final int MAX_VAL_OF_KER_PIX = Integer.MAX_VALUE;

    public static final int MIN_DIM_OF_SMO_SUR = 1;
    public static final int MAX_DIM_OF_SMO_SUR = Integer.MAX_VALUE;
    public static final double MIN_VAL_OF_SMO_FAC = 0.0;
    public static final double MAX_VAL_OF_SMO_FAC = 1.0;
    public static final int MIN_VAL_OF_X_CO = 1;
    public static final int MIN_VAL_OF_Y_CO = 1;

    public static final int MIN_DATE = 0;
    public static final int MAX_DATE = 99_99_9999;

    // PUBLIC SUBMODULES

    /**************************************************************************
     * NAME: importImage                                                      *
     * IMPORT: none                                                           *
     * EXPORT: imgArr (int[][])                                               *
     * PURPOSE: Calls submodule to import an image from a file or user input  *
     * CREATION: 27/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importImage()
    {
        String prompt;
        int sel;
            
        // Prints menu and receives input specifying selection
        UserInterface.printImportImageSourceMenu();
        prompt = "Selection: ";
        sel = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
        System.out.println();

        // Executes if input specifies 'File'
        int[][] imgArr = null;
        if (sel == OPTION_1)
        {
            imgArr = importImageFromFile();
        }
        // Executes is input specifies 'User Input'
        else
        {
            imgArr = importImageFromUserInput();
        }
        return imgArr;
    }

    /**************************************************************************
     * NAME: importImageFromFile                                              *
     * IMPORT: none                                                           *
     * EXPORT: imgArr (int[][])                                               *
     * PURPOSE: Attempt to import an image from a file                        *
     * CREATION: 27/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importImageFromFile()
    {
        String prompt, nmOfImg;
        String[] nmOfImgSpt;
        String nmOfImgExt;

        // Receives input specifying name of image
        prompt = "Name of image: ";
        nmOfImg = UserInterface.userInput(prompt);
        System.out.println();

        int[][] imgArr = null;
        try
        {
            nmOfImgSpt = nmOfImg.split("\\.");
            nmOfImgExt = nmOfImgSpt[1];

            // Executes if image is a CSV
            if (nmOfImgExt.equals("csv"))
            {
                imgArr = FileIO.readFromCSV(nmOfImg);

                // Executes if image is rectangular
                if (validateImage(imgArr))
                {
                    UserInterface.printMessage(nmOfImg + " successfully " +
                                               "imported\n");
                }
                else
                {
                    throw new IllegalArgumentException("Invalid image");
                }
            }
            // Executes if image is a PNG
            else if (nmOfImgExt.equals("png"))
            {       
                imgArr = FileIO.readFromPNG(nmOfImg);
                UserInterface.printMessage(nmOfImg + " successfully " +
                                           "imported\n");
            }
            else
            {
                UserInterface.printError ("Program only supports CSV and PNG " +
                                          "images\n");
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            imgArr = null;
            UserInterface.printError("Image could not be imported.\n" + 
                                     "View README.txt for usage information\n");
        }
        catch (IllegalArgumentException e)
        {
            imgArr = null;
            UserInterface.printError("Image could not be imported.\n" +
                                     "View README.txt for usage information\n");
        }
        return imgArr;
    }

    /**************************************************************************
     * NAME: importImageFromUserInput                                         *
     * IMPORT: none                                                           *
     * EXPORT: imgArr (int[][])                                               *
     * PURPOSE: Create an image from user input                               *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importImageFromUserInput()
    {
        String prompt;
        int hgtOfImg, wthOfImg, valForPix;

        // Receives input specifying height of image
        prompt = "Height of image: ";
        hgtOfImg = UserInterface.userInput(MIN_DIM_OF_IMG, MAX_DIM_OF_IMG, 
                                           prompt);

        // Receives input specifying width of image
        prompt = "Width of image: ";
        wthOfImg = UserInterface.userInput(MIN_DIM_OF_IMG, MAX_DIM_OF_IMG, 
                                           prompt);
        System.out.println();

        // Executes if image is rectangular
        int[][] imgArr = new int[hgtOfImg][wthOfImg];
            
        UserInterface.printMessage("Coordinates are in the format (x, y)\n");

        // Loops through rows of imgArr
        for (int i = 0; i < hgtOfImg; i++)
        {
            int i1bsd = i + 1;
            // Loops through columns of imgArr
            for (int j = 0; j < wthOfImg; j++)
            {
                int j1bsd = j + 1;
                // Receives input specifying value of current pixel
                prompt = "Value for pixel (" + j1bsd + ", " + i1bsd + "): ";
                valForPix = UserInterface.userInput(MIN_VAL_OF_IMG_PIX,
                                                    MAX_VAL_OF_IMG_PIX,
                                                    prompt);
                imgArr[i][j] = valForPix;
            }
        }
        System.out.println();
        return imgArr;
    }

    /**************************************************************************
     * NAME: importKernel                                                     *
     * IMPORT: none                                                           *
     * EXPORT: kerArr (int[][])                                               *
     * PURPOSE: Calls submodule to import a kernel from a file or user input  *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importKernel()
    {
        String prompt;
        int sel;

        // Prints menu and receives input specifying selection
        UserInterface.printImportKernelSourceMenu();
        prompt = "Selection: ";
        sel = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
        System.out.println();

        // Executes if input specifies 'CSV'
        int[][] kerArr = null;
        if (sel == OPTION_1)
        {
            kerArr = importKernelFromFile();
        }  
        // Executes if input specifies 'User Input'
        else
        {
            kerArr = importKernelFromUserInput();
        }
        return kerArr;
    }

    /**************************************************************************
     * NAME: importKernelFromFile                                             *
     * IMPORT: none                                                           *
     * EXPORT: kerArr (int[][])                                               *
     * PURPOSE: Attempts to import a kernel from a file                       *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importKernelFromFile()
    {
        String prompt, nmOfKer;
        String[] nmOfKerSpt;
        String nmOfKerExt;
        
        // Receives input specifying name of kernel
        prompt = "Name of kernel file: ";
        nmOfKer = UserInterface.userInput(prompt);
        System.out.println();

        int[][] kerArr = null;
        try
        {
            nmOfKerSpt = nmOfKer.split("\\.");
            nmOfKerExt = nmOfKerSpt[1];

            // Executes if kernel is a CSV
            if (nmOfKerExt.equals("csv"))
            {
                kerArr = FileIO.readFromCSV(nmOfKer);

                // Executes if kernel is square
                if (kerArr.length == kerArr[0].length)
                {
                    UserInterface.printMessage(nmOfKer + " successfully " +
                                               "imported\n");
                }
                else
                {
                    throw new IllegalArgumentException("Invalid kernel");
                }
            }
            else
            {
                UserInterface.printError("Program only supports CSV kernels\n");
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            kerArr = null;
            UserInterface.printError("Kernel could not be imported.\n" + 
                                     "View README.txt for usage information\n");
        }
        catch (IllegalArgumentException e)
        {
            kerArr = null;
            UserInterface.printError("Kernel could not be imported.\n" +
                                     "View README.txt for usage information\n");
        }
        return kerArr;
    }

    /**************************************************************************
     * NAME: importKernelFromUserInput                                        *
     * IMPORT: none                                                           *
     * EXPORT: kerArr (int[][])                                               *
     * PURPOSE: Creates a kernel from user input                              *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] importKernelFromUserInput()
    {
        String prompt;
        int hgtOfKer, wthOfKer, valForPix;

        // Receives input specifying height of image
        prompt = "Height and width of kernel: ";
        hgtOfKer = UserInterface.userInput(MIN_DIM_OF_KER, MAX_DIM_OF_KER, 
                                           prompt);
        System.out.println();
        wthOfKer = hgtOfKer;

        UserInterface.printMessage("Coordinates are in the format (x, y)\n");

        // Loops through rows of kerArr
        int[][] kerArr = new int[hgtOfKer][wthOfKer];
        for (int i = 0; i < hgtOfKer; i++)
        {
            int i1bsd = i + 1;
            // Loops through columns of imgArr
            for (int j = 0; j < wthOfKer; j++)
            {
                int j1bsd = j + 1;
                // Receives input specifying value of current pixel
                prompt = "Value for pixel (" + j1bsd + ", " + i1bsd + "): ";
                valForPix = UserInterface.userInput(MIN_VAL_OF_KER_PIX,
                                                    MAX_VAL_OF_KER_PIX,
                                                    prompt);
                kerArr[i][j] = valForPix;
            }
        }
        System.out.println();
        return kerArr;
    }

    /**************************************************************************
     * NAME: convoluteImage                                                   *
     * IMPORT: imgArr (int[][]), kerArr (int[][])                             *
     * EXPORT: conArr (int[][])                                               *
     * PURPOSE: Attempt to convolute image using an image and kernel          *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    public static int[][] convoluteImage(int[][] imgArr, int[][] kerArr)
    {
        Image imgObj;

        // Executes if an image and kernel have been imported
        int[][] conArr = null;
        if ((imgArr != null) && (kerArr != null))
        {
            try
            {
                imgObj = new Image(imgArr);
                conArr = imgObj.convolute(kerArr);
                UserInterface.printMessage("Image successfully " +
                                           "convoluted\n");
            }
            catch (IllegalArgumentException e)
            {
                conArr = null;
                UserInterface.printError("Image could not be convoluted.\n" +
                                         "View README.txt for usage " +
                                         "information\n");
            }
        }
        else
        {
            UserInterface.printError("No image and/or kernel have been " + 
                                     "imported\n");
        }
        return conArr;
    }

    /**************************************************************************
     * NAME: smoothenImageOrConvolute                                         *
     * IMPORT: arr (int[][])                                                  *
     * EXPORT: smthndArr (int[][])                                            *
     * PURPOSE: Attempt to smoothen image or convolute                        *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/
    
    public static int[][] smoothenImageOrConvolute(int[][] arr) 
    {
        String prompt;
        int sel;
        String error;
        int maxDimOfSmoSur, hgtOfSmoSur, wthOfSmoSur, maxValOfXco, x,
            maxValOfYco, y;
        double smoFac;

        // Executes if selected image has been imported or convoluted
        int[][] smthndArr = arr;
        if (arr != null)
        {
            UserInterface.printMessage("Dimensions of image:\n" + 
                                       "Height: " + arr.length + "\n" + 
                                       "Width: " + arr[0].length + "\n");

            // Executes if width of image is smaller than its height
            maxDimOfSmoSur = arr.length;
            if (arr[0].length < maxDimOfSmoSur)
            {
                maxDimOfSmoSur = arr[0].length;
            }

            // Executes to ensure dimensions of max smoothing surface are odd
            if (maxDimOfSmoSur % 2 == 0)
            {
                maxDimOfSmoSur--;
            }
    
            prompt = "Height and width of smoothing surface: ";
            hgtOfSmoSur = UserInterface.userInput(MIN_DIM_OF_SMO_SUR, 
                                                  maxDimOfSmoSur, prompt);
            System.out.println();
            wthOfSmoSur = hgtOfSmoSur;

            // Executes if dimensions of smoothing surface are odd
            if (hgtOfSmoSur % 2 != 0)
            {
                try
                {
                    maxValOfXco = arr[0].length;
                    prompt = "x-axis coordinate of target pixel: ";
                    x = UserInterface.userInput(MIN_VAL_OF_X_CO, maxValOfXco,
                                                prompt);

                    maxValOfYco = arr.length;
                    prompt = "y-axis coordinate of target pixel: ";
                    y = UserInterface.userInput(MIN_VAL_OF_Y_CO, maxValOfYco,
                                                prompt);
    
                    prompt = "Smoothening factor: ";
                    smoFac = UserInterface.userInput(MIN_VAL_OF_SMO_FAC, 
                                                     MAX_VAL_OF_SMO_FAC, 
                                                     prompt);
                    System.out.println();

                    // Attempts to smoothen image
                    Image obj = new Image(arr);
                    obj.smoothen(x, y, hgtOfSmoSur, smoFac); 
                    smthndArr = obj.getOriginalImage(); 
                    
                    UserInterface.printMessage("Image successfully " +
                                               "smoothened\n");
                }
                catch (IllegalArgumentException e)
                {
                    smthndArr = arr;
                    UserInterface.printError("Image could not be " + 
                                             "smoothened.\n" +
                                             "View README.txt for usage " +
                                             "information\n");
                }
            }
            else
            {
                UserInterface.printError("Dimensions of smoothing surface " + 
                                         "must be odd\n");
            }     
        }
        else
        {
            UserInterface.printError("No image has been imported/convoluted\n"); 
        }
        return smthndArr;
    } 
      
    /**************************************************************************
     * NAME: exportImageOrConvolute                                           *
     * IMPORT: arr (int[][])                                                  * 
     * EXPORT: none                                                           *
     * PURPOSE: Attempt to export image or convolute                          *
     * CREATION: 28/05/2020                                                   *
     * LAST MODIFICATION: 28/05/2020                                          *
     **************************************************************************/

    public static void exportImageOrConvolute(int[][] arr) 
    {
        String prompt;
        int sel;
        String error;
        int dtForConInt;
        int day, mth, yr;
        Date dtForConObj;
        String nmOfImg;
        
        // Executes if selected image has been imported or convoluted
        if (arr != null)
        {
            try
            {
                // Receives input specifying image date
                prompt = "Date to place in name of image (DDMMYYYY): ";
                dtForConInt = UserInterface.userInput(MIN_DATE, MAX_DATE, 
                                                      prompt);               
                System.out.println();
 
                // Splits date into its day, month and year components
                day = dtForConInt / 1_000_000;
                mth = (dtForConInt / 10_000) % 100;
                yr = dtForConInt % 10_000;
                dtForConObj = new Date(day, mth, yr);

                // Prints menu and receives input specifying selection
                UserInterface.printExportImageOrConvoluteFormatMenu();
                prompt = "Selection: ";
                sel = UserInterface.userInput(OPTION_1, OPTION_2, prompt);
                System.out.println();

                // Receives input specifying name of image and adds details
                prompt = "Name of image: ";
                nmOfImg = UserInterface.userInput(prompt);
                System.out.println();
                nmOfImg = dtForConObj.toString() + "_Processed_" + nmOfImg;

                // Executes if input specifies 'CSV'
                if (sel == OPTION_1)
                {
                    nmOfImg += ".csv";
                    FileIO.writeToCSV(nmOfImg, arr);
                }
                // Executes if input specifies 'PNG'
                else
                {
                    nmOfImg += ".png";
                    FileIO.writeToPNG(nmOfImg, arr);   
                }
                    
                UserInterface.printMessage("Image saved as: " + nmOfImg + "\n");
            }
            catch (IllegalArgumentException e)
            {
                UserInterface.printError("Image could not be saved.\n" +
                                         "View README.txt for usage " +
                                         "information\n");
            }
        }
        else
        {
            UserInterface.printError("No image has been imported/convoluted\n"); 
        }
    }

    // PRIVATE SUBMODULES

    /**************************************************************************
     * NAME: validateImage                                                    *
     * IMPORT: imgArr (int[][])                                               *
     * EXPORT: validImage (boolean)                                           *
     * PURPOSE: Validate image as being rectangular and having elements with  *
     *          values within 0 and 255 inclusive                             *
     * CREATION: 31/05/2020                                                   *
     * LAST MODIFICATION: 31/05/2020                                          *
     **************************************************************************/

    private static boolean validateImage(int[][] imgArr)
    {
        boolean isRectangular, wthn0and255, validImgArr;

        isRectangular = false;
        wthn0and255 = false;
        // Executes if imgArr is rectangular
        if (imgArr.length != imgArr[0].length)
        {
            isRectangular = true;

            wthn0and255 = true;
            // Loops through rows of imgArr
            int i = 0;
            while (i < imgArr.length && wthn0and255)
            {
                // Loops through columns of imgArr
                int j = 0;
                while (j < imgArr[0].length && wthn0and255)
                {
                    // Executes if current pixel is out of bounds
                    if (!(MIN_VAL_OF_IMG_PIX <= imgArr[i][j] && 
                          imgArr[i][j] <= MAX_VAL_OF_IMG_PIX))
                    {
                        wthn0and255 = false;
                    }
                    j++;
                }
                i++;
            }
        }
        validImgArr = isRectangular && wthn0and255;
        return validImgArr; 
    }
}
