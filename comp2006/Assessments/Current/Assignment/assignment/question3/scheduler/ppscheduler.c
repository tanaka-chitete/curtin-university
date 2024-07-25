#include <stdio.h>
#include <string.h>

#include "ppscheduler.h"
#include "bool.h"
#include "fileio.h"
#include "process.h"
#include "sharedmemory.h"

void* PpSchedulerThread(void* arg) {
    struct SharedMemory *memoryPtr = arg;

    int numProcesses;
    struct Process processes[100];
    /* double avgTurnaroundTime; */
    /* double avgWaitingTime; */
    int totalTurnaroundTime;
    int totalWaitingTime;
    int burstsRemaining[100];
    int isComplete[100];

    int currentTime;
    int numProcessesCompleted;

    int i;
    int index;
    int priority;
    int previousPid;

    int minArrivalTime;
    int maxCompletionTime;

    memset(isComplete, 0, sizeof(isComplete));
    currentTime = 0;
    numProcessesCompleted = 0;

    totalTurnaroundTime = 0;
    totalWaitingTime = 0;
    previousPid = -1;

    /* Block until filename is retrieved from user */
    pthread_mutex_lock(&memoryPtr->buffer1Lock);
    while (!memoryPtr->buffer1IsFull) {
        pthread_cond_wait(&memoryPtr->buffer1IsFullCond, &memoryPtr->buffer1Lock);
    }
    pthread_mutex_unlock(&memoryPtr->buffer1Lock);

    /* Begin execution */
    pthread_mutex_lock(&memoryPtr->executionLock);

    printf("PP\n");
    if (strcmp(memoryPtr->buffer1, "QUIT") == FALSE) {
        printf("terminated\n");
    }
    /* Compute results */
    else {
        numProcesses = read(memoryPtr->buffer1, processes, burstsRemaining);
        memoryPtr->numProcesses = numProcesses;
        if (numProcesses > 0) {
            while (numProcessesCompleted != numProcesses) {
                /* Denotes index of highest priority process */
                index = -1;            
                /* Denotes priority of highest priority process */
                priority = 2147483647; 
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

            memoryPtr->buffer2[0] = (double) totalTurnaroundTime / numProcesses;
            memoryPtr->buffer2[1] = (double) totalWaitingTime / numProcesses;
        }
    }
    
    memoryPtr->buffer2IsFull = TRUE;
    pthread_cond_signal(&memoryPtr->buffer2IsFullCond);
    pthread_mutex_unlock(&memoryPtr->executionLock);

    pthread_exit(NULL);
}
