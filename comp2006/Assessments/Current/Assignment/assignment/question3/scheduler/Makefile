CC = gcc
CFLAGS = -Wall -Werror -ansi -pedantic -pthread
OBJ = scheduler.o ppscheduler.o srtfscheduler.o fileio.o scan.o
EXEC = scheduler

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC) -pthread

debug : CFLAGS += -g
debug : $(EXEC)

scheduler.o : scheduler.c bool.h ppscheduler.h results.h scan.h sharedmemory.h srtfscheduler.h
	$(CC) -c scheduler.c $(CFLAGS)

ppscheduler.o : ppscheduler.c ppscheduler.h bool.h fileio.h process.h sharedmemory.h
	$(CC) -c ppscheduler.c $(CFLAGS)

srtfscheduler.o : srtfscheduler.c srtfscheduler.h bool.h fileio.h process.h sharedmemory.h
	$(CC) -c srtfscheduler.c $(CFLAGS)

fileio.o : fileio.c fileio.h process.h
	$(CC) -c fileio.c $(CFLAGS)

scan.o : scan.c
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
