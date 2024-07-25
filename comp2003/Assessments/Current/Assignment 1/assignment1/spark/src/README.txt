____________________________________________________________________________________________________
COMPILATION
----------------------------------------------------------------------------------------------------
To compile Spark, execute the following command;
ant

____________________________________________________________________________________________________
EXECUTION
----------------------------------------------------------------------------------------------------
To run Spark, execute one of the following commands:

Invocation 1: Generate and Display
ant -Darg0=g -Darg1=d generateanddisplay

Invocation 2: Generate and Write
ant -Darg0=g -Darg1=w -Darg2=<output filename> generateandwrite

Invocation 3: Read and Display
ant -Darg0=r -Darg1=<input filename> -Darg2=d readanddisplay

Invocation 4: Read and Write
ant -Darg0=r -Darg1=<input filename> -Darg2=w -Darg3=<output filename> readandwrite

where <input filename> is the name of the CSV file (including the .csv extension) from which to 
read the lines representing the nodes of the network and <output filename> is the name of the CSV 
file (also including the .csv extension) to which to write the nodes of the network as their 
representative lines.

EXAMPLES
Invocation 1: Generate and Display
ant -Darg0=g -Darg1=d generateanddisplay

Invocation 2: Generate and Write
ant -Darg0=g -Darg1=w -Darg2=resources/outputfiles/outputfile.csv generateandwrite

Invocation 3: Read and Display
ant -Darg0=r -Darg1=resources/inputfiles/inputfile.csv -Darg2=d readanddisplay

Invocation 4: Read and Write
ant -Darg0=r -Darg1=resources/inputfiles/inputfile.csv  -Darg2=w -Darg3=resources/outputfiles/outputfile.csv readandwrite

____________________________________________________________________________________________________
CLEAN UP
----------------------------------------------------------------------------------------------------
To delete .class files, execute the following command;

ant clean

____________________________________________________________________________________________________
NOTES
----------------------------------------------------------------------------------------------------
• All of the aforementioned commands are to be executed from the root directory (i.e. /spark)
