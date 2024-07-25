CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = numbers.o orders.o scan.o
EXEC = numbers

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

numbers.o : numbers.c numbers.h orders.h scan.h
	$(CC) -c numbers.c $(CFLAGS)

orders.o : orders.h
	$(CC) -c orders.c $(CFLAGS)

scan.o : scan.h
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ) 
