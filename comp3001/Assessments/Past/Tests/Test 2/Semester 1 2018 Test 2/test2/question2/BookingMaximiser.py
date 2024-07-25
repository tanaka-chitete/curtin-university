def main():
    bookings = [
        [0, 5], 
        [3, 5], 
        [0, 6], 
        [5, 7], 
        [3, 8],
        [5, 9],
        [6, 10],
        [8, 11],
        [8, 12], 
        [0, 14], 
        [12, 14]
    ]

    print(maximiseBookings(bookings))


def maximiseBookings(bookings):
    maximisedBookings = list()
    maximisedBookings.append(bookings[0])
    j = 0
    for i in range(1, len(bookings)):
        if bookings[i][0] >= bookings[j][1]:
            maximisedBookings.append(bookings[i])
            j = i

    return maximisedBookings


if __name__ == "__main__":
    main()
