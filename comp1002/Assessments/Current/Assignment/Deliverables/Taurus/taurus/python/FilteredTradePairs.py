"""
NAME: FilteredTradePairs.py
AUTHOR: Tanaka Chitete
PURPOSE: Get and print crypto-filtered trade pairs
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

import ExchangeInfoGetter
import JSON_IOWrapper
import Quicksort
import UserInterface

"""
NAME: subMenu
IMPORT(S): assetFilter (Set)
EXPORT(S): None
PURPOSE: Print sub-menu and prepare to launch user-specified operation
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def subMenu(assetFilter):
    exchangeInfo = {}
    while True:
        print("Get and Display Crypto-Filtered Trade Pairs\n\n" + \
            "1. Display\n" + \
            "2. Make Live Request\n" + \
            "3. Load from File\n" + \
            "4. Save to File\n" + \
            "0. Exit\n"
        )
        prompt = "Selection: "
        selection = UserInterface.getInt(0, 4, prompt)

        print() # Formatting purposes

        if selection == 1:
            printFilteredTradePairs(exchangeInfo, assetFilter)
        elif selection == 2:
            exchangeInfo = ExchangeInfoGetter.getExchangeInfoFromAPI()
        elif selection == 3:
            exchangeInfo = JSON_IOWrapper.readFromFileWrapper()
        elif selection == 4:
            JSON_IOWrapper.writeToFileWrapper(exchangeInfo)
        else:
            break


"""
NAME: printFilteredTradePairs
IMPORT(S): exchangeInfo (dict), assetFilter (Set)
EXPORT(S): None
PURPOSE: Print crypto-filtered trade pairs
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def printFilteredTradePairs(exchangeInfo, assetFilter):
    if not exchangeInfo:
        print("Cannot display before making live request or loading from file")
    else:
        print(f"Crypto-Filtered Trade Pairs\n")

        tradePairs = exchangeInfo["symbols"]
        sortedTradePairs = Quicksort.quicksort(tradePairs, 0, len(tradePairs) - 1, "baseAsset")

        for pair in sortedTradePairs:
            # Ensures only active trade pairs are considered
            if pair["status"] == "TRADING":
                # Prints pair if neither base nor quote was added to asset filter
                if not assetFilter.has(pair["baseAsset"]) or not assetFilter.has(pair["quoteAsset"]):
                    print(pair["symbol"])
        
        print() # Formatting purposes

        if len(assetFilter) == 0:
            print("No cryptos have been added to crypto filter\n")
        else:
            print(f"Cryptos in crypto filter: {assetFilter}\n")