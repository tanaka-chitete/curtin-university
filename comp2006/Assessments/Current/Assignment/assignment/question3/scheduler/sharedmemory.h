#include <pthread.h>

#ifndef SHARED_MEMORY_H
#define SHARED_MEMORY_H
typedef struct SharedMemory {
    int             numProcesses;
    const char*     buffer1;    /* Stores filename */
    double          buffer2[2]; /* Stores average turnaround time and average waiting time */
    pthread_mutex_t buffer1Lock;
    pthread_mutex_t buffer2Lock;
    pthread_mutex_t executionLock;
    int             buffer1IsFull;
    int             buffer2IsFull;
    int             PpSchedulerIsFinished;
    pthread_cond_t  buffer1IsFullCond;
    pthread_cond_t  buffer2IsFullCond;
    pthread_cond_t  PpSchedulerIsFinishedCond;
} SharedMemory;
#endif
