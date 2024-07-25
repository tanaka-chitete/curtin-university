"""
NAME: UserInterface
AUTHOR: Tanaka Chitete
PURPOSE: Implement various submodules to get different types of user input
CREATION: 02/03/2021
LAST MODIFICATION: 02/03/2021
"""

"""
NAME: getInt
IMPORT(S): low (int), high (int), prompt (str)
EXPORT(S): userInput (int)
PURPOSE: get int input from user
CREATION: 02/03/2021
LAST MODIFICATION: 02/03/2021
"""

def getInt(low, high, prompt):
    numOptions = high - low
    if numOptions > 1:
        error = f"Input must be a whole number between {low} and {high} inclusive"
    else:
        error = f"Input must be either {low} or {high}"

    out = prompt
    userInput = None
    while True:
        try:
            userInput = int(input(out))
            if userInput < low or userInput > high:
                print(error)
            else:
                break
        except ValueError:
            print(error)
            
    return userInput

"""
NAME: getStr
IMPORT(S): prompt (str)
EXPORT(S): userInput (int)
PURPOSE: get str input from user
CREATION: 02/03/2021
LAST MODIFICATION: 02/03/2021
"""

def getStr(prompt):
    return str(input(prompt))