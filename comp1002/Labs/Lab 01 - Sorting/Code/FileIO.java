/******************************************************************************
 * NAME: FileIO                                                               *
 * CREATOR: Tanaka Chitete                                                    * 
 * STUDENT_ID: 20169321                                                       * 
 * UNIT: COMP1002                                                             * 
 * PURPOSE: Reads and writes from and to Students.csv, respectively           * 
 * CREATION: 20/05/2020                                                       *
 * LAST MODIFICATION: 10/08/2020                                              * 
 ******************************************************************************/

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

// Obtained from Chitete, T
// Accessed 10/08/2020  

public class FileIO
{
    // CLASS CONSTANTS
    
    // none

    // PUBLIC SUBMODULES    

    /**************************************************************************
     * NAME: readCSV                                                          *
     * IMPORT: nameOfCSV (String)                                             *
     * EXPORT: CSVarray (String[][])                                          *
     * PURPOSE: Open nameOfCSV and read its contents into a 2D array          *
     * CREATION: 16/05/2020                                                   *
     * LAST MODIFICATION: 10/08/2020                                          *
     **************************************************************************/

    public static String[][] readCSV(String nameOfCSV)
    {
        // 7000 rows by 2 columns
        String[][] CSVarray = new String[7000][2];
            
        FileInputStream fileStream = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        String line;
        String[] splitLine = new String[2];

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
                // Splits line of CSV on commas (Id, name)
                splitLine = line.split(",");
                 
                // Copies Id
                CSVarray[i][0] = splitLine[0];
                // Copies name
                CSVarray[i][1] = splitLine[1];
   
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
        return CSVarray;
    }

    /**************************************************************************
     * NAME: writeCSV                                                         *
     * IMPORT: nameOfCSV (String), sourceArray (Students[])                   *
     * EXPORT: none                                                           *
     * PURPOSE: Write sourceArray to nameOfCSV                                *
     * CREATION: 16/05/2020                                                   *
     * LAST MODIFICATION: 10/05/2020                                          *
     **************************************************************************/

    public static void writeCSV(String nameOfCSV, Student[] sourceArray)
    {
        FileOutputStream fileStream = null;
        PrintWriter pw;

        // Attempts to write lines of file
        try
        {
            fileStream = new FileOutputStream(nameOfCSV);
            pw = new PrintWriter(fileStream);
            // Iterates through rows of sourceArray
            for (int i = 0; i < sourceArray.length; i++)
            {
                pw.println(sourceArray[i].toFileString());
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
}
