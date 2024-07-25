/*
 * NAME: Report Entry
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Provide entry point and launcher for Report operations
 * CREATION: 25/10/2020
 * LAST MODIFICATION: 25/10/2020
 */

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
        JSONArray pairs = null;
        JSONArray recentTrades = null;

        DSAHashSet excludedAssets = new DSAHashSet();
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

            switch (selection) {
                // User specified "Asset Filter"
                case 1:
                    InteractiveAnalyser.changeExcludedAssets(excludedAssets);
                    break;
                // User specified "Display Asset Overview"
                case 2:
                    if (pairs == null) {
                        pairs = IOJSONArray.readFromFile(assetFilename);
                    }

                    if (pairs != null) {
                        InteractiveAndReportDisplayer.displayAllTradePairs(pairs, excludedAssets);
                    }
                    break;
                // User specified "Display Trade Overview"
                case 3:
                    if (recentTrades == null) {
                        recentTrades = IOJSONArray.readFromFile(tradeFilename);
                    }

                    if (recentTrades != null) {
                        InteractiveAndReportSorter.sortRecentTradesByPrice(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradePrices(recentTrades);

                        InteractiveAndReportSorter.sortRecentTradesByQty(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradeQtys(recentTrades);

                        InteractiveAndReportSorter.sortRecentTradesByQuote(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradeQuotes(recentTrades);
                    }
                    break;
            }
        }
        while (selection != QUIT);
    } 
}
