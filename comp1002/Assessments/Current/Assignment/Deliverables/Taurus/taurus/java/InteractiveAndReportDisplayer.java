import java.util.*;
import org.json.*;

public class InteractiveAndReportDisplayer {
    private static final String PRICE_KEY = "price";
    private static final String PRICE_DISP = "Price";
    private static final String QTY_KEY = "qty";
    private static final String QTY_DISP = "Quantity";
    private static final String QUOTE_KEY = "quoteQty";
    private static final String QUOTE_DISP = "Quote";

    /*
     * NAME: displayAllTradePairs
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display all trade pairs involving assets not excluded from analyses
     * CREATION: 14/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayAllTradePairs(JSONObject exchangeInfo, Set<String> excludedAssets) {
        JSONArray pairs = exchangeInfo.getJSONArray("symbols");

        System.out.println("Asset Overview\n");

        System.out.println("Trade Pairs\n");

        int tradePairCount = 0;
        for (int i = 0; i < pairs.length(); i++) {
            JSONObject currPair = pairs.getJSONObject(i);
            String currBase = currPair.getString("baseAsset");
            String currQuote = currPair.getString("quoteAsset");
            
            // Prints trade pair if neither base nor quote has been excluded
            if (!excludedAssets.contains(currBase) && !excludedAssets.contains(currQuote)) {
                System.out.println(currPair.getString("symbol"));
                tradePairCount++;
            }
        }
        System.out.println("\nExcluded Assets: " + excludedAssets + "\n");

        System.out.println("Visible Trade Pair Count: " + tradePairCount);
        System.out.println("Total Trade Pair Count:   " + pairs.length() + "\n");

        Date retrievalDate = new Date(exchangeInfo.getLong("serverTime"));
        System.out.println("Trade Pairs are valid as of: " + retrievalDate + "\n");
    }

    /*
     * NAME: displayRecentTrades
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display recent trades for a user-specified trade pair ordered by a given stat
     * CREATION: 13/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayRecentTrades(JSONArray recentTrades, 
        String statKey, String statDisp) {
        for (int i = 0; i < recentTrades.length(); i++) {
            int iZero = i;
            int iOne = i + 1;
            JSONObject currTrade = recentTrades.getJSONObject(iZero);
            String currStat = currTrade.getString(statKey);
            Date currDate = new Date(currTrade.getLong("time"));

            System.out.println(iOne + ".\n" + statDisp + ": " + currStat + "\nDate:  " + currDate + 
                "\n");
        }
    }

    /*
     * NAME: displayRecentTradePrices
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display recent trades for a user-specified trade pair ordered by price
     * CREATION: 13/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayRecentTradePrices(JSONArray recentTrades) {
        System.out.println("Trade Overview\n");

        int resultCount = recentTrades.length();
        System.out.println("Top " + resultCount + " Trade Prices (USD)\n");

        String statKey = PRICE_KEY;
        String statDisp = PRICE_DISP;
        displayRecentTrades(recentTrades, statKey, statDisp);
    }

    /*
     * NAME: displayRecentTradeQtys
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display recent trades for a user-specified trade pair ordered by quantity
     * CREATION: 13/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayRecentTradeQtys(JSONArray recentTrades) {
        int resultCount = recentTrades.length();
        System.out.println("Top " + resultCount + " Trade Quantities\n");

        String statKey = QTY_KEY;
        String statDisp = QTY_DISP;
        displayRecentTrades(recentTrades, statKey, statDisp);
    }

    /*
     * NAME: displayRecentTradeQuotes
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display recent trades for a user-specified trade pair ordered by quote
     * CREATION: 13/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayRecentTradeQuotes(JSONArray recentTrades) {
        int resultCount = recentTrades.length();
        System.out.println("Top " + resultCount + " Trade Quotes " + "(USD)\n");

        String statKey = QUOTE_KEY;
        String statDisp =  QUOTE_DISP;
        displayRecentTrades(recentTrades, statKey, statDisp);
    }
}