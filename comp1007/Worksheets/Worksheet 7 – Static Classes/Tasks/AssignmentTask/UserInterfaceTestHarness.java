public class UserInterfaceTestHarness
{
    public static void main(String[] args)
    {
        // int userInput(int min, int max, String prompt) Tests
        System.out.println(
        "// int userInput(int min, int max, String prompt) //////////////////");
        printUserInterfaceInt();
        System.out.println();
        
        // long userInput(long min, long max, String prompt) Tests
        System.out.println(
        "// long userInput(long min, long max, String prompt) ///////////////");
        printUserInterfaceLong();
        System.out.println();
        
        // float userInput(float min, float max, String prompt) Tests
        System.out.println(
        "// float userInput(float min, float max, String prompt) ////////////");
        printUserInterfaceFloat();
        System.out.println();
        
        // double userInput(double min, double max, String prompt) Tests
        System.out.println(
        "// double userInput(double min, double max, String prompt) /////////");
        printUserInterfaceDouble();
        System.out.println();

        // char userInput(char min, char max, String prompt) Tests
        System.out.println(
        "// char userInput(char min, char max, String prompt) ///////////////");
        printUserInterfaceChar();
        System.out.println();

        // String userInput(String prompt) Tests
        System.out.println(
        "// String userInput(String prompt) /////////////////////////////////");
        printUserInterfaceString();
        System.out.println();
    }

    public static void printUserInterfaceInt()
    {
        int min, max;
        String prompt;

        int input;

        min = 0;
        max = 1;
        prompt = "Provide input between " + min + " and " + max + 
                 " inclusive: "; 
        input = UserInterface.userInput(min, max, prompt);
        System.out.println("Input provided: " + input);
    }

    public static void printUserInterfaceLong()
    {
        long min, max;
        String prompt;

        long input;

        min = 0l;
        max = 1l;
        prompt = "Provide input between " + min + " and " + max + 
                 " inclusive: "; 
        input = UserInterface.userInput(min, max, prompt);
        System.out.println("Input provided: " + input);
    }

    public static void printUserInterfaceFloat()
    {
        float min, max;
        String prompt;

        float input;

        min = 0.0f;
        max = 1.0f;
        prompt = "Provide input between " + min + " and " + max + 
                 " inclusive: "; 
        input = UserInterface.userInput(min, max, prompt);
        System.out.println("Input provided: " + input);
    }

    public static void printUserInterfaceDouble()
    {
        double min, max;
        String prompt;

        double input;

        min = 0.0;
        max = 1.0;
        prompt = "Provide input between " + min + " and " + max + 
                 " inclusive: "; 
        input = UserInterface.userInput(min, max, prompt);
        System.out.println("Input provided: " + input);
    }

    public static void printUserInterfaceChar()
    {
        char min, max;
        String prompt;

        char input;

        min = 'a';
        max = 'b';
        prompt = "Provide input between '" + min + "' and '" + max + 
                 "' inclusive: "; 
        input = UserInterface.userInput(min, max, prompt);
        System.out.println("Input provided: '" + input + "'");
    }

    public static void printUserInterfaceString()
    {
        String prompt;

        String input;

        prompt = "Provide input: "; 
        input = UserInterface.userInput(prompt);
        System.out.println("Input provided: '" + input + "'");
    }
}
