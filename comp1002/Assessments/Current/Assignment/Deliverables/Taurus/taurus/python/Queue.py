"""
NAME: Queue
AUTHOR: Tanaka Chitete
PURPOSE: Implement Queue
CREATION: 01/03/2021
LAST MODIFICATION: 02/03/2021
"""

from LinkedList import LinkedList

class Queue:
    # CONSTRUCTOR

    """
    CONSTRUCTOR
    IMPORT(S): NONE
    EXPORT(S): Address of new Queue
    PURPOSE: Make new Queue
    CREATION: 01/03/2021
    LAST MODIFICATION: 02/03/2021
    """

    def __init__(self):
        self.queue = LinkedList()


    # SETTERS (MUTATORS)

    def enqueue(self, element):
        successful = False
        if element != None:
            self.queue.insertLast(element)
            successful = True
        return successful
        

    # GETTERS (ACCESSORS)

    def peek(self):
        if self.isEmpty():
            raise RuntimeError("Cannot peek at an element from an empty queue")
        else:
            return self.queue.peekFirst()


    def dequeue(self):
        if self.isEmpty():
            raise RuntimeError("Cannot pop an element from an empty queue")
        else:
            return self.queue.removeFirst()


    # OPERATORS

    def __str__(self):
        return str(self.queue)


    def __len__(self):
        return len(self.queue)


    def __iter__(self):
        return iter(self.queue)


    def isEmpty(self):
        return self.queue.isEmpty()