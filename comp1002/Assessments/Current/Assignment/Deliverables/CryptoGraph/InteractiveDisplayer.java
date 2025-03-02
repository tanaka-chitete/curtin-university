import org.json.*;

public class InteractiveDisplayer {
    /*
     * NAME: getAssetTradePairs
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display all trade pairs involving a user-specified asset
     * CREATION: 06/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayAssetTradePairs(JSONArray pairs, String asset) {
        System.out.println("Asset Details: " + asset + "\n");

        int basePairCount = 0;
        int quotePairCount = 0;
        for (int i = 0; i < pairs.length(); i++) {
            JSONObject currPair = pairs.getJSONObject(i);
            String currBase = currPair.getString("baseAsset");
            String currQuote = currPair.getString("quoteAsset");

            // Prints trade pair if asset from user is base in current pair
            if (asset.equals(currBase)) {
                System.out.println(currPair.getString("symbol"));

                basePairCount++;
            }
            // Prints trade pair if asset from user is quote in current pair
            else if (asset.equals(currQuote)) {
                System.out.println(currPair.getString("symbol"));

                quotePairCount++;
            }
        }

        int pairCount = basePairCount + quotePairCount;
        double basePairPct = ((double) basePairCount / (double) pairCount) * 100.0 ;
        double quotePairPct = ((double) quotePairCount / (double) pairCount) * 100.0;

        System.out.println("\nTrade Pairs: " + pairCount);

        System.out.print("Trade Pairs where " + asset + " is the base: " + basePairCount + "/" + 
            pairCount);
        System.out.println(" (" + basePairPct + "%)");

        System.out.print("Trade Pairs where " + asset + " is the quote: " + quotePairCount + "/" + 
            pairCount);
        System.out.println(" (" + quotePairPct + "%)\n");
    }

    /*
     * NAME: displayTradePriceChangeInfo
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display price change information about a user-specified trade pair
     * CREATION: 06/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayPriceChangeInfo(JSONObject priceChangeInfo) {
        System.out.println("Trade Details\n");

        System.out.println("Symbol: " + priceChangeInfo.getString("symbol") + "\n");

        System.out.println("Price: " + priceChangeInfo.getString("lastPrice") + " USD");
        System.out.println("Price Change ($): " + priceChangeInfo.getString("priceChange") + " USD");
        System.out.println("Price Change (%): " + priceChangeInfo.getString("priceChangePercent") + 
            "\n");
    }

    /*
     * NAME: displayTradePaths
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Display all paths from user-specified base and quote assets
     * CREATION: 07/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void displayTradePaths(DSAGraph tradePaths, 
        String baseAsset, String quoteAsset) {
        System.out.println("Trade Paths\n");

        tradePaths.displayPaths(baseAsset, quoteAsset);
        System.out.println();
    }
}
