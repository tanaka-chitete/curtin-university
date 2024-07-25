/* Adapted from CodoPhobia */
/* Preemptive priority scheduling program in operating system | Process Scheduling */
/* https://www.youtube.com/watch?v=VPDZuF_-vaY&t=1s&ab_channel=CodoPhobia */
/* Accessed 03/05/2021 */

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
