CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = main.o enemy.o fileio.o hero.o linkedlist.o linkedlistnode.o newsleep.o rpgvisualiser.o stack.o
EXEC = rpg

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

main.o : main.c fileio.h hero.h linkedlist.h newsleep.h rpgvisualiser.h 
	$(CC) -c main.c $(CFLAGS)

enemy.o : enemy.c enemy.h
	$(CC) -c enemy.c $(CFLAGS)

fileio.o : fileio.c fileio.h enemy.h hero.h stack.h 
	$(CC) -c fileio.c $(CFLAGS)

hero.o : hero.c hero.h
	$(CC) -c hero.c $(CFLAGS)

linkedlist.o : linkedlist.c linkedlist.h 
	$(CC) -c linkedlist.c $(CFLAGS)

linkedlistnode.o : linkedlistnode.c linkedlist.h 
	$(CC) -c linkedlistnode.c $(CFLAGS)

newsleep.o : newsleep.c newsleep.h
	$(CC) -c newsleep.c $(CFLAGS)

rpgvisualiser.o : rpgvisualiser.c rpgvisualiser.h enemy.h hero.h stack.h
	$(CC) -c rpgvisualiser.c $(CFLAGS)

stack.o : stack.c stack.h linkedlist.h linkedlistnode.h
	$(CC) -c stack.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)

