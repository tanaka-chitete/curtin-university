CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = bounds.o powers.o scan.o
EXEC = bounds

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

bounds.o : bounds.c bounds.h macros.h powers.h scan.h
	$(CC) -c bounds.c $(CFLAGS)

powers.o : powers.h
	$(CC) -c powers.c $(CFLAGS)

scan.o : scan.h
	$(CC) -c scan.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ)
