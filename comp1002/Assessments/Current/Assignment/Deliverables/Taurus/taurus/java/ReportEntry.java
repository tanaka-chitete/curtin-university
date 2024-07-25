/*
 * NAME: ReportEntry
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Provide entry point for running Taurus in Report mode
 * CREATION: 25/10/2020
 * LAST MODIFICATION: 10/11/2020
 */

import java.io.*;
import org.json.*;

public class ReportEntry {
    public static final int QUIT = 0;
    public static final int OPTION_3 = 3;

    /*
     * NAME: entry
     * IMPORT(S): assetFilename (String), tradeFilename (String)
     * EXPORT(S): NONE
     * PURPOSE: Provide entry point for Report operations
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 25/10/2020
     */

    public static void entry(String assetFilename, String tradeFilename) {
        JSONObject exchangeInfo = null;
        try {
            exchangeInfo = JSONObjectIO.readFromFile(assetFilename);
        }
        catch (IOException e) {
            System.out.println("Could not load " + assetFilename);
        }
        
        JSONArray recentTrades = null;
        try {
            recentTrades = JSONArrayIO.readFromFile(tradeFilename);
        }
        catch (IOException e) {
            System.out.println("Could not load " + tradeFilename);
        }

        if (exchangeInfo != null && recentTrades != null) {
            Set<String> excludedAssets = new Set<String>();
            int selection;
            do {
                System.out.println("Report Menu\n\n" + 
                    "1. Asset Filter\n" +
                    "2. Display Asset Overview\n" +
                    "3. Display Trade Overview\n" +
                    "0. Quit\n");
                String prompt = "Selection: ";
                selection = UserInterface.userInput(QUIT, OPTION_3, prompt);
                System.out.println();

                // Launches user-specified operation
                if (selection != QUIT) {
                    launch(exchangeInfo, recentTrades, selection, excludedAssets);
                }
            }
            while (selection != QUIT);
        }
        else {
            System.out.println("Cannot launch Report without valid asset and/or trade file\n");
        }
    } 

    /*
     * NAME: launch
     * IMPORT(S): selection (int)
     * EXPORT(S): NONE
     * PURPOSE: Launch Report operations
     * CREATION: 10/11/2020
     * LAST MODIFICATION: 10/11/2020
    */

    private static void launch(JSONObject exchangeInfo, JSONArray recentTrades, int selection, 
    Set<String> excludedAssets) {
        switch (selection) {
            // User specified "Asset Filter"
            case 1:
                InteractiveAndReportAnalyser.changeExcludedAssets(excludedAssets);
                break;
            // User specified "Display Asset Overview"
            case 2:
                InteractiveAndReportDisplayer.displayAllTradePairs(exchangeInfo, excludedAssets);
                break;
            // User specified "Display Trade Overview"
            case 3:
                InteractiveAndReportSorter.sortRecentTradesByPrice(recentTrades);
                InteractiveAndReportDisplayer.displayRecentTradePrices(recentTrades);

                InteractiveAndReportSorter.sortRecentTradesByQty(recentTrades);
                InteractiveAndReportDisplayer.displayRecentTradeQtys(recentTrades);

                InteractiveAndReportSorter.sortRecentTradesByQuote(recentTrades);
                InteractiveAndReportDisplayer.displayRecentTradeQuotes(recentTrades);
                break;
        }
    }
}
