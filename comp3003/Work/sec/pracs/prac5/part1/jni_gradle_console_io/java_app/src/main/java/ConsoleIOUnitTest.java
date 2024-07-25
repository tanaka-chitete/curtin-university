import java.util.List;

public class ConsoleIOUnitTest
{
    public static void main(String[] args)
    {
        // TEST 1: read (double input)
        System.out.print("Input: ");
        double input = ConsoleIO.read(991.2);
        System.out.println(input); 

        // TEST 2: read (non-double input)
        System.out.print("Input: ");
        input = ConsoleIO.read(991.2);
        System.out.println(input); 

        // TEST 3: printStr (String input)
        ConsoleIO.printStr("My favourite car is the 2018 Porsche GT2 RS");

        // TEST 4: printStr (null input)
        ConsoleIO.printStr(null);

        // TEST 5: printList (List input)
        ConsoleIO.printList(List.of("2018 Porsche GT2 RS", 
                        "2014 Ferrari 458 Speciale", 
                        "2017 Mercedes-AMG GTR"));

        // TEST 6: printList (null input)
        ConsoleIO.printList(null);
    }
}