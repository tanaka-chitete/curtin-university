"""
NAME: Graph
AUTHOR: Tanaka Chitete
PURPOSE: Implement Graph
CREATION: 01/03/2021
LAST MODIFICATION: 05/03/2021
"""

import LinkedList
import Queue
import Stack

class Graph:
    # NESTED CLASSES

    """
    NAME: Node
    AUTHOR: Tanaka Chitete
    PURPOSE: Implement a node for Graph
    CREATION: 01/03/2021 
    LAST MODIFICATION: 02/03/2021
    """

    class Node:
        # CONSTRUCTORS

        """
        CONSTRUCTOR
        IMPORT(S): element (str)
        EXPORT(S): Address of new Node
        PURPOSE: Make new Node in alternate state
        CREATION: 01/03/2021
        LAST MODIFICATION: 01/03/2021
        """

        def __init__(self, label):
            if label == None:
                raise ValueError("Cannot instantiate Node with None label")
            else:
                self.label = label
                self.neighbours = LinkedList.LinkedList()
                self.wasVisitedBool = False


        # SETTERS (MUTATORS)

        def addNeighbour(self, neighbour):
            self.neighbours.insertLast(neighbour)

        
        def wasVisited(self):
            return self.wasVisitedBool


    # CONSTRUCTORS

    """
    CONSTRUCTOR
    IMPORT(S): NONE
    EXPORT(S): Address of new Graph
    PURPOSE: Make new Graph
    CREATION: 01/03/2021
    LAST MODIFICATION: 01/03/2021
    """

    def __init__(self, isDirected=False):
        self.nodes = LinkedList.LinkedList()
        self.isDirected = isDirected
        self.numNodesInt = 0
        self.numConnectionsInt = 0


    # SETTERS (MUTATORS)

    def add(self, label):
        if label is None:
            raise ValueError("Cannot call add with None label")
        elif not isinstance(label, str):
            raise ValueError("Cannot call add with non-str label")
        elif self.has(label):
            raise ValueError("Cannot call add with label of existant node")
        else:
            newNode = self.Node(label)
            self.nodes.insertLast(newNode)
            self.numNodesInt += 1
    

    def connect(self, srcLabel, destLabel):
        # HELPERS

        def connectHelper(srcLabel, destLabel):
            src = self.get(srcLabel)
            dest = self.get(destLabel)

            src.addNeighbour(dest)
            if not self.isDirected:
                dest.addNeighbour(src)

            self.numConnectionsInt += 1

        # BODY

        if self.numNodesInt < 2:
            raise RuntimeError("Cannot call connect on Graph with less than two nodes")
        elif srcLabel is None or destLabel is None:
            raise ValueError("Cannot call connect with None label")
        elif not isinstance(srcLabel, str) or not isinstance(destLabel, str):
            raise ValueError("Cannot call add with non-str label")
        elif srcLabel == destLabel:
            raise ValueError("Cannot call connect with equal labels")
        elif not self.has(srcLabel) or not self.has(destLabel):
            raise ValueError("Cannot call connect with label of non-existant node")
        else:
            connectHelper(srcLabel, destLabel)
        

    # GETTERS (ACCESSORS)

    def get(self, label):
        # HELPERS

        def getHelper(label):
            n = None
            nodes = iter(self.nodes)
            while nodes.hasNext() and not n:
                curr = next(nodes)
                if curr.label == label:
                    n = curr

            return n

        # BODY

        if self.nodes.isEmpty():
            raise RuntimeError("Cannot call get on an empty graph")
        elif label is None:
            raise ValueError("Cannot call get with a None label")
        elif not isinstance(label, str):
            raise ValueError("Cannot call get with a non-str label")
        elif not self.has(label):
            raise ValueError("Cannot call get with label of non-existant node")
        else:
            return getHelper(label)


    def numNodes(self):
        return self.numNodesInt


    def numConnections(self):
        return self.numConnectionsInt


    # OPERATORS

    def has(self, label):
        # HELPERS

        def hasHelper(label):
            nodes = iter(self.nodes)
            has = False

            while nodes.hasNext() and not has:
                n = next(nodes)
                if n.label == label:
                    has = True
            
            return has

        # BODY

        if self.nodes.isEmpty():
            return False
        if label is None:
            raise ValueError("Cannot call has with a None label")
        elif not isinstance(label, str):
            raise ValueError("Cannot call has with a non-str label")
        else:
            return hasHelper(label)




    def areNeighbours(self, srcLabel, destLabel):
        # HELPERS

        def areNeighboursHelper(self, srcLabel, destLabel):
            areNeighbours = False
            nodes = iter(self.nodes)
            # Finds node with label srcLabel
            while nodes.hasNext() and not areNeighbours:
                node = next(nodes)
                # Upon finding node, searches n's neighbours for neighbour with label destLabel
                if node.label == srcLabel:
                    neighbours = iter(node.neighbours)
                    while neighbours.hasNext() and not areNeighbours:
                        neighbour =  next(neighbours)
                        # Upon finding neighbour, validates that node and neighbour are neighbours
                        if neighbour.label == destLabel:
                            areNeighbours = True

            return areNeighbours


        # BODY

        if self.numNodes < 2:
            raise RuntimeError("Cannot call areNeighbours on Graph with less than 2 nodes")
        elif srcLabel is None or destLabel is None:
            raise ValueError("Cannot call areNeighbours with a None label")
        elif not isinstance(srcLabel, str) or not isinstance(destLabel, str):
            raise ValueError("Cannot call areNeighbours with equal labels")
        else:
            return self.areNeighboursHelper(srcLabel, destLabel)


    def bfs(self):
        upcoming = Queue.Queue() 
        visited = Queue.Queue()

        start = self.nodes.peekFirst()

        # Prepares to visit startNode's neighbours
        upcoming.enqueue(start)
        # Prevents start from being visited again and adds it to visited
        start.wasVisitedBool = True
        visited.enqueue(start.label)

        while not upcoming.isEmpty():
            node = upcoming.dequeue()
            nodeNeighbours = iter(node.neighbours)
            # Visits node's neigbours
            while nodeNeighbours.hasNext():
                neighbour = next(nodeNeighbours)
                if not neighbour.wasVisited():
                    # Prepares to visit neighbour's neighbours
                    upcoming.enqueue(neighbour)
                    # Prevents neighbour from being visited again and adds it to visited
                    neighbour.wasVisitedBool = True
                    visited.enqueue(neighbour.label)
        
        # Allows nodes to be operated on again
        self.unvisit()

        return visited
    

    def dfs(self):
        # HELPERS

        def visit(self, node, visited):
            # Prevents node from being visited again and adds it to visited
            node.wasVisitedBool = True
            visited.enqueue(node.label)

            neighbours = iter(node.neighbours)
            while neighbours.hasNext():
                curr = next(neighbours)
                # Visits curr's neighbours
                if not curr.wasVisited():
                    self.visit(curr, visited)


        # BODY

        nodes = iter(self.nodes)
        visited = Queue.Queue()

        while nodes.hasNext():
            curr = next(nodes)
            # Visits node's neighbours
            if not curr.wasVisited():
                self.visit(curr, visited)
        
        # Allows nodes to be operated on again
        self.unvisit()

        return visited


    def printPaths(self, srcLabel, destLabel):
        # HELPERS

        def printPathsHelper(node, dest, path):
            # Adds p to paths and stops recursing
            if node.label == dest.label:
                print(path)
            else:
                # Prevents node from being visited again
                node.wasVisitedBool = True
                # Visits node's neighbours
                neighbours = iter(node.neighbours)
                while neighbours.hasNext():
                    curr = next(neighbours)
                    if not curr.wasVisited():
                        path.push(curr.label)
                        printPathsHelper(curr, dest, path)
                        path.pop()

                # Allows node to be visited again
                node.wasVisitedBool = False


        # BODY

        path = Stack.Stack()
        path.push(srcLabel)

        src = self.get(srcLabel)
        dest = self.get(destLabel)

        printPathsHelper(src, dest, path)

        # Allows nodes to be operated on again
        self.unvisit()


    # OTHER FUNCTIONS

    def unvisit(self):
        nodes = iter(self.nodes)
        while nodes.hasNext():
            curr = next(nodes)
            if curr.wasVisited():
                curr.wasVisitedBool = False