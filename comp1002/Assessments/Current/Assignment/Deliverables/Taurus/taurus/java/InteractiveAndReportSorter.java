import org.json.*;

public class InteractiveAndReportSorter {
    private static final String PRICE = "price";
    private static final String QTY = "qty";
    private static final String QUOTE = "quoteQty";

    public static void sortRecentTradesByPrice(JSONArray recentTrades) {
        _sortRecentTrades(recentTrades, PRICE);
    }

    public static void sortRecentTradesByQty(JSONArray recentTrades) {
        _sortRecentTrades(recentTrades, QTY);
    }

    public static void sortRecentTradesByQuote(JSONArray recentTrades) {
        _sortRecentTrades(recentTrades, QUOTE);
    }

    /*
     * NAME: _sortRecentTrades
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Sort recent trades for a user-specified trade pair ordered by a given stat
     * CREATION: 12/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    private static void _sortRecentTrades(JSONArray recentTrades, String stat) {

        for (int i = 1; i < recentTrades.length(); i++) {
            JSONObject keyObj = recentTrades.getJSONObject(i);
            String keyStatStr = keyObj.getString(stat);
            double keyStatDouble = Double.parseDouble(keyStatStr);

            // Loops through elements from the end
            int j = i - 1;
            boolean keyHigherThanCurr = true;
            while ((j >= 0) && keyHigherThanCurr) {
                JSONObject currObj = recentTrades.getJSONObject(j);
                String currStatStr = currObj.getString(stat);
                double currStatDouble = Double.parseDouble(currStatStr);

                // Shuffles current element to next index
                if (currStatDouble < keyStatDouble) {
                    recentTrades.put(j + 1, currObj);
                    j--;
                }
                // Prepares to stop sorting as updated index was found
                else {
                    keyHigherThanCurr = false;
                }
            }
            // Places key at updated index
            recentTrades.put(j + 1, keyObj);
        }
    }
}
