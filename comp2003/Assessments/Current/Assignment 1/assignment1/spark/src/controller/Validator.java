/**
 * Check if Command-Line arguments represent running Spark using a valid invocation
 * @author Tanaka Chitete
 */


package controller;

import java.util.Set;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Validator {
    /** Constants */

    public static final String USAGE = 
        "Usage: To run Spark, execute one of the following commands:\n" +
        "ant -Darg0=g -Darg1=d generateanddisplay\n" + 
        "ant -Darg0=g -Darg1=w -Darg2=<output filename> generateandwrite\n" + 
        "ant -Darg0=r -Darg1=<input filename> -Darg2=d readanddisplay\n" + 
        "ant -Darg0=r -Darg1=<input filename> -Darg2=w -Darg3=<output filename> readandwrite\n" + 
        "View src/README.txt for detailed usage description";

    /**
     * Check if args specifies running Spark using Invocation 1: Generate and Display, where the 
     * invocation is executed using:
     * 
     * ant -Darg0=g -Darg1=d generateanddisplay
     * 
     * @param args
     * @return isGenerateAndDisplay: Boolean representing if args specifies Invocation 1
     */
    public static boolean isGenerateAndDisplay(String[] args) {
        boolean isGenerateAndDisplay = false;
        if (args.length == 2) {
            if (args[0].equals("g")) {
                if (args[1].equals("d")) {
                    isGenerateAndDisplay = true;
                }
            }
        }

        return isGenerateAndDisplay;
    }

    /**
     * Check if args specifies running Spark using Invocation 2: Generate and Write, where the 
     * invocation is executed using:
     * 
     * ant -Darg0=g -Darg1=w -Darg2=resources/outputfiles/outputfile.csv generateandwrite
     * 
     * @param args
     * @return isGenerateAndWrite: Boolean representing if args specifies Invocation 2
     */
    public static boolean isGenerateAndWrite(String[] args) {
        boolean isGenerateAndWrite = false;
        if (args.length == 3) {
            if (args[0].equals("g")) {
                if (args[1].equals("w")) {
                    if (isValidFileExtension(args[2])) {
                        isGenerateAndWrite = true;
                    }
                }
            }
        }

        return isGenerateAndWrite;
    }

    /**
     * Check if args specifies running Spark using Invocation 3: Read and Display, where the 
     * invocation is executed using:
     * 
     * ant -Darg0=r -Darg1=resources/inputfiles/inputfile.csv -Darg2=d readanddisplay
     * 
     * @param args
     * @return isReadAndDisplay: Boolean representing if args specifies Invocation 3
     */
    public static boolean isReadAndDisplay(String[] args) {
        boolean isReadAndDisplay = false;
        if (args.length == 3) {
            if (args[0].equals("r")) {
                if (isValidFileExtension(args[1])) {
                    if (args[2].equals("d")) {
                        isReadAndDisplay = true;
                    }
                }
            }
        }

        return isReadAndDisplay;
    }

    /**
     * Check if args specifies running Spark using Invocation 4: Read and Write, where the 
     * invocation is executed using:
     * 
     * ant -Darg0=r -Darg1=resources/inputfiles/inputfile.csv  -Darg2=w -Darg3=resources/outputfiles/outputfile.csv readandwrite
     * 
     * @param args
     * @return isReadAndWrite: Boolean representing if args specifies Invocation 4
     */
    public static boolean isReadAndWrite(String[] args) {
        boolean isReadAndWrite = false;
        if (args.length == 4) {
            if (args[0].equals("r")) {
                if (isValidFileExtension(args[1])) {
                    if (args[2].equals("w")) {
                        if (isValidFileExtension(args[3])) {
                            isReadAndWrite = true;
                        }
                    }
                }
            }
        }

        return isReadAndWrite;
    }

    /**
     * Check if String filename extension is .csv
     * 
     * @param filename
     * @return Boolean representing if String filename extension is .csv
     */   
    private static boolean isValidFileExtension(String filename) {
        boolean isValidFileExtension = filename.endsWith(".csv");
        if (!isValidFileExtension) {
            System.out.format("Validate input/output file extension fail: file %s does not " + 
                              "denote a .csv file %n", filename);
            System.out.println(); // Formatting purposes
        }

        return isValidFileExtension;
    }

    /**
     * Check if contents of input file are in expected format
     * 
     * @param inputFilename
     * @return areValidInputFileContents
     */   
    public static boolean areValidInputFileContents(String inputFilename) {
        Set<String> nameLabels = new HashSet<>();
        Set<String> parentLabels = new HashSet<>();
        
        boolean areValidInputFileContents = true;
        try {
            if (!new File(inputFilename).isFile()) {
                areValidInputFileContents = false;
                System.out.format("Validate network file existence fail: file %s does not exist%n",
                                  inputFilename);
            }
            else {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilename));
                String row = bufferedReader.readLine();
                
                if (!isValidRoot(row, nameLabels, parentLabels)) {
                    areValidInputFileContents = false;
                    System.out.format("Validate root node fail: line \"%s\" does not represent a " + 
                                      "valid root node%n", row);
                }
                else {
                    while (areValidInputFileContents && (row = bufferedReader.readLine()) != null) {
                        if (!isValidNonRoot(row, nameLabels, parentLabels)) {
                            areValidInputFileContents = false;
                        }
                    }
                }
                bufferedReader.close();
            }
        }
        catch (IOException e) {
            areValidInputFileContents = false;
        }

        return areValidInputFileContents;
    }

    /**
     * Check if String consists of a single word
     * 
     * @param row
     * @param nameLabels
     * @param parentLabels
     * @return isValidRoot
     */   
    private static boolean isValidRoot(String row, 
                                       Set<String> nameLabels, 
                                       Set<String> parentLabels) {
        String[] delimitedRow = row.split(",");

        boolean isValidRoot = false;
        if (delimitedRow.length == 1) {
            if (!delimitedRow[0].isBlank()) {

                nameLabels.add(delimitedRow[0]);
                parentLabels.add(delimitedRow[0]);
                isValidRoot = true;
            }
        }

        return isValidRoot;
    }

    /**
     * Check if String name and parent (and, potentially, category and consumption) are valid
     * 
     * @param delimitedRow
     * @param nameLabels
     * @param parentLabels
     * @return isValidNonRoot
     */   
    private static boolean isValidNonRoot(String row, 
                                          Set<String> nameLabels, 
                                          Set<String> parentLabels) {
        String[] delimitedRow = row.split(",");

        boolean isValidNonRoot = false;
        if (2 <= delimitedRow.length && delimitedRow.length <= 10) {
            // Prevents another node with given name from being added to network
            if (!nameLabels.contains(delimitedRow[0])) {
                // Allows subsequent nodes to use node with given name as parent
                if (parentLabels.contains(delimitedRow[1])) {
                    if (delimitedRow.length == 2) {
                        isValidNonRoot = true;
                    }
                    else {
                        isValidNonRoot = isValidLeaf(delimitedRow, nameLabels, parentLabels);
                    }
                }
            }

            if (!isValidNonRoot) {
                if (delimitedRow.length == 2) {
                    System.out.format("Validate non-root/non-leaf node fail: line \"%s\" " + 
                                      "represents an invalid non-root/non-leaf node%n", row);
                }
                else {
                    System.out.format("Validate leaf node fail: line \"%s\" represents an " + 
                                      "invalid leaf node%n", row);
                }
            }
            else {
                // Used to prevent another node with given name from being added to network
                nameLabels.add(delimitedRow[0]);
                // Used to allow subsequent nodes to use node with given name as parent
                parentLabels.add(delimitedRow[0]);
            }
        }

        return isValidNonRoot;
    }

    /**
     * Check if String category and consumption are valid
     * 
     * @param delimitedRow
     * @param nameLabels
     * @param parentLabels
     * @return isValidLeaf
     */    
    private static boolean isValidLeaf(String[] delimitedRow,
                                       Set<String> nameLabels,
                                       Set<String> parentLabels) {
        int i = 2;
        boolean isValidLeaf = true;
        while (isValidLeaf && i < delimitedRow.length) {
            boolean isValidCategoryAndConsumption = false;
            String[] categoryAndConsumption = delimitedRow[i].split("=");
            if (categoryAndConsumption.length == 2) {
                if (isValidCategory(categoryAndConsumption[0])) {
                    if (isValidConsumption(categoryAndConsumption[1])) {
                        isValidCategoryAndConsumption = true;
                    }
                }
            }

            if (!isValidCategoryAndConsumption) {
                isValidLeaf = false;
            }

            i++;
        }

        return isValidLeaf;
    }

    /**
     * Check if String denotes a valid category
     * 
     * @param category
     * @return Boolean representing if category denotes a valid category
     */
    private static boolean isValidCategory(String category) {
        return category.equals("dm") || 
               category.equals("da") || 
               category.equals("de") || 
               category.equals("em") || 
               category.equals("ea") || 
               category.equals("ee") || 
               category.equals("h")  || 
               category.equals("s");
    }

    /**
     * Check if String can be parsed as a double
     * 
     * @param consumption
     * @return isValidConsumption
     */
    private static boolean isValidConsumption(String consumption) {
        boolean isValidConsumption;
        try {
            Double.parseDouble(consumption);
            isValidConsumption = true;
        }
        catch (NumberFormatException e) {
            isValidConsumption = false;
        }

        return isValidConsumption;
    }
}
