"""
NAME: Quicksort.py
AUTHOR: Tanaka Chitete
PURPOSE: Sort list using Quicksort
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

"""
NAME: quicksort
IMPORT(S): array (list), low (int), high (int), key (str)
EXPORT(S): None
PURPOSE: Implements frontend of self-implemented Quicksort
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def quicksort(array, low, high, key):
    if low < high:
        # array[partitionIndex] is now at correct place
        partitionIndex = partition(array, low, high, key)

        # quicksort to the left of array[partitionIndex]
        quicksort(array, low, partitionIndex - 1, key)
        # quicksort to the right of array[partitionIndex]
        quicksort(array, partitionIndex + 1, high, key)

    return array


"""
NAME: partition
IMPORT(S): array (list), low (int), high (int), key (str)
EXPORT(S): None
PURPOSE: Take element (dict) of list and use list[...][key] as pivot, place last element at 
    its correct position in sorted list and place all elements where list[...][key] < pivot to 
    the left of last element and all elements where list[...][key] > pivot to the right of last 
    element
CREATION: 04/03/2021
LAST MODIFICATION: 04/03/2021
"""

def partition(array, low, high, key):
    # Element to be placed at correct position
    pivot = array[high]

    # Index of smaller element and indicates the correct position of pivot found so far
    i = (low - 1)

    for j in range(low, high):
        if array[j][key] < pivot[key]:
            i += 1
            # Swaps array[i] and array[j]
            temp = array[i]
            array[i] = array[j]
            array[j] = temp
    # Swaps array[i + 1] and array[high]
    temp = array[i + 1]
    array[i + 1] = array[high]
    array[high] = temp

    return i + 1