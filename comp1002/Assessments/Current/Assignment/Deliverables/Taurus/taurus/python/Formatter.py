"""
NAME: Formatter.py
AUTHOR: Tanaka Chitete
PURPOSE: Format various values
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

import datetime
import time

"""
NAME: formatTimeStamp
IMPORT(S): timeStamp (int)
EXPORT(S): formattedDate (str)
PURPOSE: Format timeStamp in date form
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def formatTimeStamp(timeStamp):
    # Division by 1000 converts timeStamp from milliseconds to seconds
    rawDate = datetime.datetime.fromtimestamp(timeStamp / 1_000)

    formattedDate = rawDate.strftime(f"%A, %d %B %Y at %X {time.tzname[1]}")

    return formattedDate


"""
NAME: formatReal
IMPORT(S): real (float)
EXPORT(S): formattedReal (str)
PURPOSE: Format real to six d.p's with commas
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def formatReal(real):
    formattedReal = f"{round(float(real), 6):,.6f}"

    return formattedReal


"""
NAME: formatPctChange
IMPORT(S): real (float)
EXPORT(S): formatPct (str)
PURPOSE: Format percentage to six d.p's with commas
CREATION: 04/03/2021
LAST MODIFICATION: 05/03/2021
"""

def formatPctChange(pctChange):
    formattedPctChange = f"{round(float(pctChange), 6):,.6f}"

    # Add '+' to positive as positives are unsigned
    if float(pctChange) > 0:
        formattedPctChange = f"+{formattedPctChange}"

    return formattedPctChange