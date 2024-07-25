"""
NAME: LinkedList
AUTHOR: Tanaka Chitete
PURPOSE: Implement Linked List
CREATION: 27/02/2021
LAST MODIFICATION: 02/03/2021
"""

class LinkedList:
    # NESTED CLASSES

    """
    NAME: LinkedListNode
    AUTHOR: Tanaka Chitete
    PURPOSE: Implement node for Linked List
    CREATION: 27/02/2021 
    LAST MODIFICATION: 02/03/2021
    """

    class LinkedListNode:
        """
        CONSTRUCTOR
        IMPORT(S): element (Object)
        EXPORT(S): Address of new LinkedListNode
        PURPOSE: Make new LinkedListNode
        CREATION: 27/02/2021
        LAST MODIFICATION: 02/03/2021
        """

        def __init__(self, element):
            self.element = element
            self.previous = None
            self.next = None


    """
    NAME: Iterator
    AUTHOR: Tanaka Chitete
    PURPOSE: Implement iterator for Linked List
    CREATION: 01/03/2021 
    LAST MODIFICATION: 2/03/2021
    """

    class Iterator:
        """
        CONSTRUCTOR
        IMPORT(S): linkedList (LinkedList)
        EXPORT(S): Address of new Iterator
        PURPOSE: Make new Iterator
        CREATION: 01/03/2021
        LAST MODIFICATION: 02/03/2021
        """

        def __init__(self, linkedList):
            self.next = linkedList.first


        def hasNext(self):
            return not self.next is None


        def __next__(self):
            element = None
            if self.next is not None:
                element = self.next.element
                self.next = self.next.next
            return element


    """
    CONSTRUCTOR
    IMPORT(S): NONE
    EXPORT(S): Address of new LinkedList
    PURPOSE: Make new LinkedList
    CREATION: 28/02/2021
    LAST MODIFICATION: 02/03/2021
    """

    def __init__(self):
        self.first = None
        self.last = None
        self.count = 0


    # SETTERS (MUTATORS)

    def insertFirst(self, newFirstElement):
        newFirst = self.LinkedListNode(newFirstElement)

        if self.isEmpty():
            self.first = newFirst
            self.last = newFirst
        else:
            newFirst.next = self.first
            self.first = newFirst
        self.count += 1


    def insertLast(self, newLastElement):
        newLast = self.LinkedListNode(newLastElement)

        if self.isEmpty():
            self.last = newLast
            self.first = newLast
        else:
            self.last.next = newLast
            self.last = newLast
        self.count += 1


    # GETTERS (ACCESSORS)

    def peekFirst(self):
        if self.isEmpty():
            raise ValueError("Cannot peek at the first node of an empty linked list")
        else:
            return self.first.element


    def peekLast(self):
        if self.isEmpty():
            raise ValueError("Cannot peek at the last node of an empty linked list")
        else:
            return self.last.element


    def removeFirst(self):
        if self.isEmpty():
            raise ValueError("Cannot remove the first node of an empty linked list")
        else:
            removedFirstElement = self.first.element
            self.first = self.first.next
            self.count -= 1
            return removedFirstElement


    def removeLast(self):
        if self.isEmpty():
            raise ValueError("Cannot remove the last node of an empty linked list")
        elif self.first.next == None:
            removedLastElement = self.first.element
            self.first = None
            self.count -= 1
            return removedLastElement
        else:
            removedLastElement = self.last.element
            self.last = self.last.previous
            self.last.next = None
            self.count -= 1
            return removedLastElement


    # OPERATORS

    def __str__(self):
        nodes = iter(self)
        string = "["
        while nodes.hasNext():
            n = next(nodes)
            # string += f"{str(n)}, "
            string += f"{str(n)} -> " 
        # string = string[:-2] # Removes last ", "
        string = string[:-4] # Removes last " -> "
        string += "]"

        return string


    def __len__(self):
        return self.count


    def __iter__(self):
        return self.Iterator(self)


    def isEmpty(self):
        return self.first is None