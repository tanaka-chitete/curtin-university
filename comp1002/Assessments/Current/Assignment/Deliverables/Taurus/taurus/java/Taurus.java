/*
 * NAME: Taurus
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Provide entry point for crypto trading data analysis suite
 * CREATION: 30/09/2020
 * LAST MODIFICATION: 06/11/2020
 */

public class Taurus {
    public static final int INTERACTIVE_ARG_COUNT = 1;
    public static final int REPORT_ARG_COUNT = 3;

    public static final String INTERACTIVE = "-i";
    public static final String REPORT = "-r";
    
    public static void main (String[] args) {
        String mode;
        String assetFilename;
        String tradeFilename;

        // User has not specified correct number of CLAs for either mode
        int argc = args.length;
        if (argc != INTERACTIVE_ARG_COUNT && argc != REPORT_ARG_COUNT) {
            // Informs user of correct Interactive and Report usage
            System.out.println("Usage (Interactive): java -cp .:json-20200518.jar Taurus -i");
            System.out.println("or");
            System.out.println("Usage (Report):      java -cp .:json-20200518.jar Taurus -r " + 
                "<asset filename> <trade filename>");
        }
        else {
            mode = args[0];
            // User specifies Interactive
            if (mode.equals(INTERACTIVE)) {
                mode = args[0];
                // User has provided incorrect number of CLAs for Interactive
                if (argc != INTERACTIVE_ARG_COUNT) {
                    // Informs user of correct Interactive usage
                    System.out.println("Usage (Interactive): java -cp .:json-20200518.jar Taurus " +
                        "-i");
                }
                // Runs in Interactive
                else {
                    System.out.println();
                    InteractiveEntry.entry();
                }
            }
            // User specifies Report
            else if (mode.equals(REPORT)) {
                mode = args[0];
                assetFilename = args[1];
                tradeFilename = args[2];
                // User has provided incorrect number of CLAs for Report
                if (argc != REPORT_ARG_COUNT) {
                    // Informs user of correct report usage
                    System.out.println("Usage (Report): java -cp .:json-20200518.jar Taurus -r " + 
                        "<asset filename> <trade filename>");
                }
                // Runs in Report
                else {
                    System.out.println();
                    ReportEntry.entry(assetFilename, tradeFilename);
                }
            }
            // User specified neither Interactive nor Report
            else {
                // Informs user of correct mode usages
                System.out.println("Usage (Interactive): java -cp .:json-20200518.jar Taurus -i");
                System.out.println("or");
                System.out.println("Usage (Report):      java -cp .:json-20200518.jar Taurus -r " + 
                    "<asset filename> <trade filename>");
            }    
        }
    }
}
