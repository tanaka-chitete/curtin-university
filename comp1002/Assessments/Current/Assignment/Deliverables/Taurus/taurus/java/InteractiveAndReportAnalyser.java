public class InteractiveAndReportAnalyser {
    private static final int EXIT = 0;
    private static final int OPTION_3 = 3;

    /*
     * NAME: changeExcludedAssets
     * IMPORT(S): NONE
     * EXPORT(S): NONE
     * PURPOSE: Set which assets to include or ignore in asset overviews
     * CREATION: 03/10/2020
     * LAST MODIFICATION: 01/11/2020
     */

    public static void changeExcludedAssets(Set<String> excludedAssets) {
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
}
