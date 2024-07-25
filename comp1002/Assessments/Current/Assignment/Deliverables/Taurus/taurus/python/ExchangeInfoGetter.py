"""
NAME: ExchangeInfoGetter.py
AUTHOR: Tanaka Chitete
PURPOSE: Get exchange info from Binance API
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

import JSON_IO

"""
NAME: getExchangeInfoFromAPI
IMPORT(S): None
EXPORT(S): exchangeInfo (dict)
PURPOSE: Get exchange info from Binance API
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def getExchangeInfoFromAPI():
    try:
        request = "https://api.binance.com/api/v3/exchangeInfo"
        exchangeInfo = JSON_IO.readFromUrl(request)

        return exchangeInfo
    except (KeyError, TypeError, ValueError):
        print("Failed to make live request")