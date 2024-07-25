CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = main.o fileio.o house.o houses.o linkedlist.o linkedlistnode.o scan.o
EXEC = houses

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

main.o : main.c fileio.h houses.h scan.h
	$(CC) -c main.c $(CFLAGS)

fileio.o : fileio.c fileio.h house.h linkedlist.h 
	$(CC) -c fileio.c $(CFLAGS)

house.o : house.c house.h
	$(CC) -c house.c $(CFLAGS)

houses.o : houses.c houses.h bool.h house.h linkedlist.h
	$(CC) -c houses.c $(CFLAGS)

linkedlist.o : linkedlist.c linkedlist.h 
	$(CC) -c linkedlist.c $(CFLAGS)

linkedlistnode.o : linkedlistnode.c linkedlist.h house.h 
	$(CC) -c linkedlistnode.c $(CFLAGS)

scan.o : scan.c
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)

