"""
NAME: RecentTradesHelper.py
AUTHOR: Tanaka Chitete
PURPOSE: House makeCryptoGraph function for TradePaths.py
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

import Graph

"""
NAME: makeCryptoGraph
IMPORT(S): exchangeInfo (dict)
EXPORT(S): None
PURPOSE: Using trade pairs obtained from exchangeInfo, make graph mapping crypto-currencies
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def makeCryptoGraph(exchangeInfo):
    tradePairs = exchangeInfo["symbols"]
    cryptoGraph = Graph.Graph(True) # True indicates graph is directed

    # Adds base and quote of pair to cryptoGraph if they haven't already been added
    for pair in tradePairs:
        base = pair["baseAsset"]
        quote = pair["quoteAsset"]

        hasBase = cryptoGraph.has(base)
        hasQuote = cryptoGraph.has(quote)

        if not hasBase:
            cryptoGraph.add(base)
            hasBase = True
        if not hasQuote:
            cryptoGraph.add(quote)
            hasQuote = True

        if hasBase and hasQuote:
            cryptoGraph.connect(base, quote)

    return cryptoGraph