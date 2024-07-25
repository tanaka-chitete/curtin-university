"""
NAME: Stack
AUTHOR: Tanaka Chitete
PURPOSE: Implement Stack
CREATION: 01/03/2021
LAST MODIFICATION: 02/03/2021
"""

from LinkedList import LinkedList

class Stack:
    # CONSTRUCTOR

    """
    CONSTRUCTOR
    IMPORT(S): NONE
    EXPORT(S): Address of new Stack
    PURPOSE: Make new Stack
    CREATION: 01/03/2021
    LAST MODIFICATION: 02/03/2021
    """

    def __init__(self):
        self.stack = LinkedList()


    # SETTERS (MUTATORS)

    def push(self, element):
        successful = False
        if element != None:
            self.stack.insertFirst(element)
            successful = True
        return successful
        
        
    # GETTERS (ACCESSORS)

    def peek(self):
        if self.isEmpty():
            raise RuntimeError("Cannot peek at an element from an empty stack")
        else:
            return self.stack.peekFirst()


    def pop(self):
        if self.isEmpty():
            raise RuntimeError("Cannot pop an element from an empty stack")
        else:
            return self.stack.removeFirst()


    # OPERATORS

    def __str__(self):
        return str(self.stack)


    def __len__(self):
        return len(self.stack)


    def __iter__(self):
        return iter(self.stack)


    def isEmpty(self):
        return self.stack.isEmpty()