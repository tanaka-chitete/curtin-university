#include <stdio.h>
#include <string.h>

#include "bool.h"
#include "fileio.h"
#include "process.h"
#include "scan.h"

int main() {
    int numProcesses;
    struct Process processes[100];
    double avgTurnaroundTime;
    double avgWaitingTime;
    int totalTurnaroundTime;
    int totalWaitingTime;
    int burstsRemaining[100];
    int isComplete[100];

    const char* input;

    int currentTime;
    int numProcessesCompleted;

    int i;
    int index;
    int priority;
    int previousPid;

    int minArrivalTime;
    int maxCompletionTime;

    do {
	input = NULL;
        memset(isComplete, 0, sizeof(isComplete));
        currentTime = 0;
        numProcessesCompleted = 0;

        totalTurnaroundTime = 0;
        totalWaitingTime = 0;
        previousPid = -1;

        /* Read input file */
        input = scanString();
        if (strcmp(input, "QUIT") != FALSE) {
            numProcesses = read(input, processes, burstsRemaining);
            if (numProcesses > 0) {
                while (numProcessesCompleted != numProcesses) {
                    index = -1;            /* Denotes index of highest priority process */
                    priority = 2147483647; /* Denotes priority of highest priority process */
                    for (i = 0; i < numProcesses; i++) {
                        /* Only consider processes in the ready queue (i.e. Haven't been completed) */
                        if (processes[i].arrivalTime <= currentTime && isComplete[i] == FALSE) {
                            if (processes[i].priority <= priority) {
                                priority = processes[i].priority;
                                index = i;
                            }
                            if (processes[i].priority == priority) {
                                if (processes[i].arrivalTime < processes[index].arrivalTime) {
                                    priority = processes[i].priority;
                                    index = i;
                                }
                            }
                        }
                    }

                    if (index != -1) {
                        if (burstsRemaining[index] == processes[index].burstTime) {
                            processes[index].startTime = currentTime;
                        }
                        burstsRemaining[index]--;
                        currentTime++;

                        if (processes[index].pid != previousPid) {
                            printf("P%-3d", processes[index].pid);
                            previousPid = processes[index].pid;
                        }
                        else {
                            printf("    ");
                        }

                        if (burstsRemaining[index] == 0) {
                            processes[index].completionTime = currentTime;
                            processes[index].turnaroundTime = processes[index].completionTime - processes[index].arrivalTime;
                            processes[index].waitingTime = processes[index].turnaroundTime - processes[index].burstTime;

                            totalTurnaroundTime += processes[index].turnaroundTime;
                            totalWaitingTime += processes[index].waitingTime;

                            isComplete[index] = TRUE;
                            numProcessesCompleted++;
                        }
                    }
                    else {
                        currentTime++;
                    }
                }
                
                printf("\n"); /* Formatting-purposes */
                for (i = 0; i < currentTime + 1; i++) {
                    printf("%-4d", i);
                }
                printf("\n"); /* Formatting-purposes */

                minArrivalTime = 2147483647;
                maxCompletionTime = -1;
                for (i = 0; i < numProcessesCompleted; i++) {
                    if (processes[i].arrivalTime < minArrivalTime) {
                        minArrivalTime = processes[i].arrivalTime;
                    }

                    if (processes[i].completionTime > maxCompletionTime) {
                        maxCompletionTime = processes[i].completionTime;
                    }
                }

                avgTurnaroundTime = (double) totalTurnaroundTime / numProcesses;
                avgWaitingTime = (double) totalWaitingTime / numProcesses;
                printf("ATT: %.2f, AWT: %.2f\n\n", avgTurnaroundTime, avgWaitingTime);
            }
        }
    }
    while (strcmp(input, "QUIT") != FALSE);

    return 0;
}
