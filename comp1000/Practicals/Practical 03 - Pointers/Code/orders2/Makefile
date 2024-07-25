CC = gcc
CFLAGS = -Wall -pedantic -ansi -Werror
OBJ = numbers.o orders.o user_input.o
EXEC = numbers

$(EXEC) : $(OBJ)
	$(CC) $(OBJ) -o $(EXEC)

numbers.o : numbers.c numbers.h orders.h user_input.h
	$(CC) -c numbers.c $(CFLAGS)

orders.o : orders.h
	$(CC) -c orders.c $(CFLAGS)

user_input.o : user_input.h
	$(CC) -c user_input.c $(CFLAGS)

clean:
	rm -f $(EXEC) $(OBJ) 
