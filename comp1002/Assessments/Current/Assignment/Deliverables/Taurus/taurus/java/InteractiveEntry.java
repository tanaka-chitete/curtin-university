public class InteractiveEntry {
    public static final int QUIT = 0;
    public static final int OPTION_6 = 6;

    /*
     * NAME: entry
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Provide entry point for Interactive operations
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 07/10/2020
     */

    public static void entry() {
        Set<String> excludedAssets = new Set<String>();
        int selection;
        do {
            System.out.println("Interactive Menu\n\n" + 
                "1. Asset Details\n" + 
                "2. Trade Details\n" +
                "3. Trade Paths\n" +
                "4. Asset Filter\n" +
                "5. Asset Overview\n" +
                "6. Trade Overview\n" +
                "0. Quit\n");
            String prompt = "Selection: ";
            selection = UserInterface.userInput(QUIT, OPTION_6, prompt);
            System.out.println();

            // Launches user-specifed operation
            if (selection != QUIT) {
                launch(selection, excludedAssets);
            }
        }
        while (selection != QUIT);
    }

    /*
     * NAME: launch
     * IMPORT(S): selection (int)
     * EXPORT(S): NONE
     * PURPOSE: Launch Interactive operations
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 07/10/2020
    */

    private static void launch(int selection, Set<String> excludedAssets) {
        switch (selection) {
            // User specified "Display Asset"
            case 1:
                InteractiveAnalyser.getAssetTradePairs();
                break;
            // User specified "Display Trade Details"
            case 2:
                InteractiveAnalyser.getTradePriceChangeInfo();
                break;
            // User specified "Display Trade Paths"
            case 3:
                InteractiveAnalyser.getAssetGraph();
                break;
            // User specified "Asset Filter"
            case 4:
                InteractiveAndReportAnalyser.changeExcludedAssets(excludedAssets); 
                break;
            // User specified "Asset Overview"
            case 5:
                InteractiveAnalyser.getAllTradePairs(excludedAssets);
                break;
            // User specified "Trade Overview"
            case 6:
                InteractiveAnalyser.getRecentTrades();
                break;
        }
    }    
}
