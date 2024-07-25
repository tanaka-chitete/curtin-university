def main():
    activities = [
        [0, 4], 
        [1, 4],
        [4, 6],
        [4, 7],
        [3, 8],
        [6, 8],
        [7, 9],
        [9, 11],
        [8, 12],
        [0, 13],
        [12, 14]
    ]

    print(maximiseActivities(activities))


def maximiseActivities(activities):
    maximisedActivities = list()
    maximisedActivities.append(activities[0])
    j = 0
    for i in range(1, len(activities)):
        if activities[i][0] >= activities[j][1]:
            maximisedActivities.append(activities[i])
            j = i

    return maximisedActivities


if __name__ == "__main__":
    main()
