"""
NAME: MarketInfo.py
AUTHOR: Tanaka Chitete
PURPOSE: Get and print market info of user-specified trade pair
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

import datetime
import Formatter
import JSON_IO
import JSON_IOWrapper
import requests
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
    marketInfo = {}
    while True:
        print("Get and Display Market Information of Given Trade Pair\n\n" + \
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
            printMarketInfo(marketInfo)
        elif selection == 2:
            marketInfo = getMarketInfoFromAPI()
        elif selection == 3:
            marketInfo = JSON_IOWrapper.readFromFileWrapper()
        elif selection == 4:
            JSON_IOWrapper.writeToFileWrapper(marketInfo)
        else:
            break


"""
NAME: printMarketInfo
IMPORT(S): marketInfo (dict)
EXPORT(S): None
PURPOSE: Print market info
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def printMarketInfo(marketInfo):
    if not marketInfo:
        print("Cannot display before making live request or loading from a file\n")
    else:
        print(f"{marketInfo['symbol']} Market Information\n")

        print(f"Latest Price (USD):        {Formatter.formatReal(marketInfo['lastPrice'])}")
        print(f"Price Change (USD):       {Formatter.formatPctChange(marketInfo['priceChange'])}")
        print(f"Price Change (%):         {Formatter.formatPctChange(marketInfo['priceChangePercent'])}\n")

        print(f"Open Time (system time):   {Formatter.formatTimeStamp(marketInfo['openTime'])}")
        print(f"Close Time (system time):  {Formatter.formatTimeStamp(marketInfo['closeTime'])}")

        print() # Formatting purposes      


"""
NAME: getMarketInfoFromAPI
IMPORT(S): None
EXPORT(S): marketInfo (dict)
PURPOSE: Get market info from Binance API
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def getMarketInfoFromAPI():
    try:
        prompt = "Base crypto-currency (case-insensitive): "
        base = UserInterface.getStr(prompt)
        prompt = "Quote crypto-currency (case-insensitive): "
        quote = UserInterface.getStr(prompt)

        print() # Formatting purposes

        tradePair = base + quote
        tradePair = tradePair.upper()

        request = f"https://api.binance.com/api/v3/ticker/24hr?symbol={tradePair}"
        marketInfo = JSON_IO.readFromUrl(request) 

        return marketInfo
    except (KeyError, TypeError, ValueError, requests.RequestException):
        print("Failed to make live request\n")