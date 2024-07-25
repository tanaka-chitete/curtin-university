import java.io.*;

import org.json.*;

public class InteractiveAnalyser {
    private static final int EXIT = 0;
    private static final int OPTION_3 = 3;
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
        JSONArray pairs = null;
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
                    if (pairs != null) {
                        prompt = "Symbol: ";
                        String asset = UserInterface.userInput(prompt);
                        asset = asset.toUpperCase();
                        System.out.println();

                        InteractiveDisplayer.displayAssetTradePairs(pairs, asset);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    String request = BASE + "exchangeInfo";
                    JSONObject exchangeInfo = IOJSONObject.readFromURL(request);
                    pairs = exchangeInfo.getJSONArray("symbols");
                    break;
                // User specified "Load"
                case 3:
                    prompt = "Filename: ";
                    String filename = UserInterface.userInput(prompt);
                    pairs = IOJSONArray.readFromFile(filename);
                    break;
                // User specified "Save"
                case 4:
                    if (pairs != null) {
                        prompt = "Filename (without .json extension): ";
                        filename = UserInterface.userInput(prompt);
                        filename += ".json";
                        System.out.println();

                        IOJSONArray.writeToFile(pairs, filename);

                        System.out.println("File saved as: " + filename + "\n");
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
                    prompt = "Base Asset Symbol: ";
                    String baseAsset = UserInterface.userInput(prompt);

                    prompt = "Quote Asset Symbol: ";
                    String quoteAsset = UserInterface.userInput(prompt);
                    System.out.println();

                    String tradeSymbol = baseAsset + quoteAsset;
                    tradeSymbol = tradeSymbol.toUpperCase();

                    String request = BASE + "ticker/24hr?symbol=" + tradeSymbol;
                    priceChangeInfo = IOJSONObject.readFromURL(request);
                    break;
                // User specified "Load from File"
                case 3:
                    prompt = "Filename: ";
                    String filename = UserInterface.userInput(prompt);
                    priceChangeInfo = IOJSONObject.readFromFile(filename);
                    break;
                // User specified "Save to File"
                case 4:
                    if (priceChangeInfo != null) {
                        prompt = "Filename (without .json extension): ";
                        filename = UserInterface.userInput(prompt);
                        filename += ".json";
                        System.out.println();

                        IOJSONObject.writeToFile(priceChangeInfo, filename);

                        System.out.println("File saved as: " + filename + "\n");
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
        DSAGraph assetGraph = null;
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
                    if (assetGraph != null) {
                        prompt = "Base Asset Symbol: ";
                        String baseAsset = UserInterface.userInput(prompt);
                        baseAsset = baseAsset.toUpperCase();

                        prompt = "Quote Asset Symbol: ";
                        String quoteAsset = UserInterface.userInput(prompt);
                        quoteAsset = quoteAsset.toUpperCase();
                        System.out.println(); 

                        InteractiveDisplayer.displayTradePaths(assetGraph, baseAsset, quoteAsset);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    String request = BASE + "exchangeInfo";
                    JSONObject exchangeInfo = IOJSONObject.readFromURL(request);
                    assetGraph = InteractiveGrapher.makeAssetGraph(exchangeInfo);
                    break;
                // User specified "Load from File"
                case 3:
                    prompt = "Filename: ";
                    String filename = UserInterface.userInput(prompt);
                    System.out.println(); 
                    
                    assetGraph = IODSAGraph.read(filename);
                    break;
                // User specified "Save to file"
                case 4:
                    if (assetGraph != null) {
                        prompt = "Filename (without .txt extension): ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        filename += ".txt";

                        IODSAGraph.write(assetGraph, filename);

                        System.out.println("File saved as: " + filename + "\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }

    /*
     * NAME: changeExcludedAssets
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Set which assets to include or ignore in asset overviews
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void changeExcludedAssets(DSAHashSet excludedAssets) {
        int selection;
        do {
            System.out.println("Asset Filter Menu\n\n" +
                "1. Exclude\n" +
                "2. Include\n" +
                "3. Display\n" +
                "0. Exit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(EXIT, OPTION_3, prompt);
            System.out.println();

            switch (selection) {
                // User specified "Exclude Asset"
                case 1:
                    String assetToExclude = null;
                    try {
                        prompt = "Asset: ";
                        assetToExclude = UserInterface.userInput(prompt);
                        assetToExclude = assetToExclude.toUpperCase();
                        System.out.println();
    
                        excludedAssets.add(assetToExclude);
    
                        System.out.println("\"" + assetToExclude + "\" will be excluded from " + 
                            "future analyses\n");
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("\"" + assetToExclude + "\" had been excluded " + 
                            "previously\n");
                    }
                    break;
                // User specified "Include Asset"
                case 2:
                    String assetToInclude = null;
                    try {
                        prompt = "Asset: ";
                        assetToInclude = UserInterface.userInput(prompt);
                        assetToInclude = assetToInclude.toUpperCase();
                        System.out.println();
    
                        excludedAssets.remove(assetToInclude);
    
                        System.out.println("\"" + assetToInclude + "\" will be included in " + 
                            "future analyses\n");
                        break;
                    }
                    catch(IllegalStateException e) {
                        System.out.println("\"" + assetToInclude + "\" cannot be included if " + 
                            "there are no excluded assets\n");
                    }
                    catch(IllegalArgumentException e) {
                        System.out.println("\"" + assetToInclude + "\" had not been excluded " + 
                            "previously\n");
                    }
                    break;
                // User specified "Display Asset filter"
                case 3:
                    System.out.println("Excluded Assets: " + excludedAssets + "\n");
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

    public static void getAllTradePairs(DSAHashSet excludedAssets) {
        JSONArray pairs = null;
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
                    if (pairs != null) {
                        InteractiveAndReportDisplayer.displayAllTradePairs(pairs, excludedAssets);
                    }
                    else {
                        System.out.println("Cannot call Display before calling Make Live Request " + 
                            "or Load from File\n");
                    }
                    break;
                // User specified "Make Live Request"
                case 2:
                    String request = BASE + "exchangeInfo";
                    JSONObject exchangeInfo = IOJSONObject.readFromURL(request);
                    pairs = exchangeInfo.getJSONArray("symbols");
                    break;
                // User specified "Load from File"
                case 3:
                    prompt = "Filename: ";
                    String filename = UserInterface.userInput(prompt);
                    pairs = IOJSONArray.readFromFile(filename);

                    System.out.println("\n" + filename + " read\n");
                    break;
                // User specified "Save to File"
                case 4:
                    if (pairs != null) {
                        prompt = "Filename (without .json extension): ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        filename += ".json";
                        IOJSONArray.writeToFile(pairs, filename);
                        System.out.println("File saved as: " + filename + "\n");
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
                    recentTrades = IOJSONArray.readFromURL(request);
                    break;
                // User specified "Load from File"
                case 3:
                    prompt = "Filename: ";
                    String filename = UserInterface.userInput(prompt);
                    recentTrades = IOJSONArray.readFromFile(filename);

                    System.out.println("\n" + filename + " read\n");
                    break;
                // User specified "Save to File"
                case 4:
                    if (recentTrades != null) {
                        prompt = "Filename (without .json extension): ";
                        filename = UserInterface.userInput(prompt);
                        System.out.println();

                        filename += ".json";
                        IOJSONArray.writeToFile(recentTrades, filename);
                        System.out.println("File saved as: " + filename + "\n");
                    }
                    break;
            }
        }
        while (selection != EXIT);
    }
}
