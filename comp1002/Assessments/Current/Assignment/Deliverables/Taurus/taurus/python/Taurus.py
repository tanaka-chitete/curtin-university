"""
NAME: Taurus
AUTHOR: Tanaka Chitete
PURPOSE: Implement entry point for Taurus crypto-currency data analysis suite
CREATION: 03/03/2021
LAST MODIFICATION: 05/03/2021
"""

import InteractiveEntry
import ReportEntry
import sys

def main():
    # User provided incorrect number of CLAs for either mode
    if len(sys.argv) != 2 and len(sys.argv) != 4:
        print("Usage (Interactive): python Taurus.py -i")
        print("or")
        print("Usage (Report): python Taurus.py -r <asset filename> <trade filename>")
    else:
        mode = sys.argv[1]
        # User wants to run program in Interactive mode
        if mode == "-i":
            if len(sys.argv) != 2:
                print("Usage (Interactive): python3 Taurus -i")
            else:
                print() # Formatting
                InteractiveEntry.menu()
        # User wants to run program in Report mode
        elif mode == "-r":
            assetFilename = sys.argv[2]
            tradeFilename = sys.argv[3]
            if len(sys.argv[1]) != 4:
                print("Usage (Report): python3 Taurus.py -r <asset filename> <trade filename>")
            else:
                print() # Formatting
                ReportEntry.entry(assetFilename, tradeFilename)
        # User hasn't specified that they want to run program in either Interactive or Report
        else:
            print("Usage (Interactive): python Taurus.py -i")
            print("or")
            print("Usage (Report): python Taurus.py -r <asset filename> <trade filename>")            


main()