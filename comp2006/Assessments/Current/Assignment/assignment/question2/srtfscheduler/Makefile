CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = srtfscheduler.o fileio.o scan.o
EXEC = srtfscheduler

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

srtfscheduler.o : srtfscheduler.c bool.h fileio.h process.h scan.h
	$(CC) -c srtfscheduler.c $(CFLAGS)

fileio.o : fileio.c fileio.h process.h
	$(CC) -c fileio.c $(CFLAGS)

scan.o : scan.c
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
