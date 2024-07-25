CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = main.o patternmatchlogic.o patternmatchvisualiser.o colourlogic.o string.o
EXEC = patternmatch 

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

index : CFLAGS += -DINDEX
index : $(EXEC)

debug : CFLAGS += -g
debug : $(EXEC)

main.o : main.c bool.h colourcodes.h colourlogic.h patternmatchlogic.h patternmatchvisualiser.h string.h
	$(CC) -c main.c $(CFLAGS)

patternmatchlogic.o : patternmatchlogic.c patternmatchlogic.h bool.h colourcodes.h colourlogic.h string.h
	$(CC) -c patternmatchlogic.c $(CFLAGS)

patternmatchvisualiser.o : patternmatchvisualiser.c patternmatchvisualiser.h bool.h colourcodes.h colourlogic.h string.h
	$(CC) -c patternmatchvisualiser.c $(CFLAGS)

colourlogic.o : colourlogic.c colourlogic.h bool.h colourcodes.h string.h
	$(CC) -c colourlogic.c $(CFLAGS)

string.o : string.c string.h bool.h
	$(CC) -c string.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
