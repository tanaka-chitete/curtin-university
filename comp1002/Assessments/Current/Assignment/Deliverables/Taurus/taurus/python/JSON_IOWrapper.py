"""
NAME: JSON_IOWrapper.py
AUTHOR: Tanaka Chitete
PURPOSE: Implement wrappers for submodules in JSON_IO.py
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

import JSON_IO
import UserInterface

"""
NAME: readFromFileWrapper
IMPORT(S): None
EXPORT(S): JSONObject (object)
PURPOSE: Read "JSON Object" (e.g. dictionary or list) from file
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def readFromFileWrapper():
    try:
        prompt = "Filename: "
        filename = UserInterface.getStr(prompt)
        print() # Formatting purposes

        JSONObject = JSON_IO.readFromFile(filename)

        print("File in current directory loaded\n")

        return JSONObject
    except (OSError, KeyError, TypeError, ValueError):
        print("Failed to load from file\n")


"""
NAME: writeToFileWrapper
IMPORT(S): JSONObject (object)
EXPORT(S): None
PURPOSE: write "JSON Object" (e.g. dictionary or list) to file
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def writeToFileWrapper(marketInfo):
    if marketInfo is None:
        print("Cannot save to file before making live request or loading from file\n")
    else:
        try:
            prompt = "Filename (without extension): "
            filename = UserInterface.getStr(prompt)
            filename += ".json"
            print() # Formatting purposes

            JSON_IO.writeToFile(marketInfo, filename)

            print("File saved in current directory (with .json extension appended to filename)\n")
        except (OSError, KeyError, TypeError, ValueError):
            print("Failed to save to file\n")