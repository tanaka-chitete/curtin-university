"""
NAME: CryptoFilter.py
AUTHOR: Tanaka Chitete
PURPOSE: Configure crypto-currency filter
CREATION: 05/03/2021
LAST MODIFICATION: 05/03/2021
"""

import UserInterface

"""
NAME: subMenu
IMPORT(S): cryptoFilter (Set)
EXPORT(S): None
PURPOSE: Print sub-menu and prepare to launch user-specified operation
CREATION: 05/03/2021
LAST MODIFICATION: 05/03/2021
"""

def subMenu(cryptoFilter):
    while True:
        print("Configure Crypto Filter\n\n" + \
            "1. Display\n" + \
            "2. Add Crypto to Crypto Filter (to have it be excluded from future analyses)\n" + \
            "3. Remove Crypto from Crypto Filter (to have it be included in future analyses)\n" + \
            "0. Exit\n"
        )
        prompt = "Selection: "
        selection = UserInterface.getInt(0, 4, prompt)

        print() # Formatting purposes

        if selection == 1:
            printCryptosInCryptoFilter(cryptoFilter)
        elif selection == 2:
            addCryptoToCryptoFilter(cryptoFilter)
        elif selection == 3:
            removeCryptoFromCryptoFilter(cryptoFilter)
        else:
            break


"""
NAME: printCryptosInCryptoFilter
IMPORT(S): cryptoFilter (Set)
EXPORT(S): None
PURPOSE: Print cryptos in crypto filter (those which are to be excluded from future analyses)
CREATION: 05/03/2021
LAST MODIFICATION: 05/03/2021
"""

def printCryptosInCryptoFilter(cryptoFilter):
    if len(cryptoFilter) == 0:
        print("No cryptos have been added to crypto filter")
    else:
        print(f"Excluded cryptos: {cryptoFilter}")

    print() # Formatting purposes 


"""
NAME: addCryptoToCryptoFilter
IMPORT(S): cryptoFilter (Set)
EXPORT(S): None
PURPOSE: Add crypto to cryptoFilter (to exclude it from future analyses)
CREATION: 05/03/2021
LAST MODIFICATION: 05/03/2021
"""

def addCryptoToCryptoFilter(cryptoFilter):
    prompt = "Crypto to add to crypto filter (case-insensitive): "
    cryptoToExclude = UserInterface.getStr(prompt).upper()

    print() # Formatting purposes

    if cryptoFilter.has(cryptoToExclude):
        print("Crypto has already been added to crypto filter")
    else:
        cryptoFilter.add(cryptoToExclude)
        print("Crypto has been added to crypto filter (and will be excluded from future analyses)")
    
    print() # Formatting purposes


"""
NAME: removeCryptoFromCryptoFilter
IMPORT(S): cryptoFilter (Set)
EXPORT(S): None
PURPOSE: Remove crypto from cryptoFilter (to include it in future analyses)
CREATION: 05/03/2021
LAST MODIFICATION: 05/03/2021
"""

def removeCryptoFromCryptoFilter(cryptoFilter):
    prompt = "Crypto to remove from crypto filter (case-insensitive): "
    cryptoToInclude = UserInterface.getStr(prompt).upper()

    print() # Formatting purposes

    if not cryptoFilter.has(cryptoToInclude):
        print("Crypto was never added to crypto filter")
    else:
        cryptoFilter.remove(cryptoToInclude)
        print("Crypto has been removed to crypto filter (and will be included in future analyses)")
    
    print() # Formatting purposes