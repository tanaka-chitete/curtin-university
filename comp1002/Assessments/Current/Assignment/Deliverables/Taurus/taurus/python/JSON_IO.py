"""
NAME: JSON_IO
AUTHOR: Tanaka Chitete
PURPOSE: Read and write JSON objects obtained from either APIs or files
CREATION: 03/03/2021
LAST MODIFICATION: 05/03/2021
"""

import json
import requests

"""
NAME: readFromUrl
IMPORT(S): Url (str)
EXPORT(S): JSONObject (object)
PURPOSE: Read and parse JSON Object obtained from API
CREATION: 03/03/2021
LAST MODIFICATION:
"""

def readFromUrl(Url):
    # Contacts API
    try:
        response = requests.get(Url)
        response.raise_for_status()
    except requests.RequestException:
        raise # Raises caught exception to calling function

    # Parses response
    try:
        JSONObject = response.json()
        return JSONObject
    except (KeyError, TypeError, ValueError):
        raise # Raises caught exception to calling function


"""
NAME: readFromFile
IMPORT(S): filename (str)
EXPORT(S): JSONObject (object)
PURPOSE: Read and parse "JSON Object" (e.g. dictionary or list) obtained from file
CREATION: 03/03/2021
LAST MODIFICATION: 03/03/2021
"""

def readFromFile(filename):
    try:
        with open(filename, "r") as jsonFile:
            JSONObject = json.load(jsonFile)
        return JSONObject
    except (OSError, KeyError, TypeError, ValueError) as e:
        raise # Raises caught exception to calling function


"""
NAME: writeToFile
IMPORT(S): JSONObject (object), filename (str)
EXPORT(S): None
PURPOSE: Write "JSON Object" (e.g. dictionary or list) to file
CREATION: 03/03/2021
LAST MODIFICATION: 03/03/2021
"""

def writeToFile(JSONObject, filename):
    try:
        with open(filename, "w", encoding="utf-8") as jsonFile:
            json.dump(JSONObject, jsonFile, ensure_ascii=False, indent=4, sort_keys=True)
    except (OSError, KeyError, TypeError, ValueError) as e:
        print(e)
        raise # Raises caught exception to calling function