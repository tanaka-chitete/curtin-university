/******************************************************************************
 * NAME: FileIO                                                               *
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1007                                                             * 
 * PURPOSE: Reads and writes CSV and PNG files                                * 
 * CREATION: 16/05/2020                                                       *
 * LAST MODIFICATION: 16/05/2020                                              * 
 ******************************************************************************/

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class FileIO
{
    // CLASS CONSTANTS

    public static final int ROWS_MIN = 1;
    public static final int COLS_MIN = 1;

    public static final int VALID_CSV = 1;
    public static final int INVALID_CSV = 0;

    // PUBLIC SUBMODULES    

    /**************************************************************************
     * NAME: readCSV                                                          *
     * IMPORT: nameOfCSV (String)                                             *
     * EXPORT: CSVarray (int[][])                                             *
     * PURPOSE: Open nameOfCSV and read its contents into a 2D array          *
     * CREATION: 16/05/2020                                                   *
     * LAST MODIFICATION: 16/05/2020                                          *
     **************************************************************************/

    public static int[][] readCSV(String nameOfCSV)
    {
        int validCSV, numOfRows, numOfCols;

        int[][] CSVarray = new int[][] {{0}};
        // Stores CSV validity, row count and column count
        int[] CSVinfo = new int[3];
        validateCSV(nameOfCSV, CSVinfo);

        validCSV = CSVinfo[0];
        // Executes if CSV rows are equal length
        if (validCSV == VALID_CSV)
        {
            numOfRows = CSVinfo[1];
            numOfCols = CSVinfo[2];

            CSVarray = new int[numOfRows][numOfCols];
            
            FileInputStream fileStream = null;
            InputStreamReader rdr;
            BufferedReader bufRdr;
            String line;
            String[] splitLine = new String[numOfCols];

            try
            {
                fileStream = new FileInputStream(nameOfCSV);
                rdr = new InputStreamReader(fileStream);
                bufRdr = new BufferedReader(rdr);
                line = bufRdr.readLine();

                int i = 0;
                // Reads line of CSV into array equivalent
                while (line != null)
                {
                    // Splits line of CSV on commas
                    splitLine = line.split(",");
                    // Copies line from splitLine to array
                    for (int j = 0; j < numOfCols; j++)
                    {
                        CSVarray[i][j] = Integer.parseInt(splitLine[j]);
                    }
                    i++;
                    line = bufRdr.readLine();
                }
                fileStream.close();
            }
            // Executes if file reading is unsuccessful
            catch (IOException e)
            {
                if (fileStream != null)
                {
                    try
                    {
                        fileStream.close();
                    }
                    catch (IOException ex2)
                    { } 
                }
                throw new IllegalArgumentException("File reading unsuccessful");
            }
        }
        else
        {
            throw new IllegalArgumentException("CSV rows must be of equal " +
                                               "length");
        }
        return CSVarray;
    }

    /**************************************************************************
     * NAME: writeCSV                                                         *
     * IMPORT: nameOfCSV (String), sourceArray (int[][])                      *
     * EXPORT: none                                                           *
     * PURPOSE: Write sourceArray to nameOfCSV                                *
     * CREATION: 16/05/2020                                                   *
     * LAST MODIFICATION: 16/05/2020                                          *
     **************************************************************************/

    public static void writeCSV(String nameOfCSV, int[][] sourceArray)
    {
        int jLast;
        FileOutputStream fileStream = null;
        PrintWriter pw;

        jLast = sourceArray[0].length - 1;
        // Attempts to read lines of file
        try
        {
            fileStream = new FileOutputStream(nameOfCSV);
            pw = new PrintWriter(fileStream);
            // Iterates through rows of sourceArray
            for (int i = 0; i < sourceArray.length; i++)
            {
                // Iterates through columns of sourceArray
                for (int j = 0; j < sourceArray[0].length; j++)
                {
                    if (j != jLast)
                    {
                        pw.print(sourceArray[i][j] + ",");
                    }
                    else
                    {
                        pw.print(sourceArray[i][j]);
                    }
                }
                pw.println();
            }
            pw.close();
        }
        // Executes if error occurs writing file
        catch (IOException e)
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch(IOException ex2)
                { }
            }
            throw new IllegalArgumentException("File writing unsuccessful");
        }
    }

    /**************************************************************************
     * NAME: readPNG                                                          *
     * IMPORT: nameOfPNG (String)                                             *
     * EXPORT: PNGarray (int[][])                                             *
     * PURPOSE: Open nameOfPNG and read its contents into a 2D                *
     * CREATION: 17/05/2020                                                   *
     * LAST MODIFICATION: 17/05/2020                                          *
     **************************************************************************/

    public static int[][] readPNG(String nameOfPNG)
    {
        BufferedImage img;
        File inputFile;
        int[][] image = null;

        try
        {
            // Opens file
            inputFile = new File(nameOfPNG);

            // Turns file into an image
            img = ImageIO.read(inputFile);

            // Constructs array to hold image
            image = new int[img.getHeight()][img.getWidth()];

            // Loops through each row of pixels
            for (int i = 0; i < img.getHeight(); i++)
            {
                // Loops through each column of pixels
                for (int j = 0; j < img.getWidth(); j++)
                {
                    // Turns each pixel into a Color object
                    Color pixel = new Color(img.getRGB(j, i), true);

                    // Converts each pixel to its grayscale equivalent
                    image[i][j] = (int)((pixel.getRed() * 0.299) +
                                        (pixel.getGreen() * 0.114) +
                                        (pixel.getBlue() * 0.587));
                }
            }
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException("File reading unsuccessful");
        }
        return image;
    }

    /**************************************************************************
     * NAME: writePNG
     * IMPORT: nameOfPNG (String), sourceArray (int[][])
     * EXPORT: none
     * PURPOSE: Write sourceArray to nameOfPNG
     * CREATION: 17/05/2020                        
     * LAST MODIFICATION: 17/05/2020
     **************************************************************************/

    public static void writePNG(String nameOfPNG, int[][] sourceArray)
    {
        BufferedImage theImage;
        File outputFile;

        try
        {
            // Opens file
            outputFile = new File(nameOfPNG);

            // Constructs a BufferedImage with dimensions and type of RGB
            theImage = new BufferedImage(sourceArray[0].length,
                                         sourceArray.length,
                                         BufferedImage.TYPE_INT_RGB);

            // Loops through each row of elements
            for (int i = 0; i < sourceArray.length; i++)
            {
                // Loops through each column of elements
                for (int j = 0;  j < sourceArray[0].length; j++)
                {
                    // Caps each value at 255
                    int value = Math.abs(sourceArray[i][j] % 256);

                    // Turns greyscale pixel to a "colour" representation
                    Color newColor = new Color(value, value, value);

                    // This will set value of pixel within the .png
                    theImage.setRGB(j, i, newColor.getRGB());
                }
            }

            // Write image to a .png
            ImageIO.write(theImage, "png", outputFile);
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException("File writing unsuccessful");
        }
    }

    // PRIVATE SUBMODULES

    /**************************************************************************
     * NAME: validateCSV                                                      *
     * IMPORT: nameOfCSV (String), CSVinfo (int[])                            *
     * EXPORT: none                                                           *
     * PURPOSE: Validate whether nameOfCSV row lengths are equal              *
     * CREATION: 16/05/2020                                                   *
     * LAST MODIFICATION: 16/05/2020                                          *
     **************************************************************************/

    private static void validateCSV(String nameOfCSV, int[] CSVinfo)
    {
        int numOfCols;
        int numOfRows = 0;

        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String line; 
        String[] splitLineFirst;
        String[] splitLineNext;

        CSVinfo[0] = VALID_CSV;
        try
        {
            fileStream = new FileInputStream(nameOfCSV);
            rdr = new InputStreamReader(fileStream);
            bufRdr = new BufferedReader(rdr);
            line = bufRdr.readLine();

            // Determines length of line post split on commas
            splitLineFirst = line.split(",");
            numOfCols = splitLineFirst.length;
            // Iterates through each line while its length is same as before
            while ((line != null) && (CSVinfo[0] != INVALID_CSV))
            {
                numOfRows++;
                splitLineNext = line.split(",");
                // Executes if current line length is the same as the first
                if (splitLineNext.length == splitLineFirst.length)
                {
                    line = bufRdr.readLine();
                }
                else
                {
                    CSVinfo[0] = INVALID_CSV;
                } 
            }
            fileStream.close();

            if (CSVinfo[0] != INVALID_CSV)
            {
                CSVinfo[1] = numOfRows;
                CSVinfo[2] = numOfCols;
            }
        }
        catch (FileNotFoundException e)
        {
            CSVinfo[0] = INVALID_CSV;
        }
        catch (IOException e)
        {
            if (fileStream != null)
            {
                try
                {
                    fileStream.close();
                }
                catch (IOException ex2)
                { }
            }
            CSVinfo[0] = INVALID_CSV;
        }
    } 
}
