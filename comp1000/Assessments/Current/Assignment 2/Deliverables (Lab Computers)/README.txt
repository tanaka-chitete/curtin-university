-----------
DESCRIPTION
-----------
bracketmatch is a program designed and implemented to perform bracket-balance-
-checking on .c or .txt files.

-----
USAGE
-----
Compilation:
* make
* make STACK (Prints contents of bracket stack during execution)
* make debug (Prints useful debugging information when run with Valgrind)

Execution:
* ./bracketmatch <filename>.c 
* ./bracketmatch <filename>.txt 
* valgrind ./bracketmatch <filename>.c 
* valgrind ./bracketmatch <filename>.txt 

-----
NOTES
-----
* Ensure that <filename>.c or <filename>.txt is new-line-terminated, as
  attempting to execute bracketmatch with non-new-line-terminated files will
  result in a segmentation fault.
* To modify terminal output sleep timer modify the seconds parameter of 
  newSleep to receive the desired sleep time. The function is located on line 
  116 of bracketmatchlogic.c. Its header is as follows:
      void newSleep(float seconds)  
