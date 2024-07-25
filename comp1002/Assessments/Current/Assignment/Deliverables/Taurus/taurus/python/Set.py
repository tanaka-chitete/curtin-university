"""
NAME: Set
AUTHOR: Tanaka Chitete
PURPOSE: Implement Set
CREATION: 02/03/2021
LAST MODIFICATION: 02/03/2021
"""

class Set:
    """
    CONSTRUCTOR
    IMPORT(S): NONE
    EXPORT(S): Address of new Set
    PURPOSE: Make new Set
    CREATION: 02/03/2021
    LAST MODIFICATION: 02/03/2021
    """

    def __init__(self, *args):
        self.entries = {}
        for a in args:
            self.add(a)


    # SETTERS (MUTATORS)

    def add(self, element):
        self.entries[element] = element


    # GETTERS (ACCESSORS)

    def has(self, element):
        return element in self.entries


    def remove(self, element):
        del self.entries[element]


    def __len__(self):
        return len(self.entries)


    def isEmpty(self):
        return not self.entries


    # OPERATORS

    def __str__(self):
        return str(list(self.entries.keys()))