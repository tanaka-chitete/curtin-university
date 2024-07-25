CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = ppscheduler.o fileio.o scan.o
EXEC = ppscheduler

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

ppscheduler.o : ppscheduler.c bool.h fileio.h process.h scan.h
	$(CC) -c ppscheduler.c $(CFLAGS)

fileio.o : fileio.c fileio.h process.h
	$(CC) -c fileio.c $(CFLAGS)

scan.o : scan.c
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
