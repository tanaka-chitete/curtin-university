#ifndef PROCESS_H
#define PROCESS_H
typedef struct Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority; /* A lower priority number indicates a higher priority */
    int startTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
} Process;
#endif
