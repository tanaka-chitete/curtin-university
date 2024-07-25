"""
NAME: TradePaths.py
AUTHOR: Tanaka Chitete
PURPOSE: Get and print all trade paths between two given assets
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

import ExchangeInfoGetter
import JSON_IOWrapper
import TradePathsHelper
import UserInterface

"""
NAME: subMenu
IMPORT(S): None
EXPORT(S): None
PURPOSE: Print sub-menu and prepare to launch user-specified operation
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def subMenu():
    exchangeInfo = {}
    while True:
        print("Get and Display All Trade Paths Between Two User-specified Cryptos\n\n" + \
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
            printTradePaths(exchangeInfo)
        elif selection == 2:
            exchangeInfo = ExchangeInfoGetter.getExchangeInfoFromAPI()
        elif selection == 3:
            exchangeInfo = JSON_IOWrapper.readFromFileWrapper()
        elif selection == 4:
            JSON_IOWrapper.writeToFileWrapper(exchangeInfo)
        else:
            break


"""
NAME: printTradePaths
IMPORT(S): exchangeInfo (dict)
EXPORT(S): None
PURPOSE: Using cryptoGraph, print all trade paths between base and quote cryptos
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def printTradePaths(exchangeInfo):
    if not exchangeInfo:
        print("Cannot display before making live request or loading from file\n")
    else:
        prompt = "Start crypto-currency (case-insensitive): "
        start = UserInterface.getStr(prompt).upper()

        prompt = "Destination crypto-currency (case-insensitive): "
        dest = UserInterface.getStr(prompt).upper()

        print() # Formatting purposes

        print(f"All Trade Paths from {start} to {dest}\n")
        cryptoGraph = TradePathsHelper.makeCryptoGraph(exchangeInfo)
        cryptoGraph.printPaths(start, dest)

        print() # Formatting purposes