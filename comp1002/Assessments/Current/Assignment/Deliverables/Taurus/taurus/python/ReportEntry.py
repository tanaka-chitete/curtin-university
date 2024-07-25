"""
NAME: ReportEntry
AUTHOR: Tanaka Chitete
PURPOSE: Act and entry point for Taurus (Report Mode)
CREATION: 03/03/2021
LAST MODIFICATION: 05/03/2021
"""

import JSON_IO
import RecentTrades
import Set
import UserInterface

"""
NAME: menu
IMPORT(S): assetFilename (str), tradeFilename (str)
EXPORT(S): None
PURPOSE: Print menu and prepare to launch user-specified operation
CREATION: 03/03/2021
LAST MODIFICATION: 05/03/2021
"""

def menu(assetFilename, tradeFilename):
    QUIT = 0
    OPTION_3 = 3

    exchangeInfo = None
    try:
        exchangeInfo = JSON_IO.readFromFile(assetFilename)
    except:
        print(f"Failed to load {assetFilename}")
    
    recentTrades = None
    try:
        exchangeInfo = JSON_IO.readFromFile(tradeFilename)
    except:
        print(f"Failed to load {tradeFilename}")
    
    if exchangeInfo and recentTrades:
        cryptoFilter = Set()
        userInput = None
        while True:
            print("Taurus (Report Mode)\n\n" + \
                "1. Display Recent Trades (sorted by price, quantity and quote)\n" + \
                "2. Display Asset Filtered Trade Pairs\n" + \
                "3. Configure Asset Filter\n" + \
                "0. Quit\n"
            )
            prompt = "Selection: "
            userInput = UserInterface.getInt(QUIT, OPTION_3, prompt)
            print() # Formatting purposes

            if userInput == QUIT:
                break
            else:
                launch(userInput, cryptoFilter)
    else:
        print("Cannot execute operations with invalid/non-existent asset and/or trade file")


"""
NAME: launch
IMPORT(S): userInput (int), cryptoFilter (Set), exchangeInfo (dict), recentTrades (dict)
EXPORT(S): None
PURPOSE: Launch user-specified operation
CREATION: 03/03/2021
LAST MODIFICATION: 04/03/2021
"""

def launch(userInput, cryptoFilter, exchangeInfo, recentTrades):
    if userInput == 1:
        RecentTrades.printRecentTrades(recentTrades)
    elif userInput == 2:
        FilteredTradePairs.printFiltered(exchangeInfo, cryptoFilter)
    elif userInput == 3:
        AssetFilter.subMenu(cryptoFilter)