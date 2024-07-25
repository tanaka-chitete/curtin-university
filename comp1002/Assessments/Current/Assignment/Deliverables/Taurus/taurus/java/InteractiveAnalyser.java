import java.io.*;
import java.util.*;
import org.json.*;

public class InteractiveAnalyser {
    private static final int EXIT = 0;
    private static final int OPTION_4 = 4;
    private static final int RESULT_COUNT_MIN = 1;
    private static final int RESULT_COUNT_MAX = 100;

    private static final String BASE = "https://api.binance.com/api/v3/";

    /*
     * NAME: getAssetTradePairs
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Get all trade pairs involving a user-specified asset
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void getAssetTradePairs() {
        JSONObject exchangeInfo = null;
        int selection;
        do {
            System.out.println("Asset Details Menu\n\n" +
                "1. Display\n" + 
                "2. Make Live Request\n" +
                "3. Load from File\n" + 
                "4. Save to File\n" + 
                "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Display"
                case 1:
                    if (exchangeInfo != null) {
                        prompt = "Symbol: ";
                        String asset = UserInterface.userInput(prompt);
                        asset = asset.toUpperCase();
                        System.out.println();

                        InteractiveDisplayer.displayAssetTradePairs(exchangeInfo, asset);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    try {
                        String request = BASE + "exchangeInfo";
                        exchangeInfo = JSONObjectIO.readFromURL(request);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Make Live Request\n");
                    }
                    break;
                // User specified "Load from File"
                case 3:
                    String filename;
                    try {
                        prompt = "Filename: ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        exchangeInfo = JSONObjectIO.readFromFile(filename);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    catch (JSONException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    break;
                // User specified "Save to File"
                case 4:
                    if (exchangeInfo != null) {
                        try {
                            prompt = "Filename (without .json extension): ";
                            filename = UserInterface.userInput(prompt);
                            System.out.println();
                            filename += ".json";

                            JSONObjectIO.writeToFile(exchangeInfo, filename);
                            System.out.println("File saved as: " + filename + "\n");
                        }
                        catch (IOException e) {
                            System.out.println("Could not perform Save to File\n");
                        }
                    }
                    else {
                        System.out.println("Cannot call Save before calling Make Live Request " + 
                        "or Load from File\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }

    /*
     * NAME: getTradePriceChangeInfo
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Get price change information about a user-specified trade pair
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void getTradePriceChangeInfo() {
        JSONObject priceChangeInfo = null;
        int selection;
        do {
            System.out.println("Trade Details Menu\n\n" +
            "1. Display\n" + 
            "2. Make Live Request\n" +
            "3. Load from File\n" + 
            "4. Save to File\n" + 
            "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Display"
                case 1:
                    if (priceChangeInfo != null) {
                        InteractiveDisplayer.displayPriceChangeInfo(priceChangeInfo);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    try {
                        prompt = "Base Asset Symbol: ";
                        String baseAsset = UserInterface.userInput(prompt);
    
                        prompt = "Quote Asset Symbol: ";
                        String quoteAsset = UserInterface.userInput(prompt);
                        System.out.println();
    
                        String tradeSymbol = baseAsset + quoteAsset;
                        tradeSymbol = tradeSymbol.toUpperCase();
    
                        String request = BASE + "ticker/24hr?symbol=" + tradeSymbol;
                        priceChangeInfo = JSONObjectIO.readFromURL(request);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Make Live Request\n");
                    }
                    break;
                // User specified "Load from File"
                case 3:
                    String filename;
                    try {
                        prompt = "Filename: ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        priceChangeInfo = JSONObjectIO.readFromFile(filename);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    catch (JSONException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    break;
                // User specified "Save to File"
                case 4:
                    if (priceChangeInfo != null) {
                        try {
                            prompt = "Filename (without .json extension): ";
                            filename = UserInterface.userInput(prompt);
                            filename += ".json";
                            System.out.println();
    
                            JSONObjectIO.writeToFile(priceChangeInfo, filename);
    
                            System.out.println("File saved as: " + filename + "\n");
                        }
                        catch (IOException e) {
                            System.out.println("Could not perform Save to File\n");
                        }   
                    }
                    else {
                        System.out.println("Cannot call Save to File before calling Make Live " + 
                            "Request or Load from File\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }

    /*
     * NAME: getAssetGraph
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Get graph of all assets
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void getAssetGraph() {
        JSONObject exchangeInfo = null;
        Graph assetGraph = null;
        Date retrievalDate = null;
        int selection;
        do {
            System.out.println("Trade Paths Menu\n\n" + 
                "1. Display\n" + 
                "2. Make Live Request\n" + 
                "3. Load from File\n" + 
                "4. Save to File\n" + 
                "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Display"
                case 1:
                    if (assetGraph != null && retrievalDate != null) {
                        prompt = "Base Asset Symbol: ";
                        String baseAsset = UserInterface.userInput(prompt);
                        baseAsset = baseAsset.toUpperCase();

                        prompt = "Quote Asset Symbol: ";
                        String quoteAsset = UserInterface.userInput(prompt);
                        quoteAsset = quoteAsset.toUpperCase();
                        System.out.println(); 

                        InteractiveDisplayer.displayTradePaths(assetGraph, retrievalDate, baseAsset, 
                            quoteAsset);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    try {
                        String request = BASE + "exchangeInfo";
                        exchangeInfo = JSONObjectIO.readFromURL(request);
                        assetGraph = InteractiveGrapher.makeAssetGraph(exchangeInfo);
                        retrievalDate = new Date(exchangeInfo.getLong("serverTime"));
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Make Live Request");
                    }
                    break;
                // User specified "Load from File"
                case 3:
                    String filename;
                    try {
                        prompt = "Filename: ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println(); 
                        exchangeInfo = JSONObjectIO.readFromFile(filename);
                        assetGraph = InteractiveGrapher.makeAssetGraph(exchangeInfo);
                        retrievalDate = new Date(exchangeInfo.getLong("serverTime"));
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    catch (JSONException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    break;
                // User specified "Save to file"
                case 4:
                    if (exchangeInfo != null) {
                        try {
                            prompt = "Filename (without .json extension): ";
                            filename = UserInterface.userInput(prompt);
                            System.out.println();

                            filename += ".json";

                            JSONObjectIO.writeToFile(exchangeInfo, filename);

                            System.out.println("File saved as: " + filename + "\n");
                        }
                        catch (IOException e) {
                            System.out.println("Could not perform Save to File\n");
                        }
                    }
                    else {
                        System.out.println("Cannot call Save to File before calling Make Live " + 
                            "Request or Load from File\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }

    /*
     * NAME: getAllTradePairs
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Get all trade pairs involving assets not excluded from analyses
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void getAllTradePairs(Set<String> excludedAssets) {
        JSONObject exchangeInfo = null;
        int selection;
        do {
            System.out.println("Asset Overview Menu\n\n" +
                "1. Display\n" + 
                "2. Make Live Request\n" + 
                "3. Load from File\n" + 
                "4. Save to File\n" + 
                "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Display"
                case 1:
                    if (exchangeInfo != null) {
                        InteractiveAndReportDisplayer.displayAllTradePairs(exchangeInfo, 
                            excludedAssets);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    try {
                        String request = BASE + "exchangeInfo";
                        exchangeInfo = JSONObjectIO.readFromURL(request);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Make Live Request");
                    }
                    break;
                // User specified "Load from File"
                case 3:
                    String filename;
                    try {
                        prompt = "Filename: ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();
                        
                        exchangeInfo = JSONObjectIO.readFromFile(filename);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    catch (JSONException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    break;
                // User specified "Save to File"
                case 4:
                    if (exchangeInfo != null) {
                        try {
                            prompt = "Filename (without .json extension): ";
                            filename = UserInterface.userInput(prompt);
                            System.out.println();
                            filename += ".json";

                            JSONObjectIO.writeToFile(exchangeInfo, filename);
                            System.out.println("File saved as: " + filename + "\n");
                        }
                        catch (IOException e) {
                            System.out.println("Could not perform Save to File\n");
                        }
                    }
                    else {
                        System.out.println("Cannot call Save to File before calling Make Live " + 
                            "Request or Load from File\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }

    /*
     * NAME: getRecentTrades
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Get recent trades for a user-specified trade pair
     * CREATION: 12/10/2020
     * LAST MODIFICATION: 12/10/2020
     */
    
    public static void getRecentTrades() {
        JSONArray recentTrades = null;
        int selection;
        do {
            System.out.println("Trade Overview Menu\n\n" + 
                "1. Display\n" + 
                "2. Make Live Request\n" + 
                "3. Load from File\n" + 
                "4. Save to File\n" + 
                "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_4, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Display"
                case 1:
                    if (recentTrades != null) {
                        InteractiveAndReportSorter.sortRecentTradesByPrice(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradePrices(recentTrades);

                        InteractiveAndReportSorter.sortRecentTradesByQty(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradeQtys(recentTrades);

                        InteractiveAndReportSorter.sortRecentTradesByQuote(recentTrades);
                        InteractiveAndReportDisplayer.displayRecentTradeQuotes(recentTrades);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    try {
                        prompt = "Base Asset Symbol: ";
                        String baseAsset = UserInterface.userInput(prompt);
                        prompt = "Quote Asset Symbol: ";
                        String quoteAsset = UserInterface.userInput(prompt);
                        System.out.println();
                        String pair = baseAsset + quoteAsset;
                        pair = pair.toUpperCase();
        
                        prompt = "Number of results to return: ";
                        int resultCount = 
                            UserInterface.userInput(RESULT_COUNT_MIN, RESULT_COUNT_MAX, prompt);
                        System.out.println(); 
        
                        String request = BASE + "trades?symbol=" + pair + "&limit=" + resultCount;
                        recentTrades = JSONArrayIO.readFromURL(request);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Make Live Request\n");
                    }
                    break;
                // User specified "Load from File"
                case 3:
                    String filename;
                    try {
                        prompt = "Filename: ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        recentTrades = JSONArrayIO.readFromFile(filename);
                    }
                    catch (IOException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    catch (JSONException e) {
                        System.out.println("Could not perform Load from File\n");
                    }
                    break;
                // User specified "Save to File"
                case 4:
                    if (recentTrades != null) {
                        try {
                            prompt = "Filename (without .json extension): ";
                            filename = UserInterface.userInput(prompt);
                            System.out.println();

                            filename += ".json";
                            JSONArrayIO.writeToFile(recentTrades, filename);

                            System.out.println("File saved as: " + filename + "\n");
                        }
                        catch (IOException e) {
                            System.out.println("Could not perform Save to File\n");
                        }
                    }
                    else {
                        System.out.println("Cannot call Save to File before calling Make Live " +
                            "Request or Load from File\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }
}
