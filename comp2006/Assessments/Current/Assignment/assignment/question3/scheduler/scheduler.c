#include <stdio.h> 
#include <pthread.h>
#include <string.h>

#include "bool.h"
#include "ppscheduler.h"
#include "results.h"
#include "scan.h"
#include "sharedmemory.h"
#include "srtfscheduler.h"

int main() {
    pthread_t PpSchedulerThreadId;
    pthread_t SrtfSchedulerThreadId;
    struct SharedMemory memory;
    struct Results results;

    do {
        /* Initialise variables */
        memory.numProcesses = 0;

        pthread_mutex_init(&memory.buffer1Lock, NULL);
        pthread_mutex_lock(&memory.buffer1Lock);
        pthread_cond_init(&memory.buffer1IsFullCond, NULL);
        memory.buffer1IsFull = FALSE;

        pthread_mutex_init(&memory.buffer2Lock, NULL);
        pthread_cond_init(&memory.buffer2IsFullCond, NULL);
        memory.buffer2IsFull = FALSE;

        pthread_mutex_init(&memory.executionLock, NULL);
        pthread_cond_init(&memory.PpSchedulerIsFinishedCond, NULL);
        memory.PpSchedulerIsFinished = FALSE;

        /* Create and run scheduler threads (they block until filename is received) */
        pthread_create(&PpSchedulerThreadId, NULL, PpSchedulerThread, (void*) &memory);
        pthread_create(&SrtfSchedulerThreadId, NULL, SrtfSchedulerThread, (void*) &memory);

        /* Retrieve user-specified filename, signal to threads to read file and begin execution */
        memory.buffer1 = scanString();
        memory.buffer1IsFull = TRUE;
        pthread_cond_signal(&memory.buffer1IsFullCond);
        pthread_mutex_unlock(&memory.buffer1Lock);

        /* Block until child one has finished executing */
        pthread_mutex_lock(&memory.executionLock);
        while (!memory.buffer2IsFull) {
            pthread_cond_wait(&memory.buffer2IsFullCond, &memory.executionLock);
        }        
        /* After child one has finished executing, get its results */
        if (memory.numProcesses > 0) {
            results.PpSchedulerAvgTurnaroundTime = memory.buffer2[0];
            results.PpSchedulerAvgWaitingTime = memory.buffer2[1];
        }

        /* Signal to child two to begin execution */
        memory.buffer2IsFull = FALSE;
        memory.PpSchedulerIsFinished = TRUE;
        pthread_cond_signal(&memory.PpSchedulerIsFinishedCond);
        pthread_mutex_unlock(&memory.executionLock);

        /* Block until child two has finished executing */
        pthread_mutex_lock(&memory.executionLock);
        while (!memory.buffer2IsFull) {
            pthread_cond_wait(&memory.buffer2IsFullCond, &memory.executionLock);
        }
        pthread_mutex_unlock(&memory.executionLock);
        /* After child two has finished executing, get its results */
        if (memory.numProcesses > 0) {
            results.SrtfSchedulerAvgTurnaroundTime = memory.buffer2[0];
            results.SrtfSchedulerAvgWaitingTime = memory.buffer2[1];
        }

        /* Print results from both threads */
        if (memory.numProcesses > 0) { 
            printf("PP:   ATT: %5.2f, AWT: %5.2f\n", 
                results.PpSchedulerAvgTurnaroundTime, 
                results.PpSchedulerAvgWaitingTime);
            printf("SRTF: ATT: %5.2f, AWT: %5.2f\n", 
                results.SrtfSchedulerAvgTurnaroundTime, 
                results.SrtfSchedulerAvgWaitingTime);
            printf("\n");
        }
    }
    while (strcmp(memory.buffer1, "QUIT") != FALSE);

    pthread_exit(NULL);

    return 0;
}
