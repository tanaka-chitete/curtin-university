CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = main.o bracketmatchlogic.o bracketmatchvisualiser.o colourlogic.o filereader.o linkedlist.o linkedlistnode.o stack.o
EXEC = bracketmatch

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

STACK : CFLAGS += -DSTACK
STACK : $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

main.o : main.c bracketmatchlogic.h filereader.h
	$(CC) -c main.c $(CFLAGS)

bracketmatchlogic.o : bracketmatchlogic.c bracketmatchlogic.h bool.h bracketmatchscenarios.h bracketmatchvisualiser.h brackets.h stack.h
	$(CC) -c bracketmatchlogic.c $(CFLAGS)

bracketmatchvisualiser.o : bracketmatchvisualiser.c bracketmatchvisualiser.h bool.h bracketmatchlogic.h bracketmatchscenarios.h colourcodes.h colourlogic.h stack.h
	$(CC) -c bracketmatchvisualiser.c $(CFLAGS)

colourlogic.o : colourlogic.c colourlogic.h brackets.h colourcodes.h
	$(CC) -c colourlogic.c $(CFLAGS)

filereader.o : filereader.c filereader.h
	$(CC) -c filereader.c $(CFLAGS)

linkedlist.o : linkedlist.c linkedlist.h bool.h linkedlistnode.h
	$(CC) -c linkedlist.c $(CFLAGS)

linkedlistnode.o : linkedlistnode.c linkedlistnode.h
	$(CC) -c linkedlistnode.c $(CFLAGS)

stack.o : stack.c stack.h bool.h linkedlist.h linkedlistnode.h
	$(CC) -c stack.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
