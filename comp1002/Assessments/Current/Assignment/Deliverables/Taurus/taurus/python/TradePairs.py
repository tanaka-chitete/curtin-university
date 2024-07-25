"""
NAME: TradePairs.py
AUTHOR: Tanaka Chitete
PURPOSE: Get and print all trade pairs involving crypto-currency
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

import JSON_IOWrapper
import Quicksort
import ExchangeInfoGetter
import UserInterface

"""
NAME: subMenu
IMPORT(S): None
EXPORT(S): None
PURPOSE: Print sub-menu and prepare to launch user-specified operation
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def subMenu():
    exchangeInfo = None
    while True:
        print("Get and Display All Trade Pairs Involving User-specified Crypto\n\n" + \
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
            printAllTradePairs(exchangeInfo)
        elif selection == 2:
            exchangeInfo = ExchangeInfoGetter.getExchangeInfoFromAPI()
        elif selection == 3:
            exchangeInfo = JSON_IOWrapper.readFromFileWrapper()
        elif selection == 4:
            JSON_IOWrapper.writeToFileWrapper(exchangeInfo)
        else:
            break


"""
NAME: printAllTradePairs
IMPORT(S): exchangeInfo (dict)
EXPORT(S): None
PURPOSE: Print all currently-trading trade pairs
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def printAllTradePairs(exchangeInfo):
    if exchangeInfo is None:
        print("Cannot display before making live request or loading from file\n")
    else:
        prompt = "Crypto-currency (case-insensitive): "
        crypto = UserInterface.getStr(prompt)
        crypto = crypto.upper()

        print() # Formatting purposes

        tradePairs = exchangeInfo["symbols"]
        sortedTradePairs = Quicksort.quicksort(tradePairs, 0, len(tradePairs) - 1, "baseAsset")

        print(f"Trade Pairs involving {crypto}\n")

        for pair in sortedTradePairs:
            # Ensures only active trade pairs are considered
            if pair["status"] == "TRADING": 
                # Prints trade pair if user-specified crypto-currency is either base or quote
                if crypto == pair["baseAsset"]:
                    print(pair["symbol"])
                elif crypto == pair["quoteAsset"]:
                    print(pair["symbol"])

        print() # Formatting purposes