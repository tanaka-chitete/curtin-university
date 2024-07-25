import java.util.*;

public class PDIMathTestHarness
{
    public static void main(String[] args)
    {
        // min Tests
        System.out.println("// min Tests //////////////////////////////////");
        printMinDiffAandB();
        System.out.println();
        printMinSameAandB(); 

        // max Tests
        System.out.println("\n// max Tests //////////////////////////////////");
        printMaxDiffAandB();
        System.out.println();
        printMaxSameAandB();     

        // abs Tests
        System.out.println("\n// abs Tests //////////////////////////////////");
        printAbsPosA();
        System.out.println();
        printAbsNegA();

        // floor Tests
        System.out.println("\n// floor Tests ////////////////////////////////");
        printFloorPosA();
        System.out.println();
        printFloorNegA(); 
        
        // ceil Tests
        System.out.println("\n// ceil Tests /////////////////////////////////");
        printCeilPosA();
        System.out.println();
        printCeilNegA(); 

        // pow Tests
        System.out.println("\n// pow Tests //////////////////////////////////");
        printPowPosBasePosExp();
        System.out.println();

        printPowNegBasePosExp(); 
        System.out.println();

        printPowPosBaseNegExp(); 
        System.out.println();

        printPowNegBaseNegExp();

        // pi Tests
        System.out.println("\n// pi Tests ///////////////////////////////////");
        printPi();

        // e Tests
        System.out.println("\n// e Tests ////////////////////////////////////");
        printE(); 
    }

    public static void printMinDiffAandB()
    {
        System.out.println("PDIMath.min(int 0, int 1) = " + 
                            PDIMath.min(0, 1));
        System.out.println("Math.min(int 0, int 1) = " + 
                            Math.min(0, 1));

        System.out.println("\nPDIMath.min(long 0, long 1) = " + 
                            PDIMath.min(0l, 1l));
        System.out.println("Math.min(long 0, long 1) = " +  
                            Math.min(0l, 1l));

        System.out.println("\nPDIMath.min(float 0.0, float 1.0) = " + 
                            PDIMath.min(0.0f, 1.0f));
        System.out.println("Math.min(float 0.0, float 1.0) = " + 
                            Math.min(0.0f, 1.0f));

        System.out.println("\nPDIMath.min(double 0.0, double 1.0) = " + 
                            PDIMath.min(0.0, 1.0));
        System.out.println("Math.min(double 0.0, double 1.0) = " +  
                            Math.min(0.0, 1.0));
    }

    public static void printMinSameAandB()
    {
        System.out.println("PDIMath.min(int 1, int 1) = " + 
                            PDIMath.min(1, 1));
        System.out.println("Math.min(int 1, int 1) = " + 
                            Math.min(1, 1));

        System.out.println("\nPDIMath.min(long 1, long 1) = " + 
                            PDIMath.min(1l, 1l));
        System.out.println("Math.min(long 1, long 1) = " +  
                            Math.min(1l, 1l));

        System.out.println("\nPDIMath.min(float 1.0, float 1.0) = " + 
                            PDIMath.min(1.0f, 1.0f));
        System.out.println("Math.min(float 1.0, float 1.0) = " + 
                            Math.min(1.0f, 1.0f));

        System.out.println("\nPDIMath.min(double 1.0, double 1.0) = " + 
                            PDIMath.min(1.0, 1.0));
        System.out.println("Math.min(double 1.0, double 1.0) = " +  
                            Math.min(1.0, 1.0));
    }

    public static void printMaxDiffAandB()
    {
        System.out.println("PDIMath.max(int 0, int 1) = " + 
                            PDIMath.max(0, 1));
        System.out.println("Math.max(int 0, int 1) = " + 
                            Math.max(0, 1));

        System.out.println("\nPDIMath.max(long 0, long 1) = " + 
                            PDIMath.max(0l, 1l));
        System.out.println("Math.max(long 0, long 1) = " +  
                            Math.max(0l, 1l));

        System.out.println("\nPDIMath.max(float 0.0, float 1.0) = " + 
                            PDIMath.max(0.0f, 1.0f));
        System.out.println("Math.max(float 0.0, float 1.0) = " + 
                            Math.max(0.0f, 1.0f));

        System.out.println("\nPDIMath.max(double 0.0, double 1.0) = " + 
                            PDIMath.max(0.0, 1.0));
        System.out.println("Math.max(double 0.0, double 1.0) = " +  
                            Math.max(0.0, 1.0));
    }

    public static void printMaxSameAandB()
    {
        System.out.println("PDIMath.max(int 1, int 1) = " + 
                            PDIMath.max(1, 1));
        System.out.println("Math.max(int 1, int 1) = " + 
                            Math.max(1, 1));

        System.out.println("\nPDIMath.max(long 1, long 1) = " + 
                            PDIMath.max(1l, 1l));
        System.out.println("Math.max(long 1, long 1) = " +  
                            Math.max(1l, 1l));

        System.out.println("\nPDIMath.max(float 1.0, float 1.0) = " + 
                            PDIMath.max(1.0f, 1.0f));
        System.out.println("Math.max(float 1.0, float 1.0) = " + 
                            Math.max(1.0f, 1.0f));

        System.out.println("\nPDIMath.max(double 1.0, double 1.0) = " + 
                            PDIMath.max(1.0, 1.0));
        System.out.println("Math.max(double 1.0, double 1.0) = " +  
                            Math.max(1.0, 1.0));
    }

    public static void printAbsPosA()
    {
        System.out.println("PDIMath.abs(int 1) = " + PDIMath.abs(1)); 
        System.out.println("Math.abs(int 1) = " + Math.abs(1));
 
        System.out.println("PDIMath.abs(long 1) = " + PDIMath.abs(1l)); 
        System.out.println("Math.abs(long 1) = " + Math.abs(1l));
 
        System.out.println("PDIMath.abs(float 1.0) = " + PDIMath.abs(1.0f)); 
        System.out.println("Math.abs(float 1.0) = " + Math.abs(1.0f));
 
        System.out.println("PDIMath.abs(double 1.0) = " + PDIMath.abs(1.0)); 
        System.out.println("Math.abs(double 1.0) = " + Math.abs(1.0)); 
    }

    public static void printAbsNegA()
    {
        System.out.println("PDIMath.abs(int -1) = " + PDIMath.abs(-1)); 
        System.out.println("Math.abs(int -1) = " + Math.abs(-1));
 
        System.out.println("PDIMath.abs(long -1) = " + PDIMath.abs(-1l)); 
        System.out.println("Math.abs(long -1) = " + Math.abs(-1l));
 
        System.out.println("PDIMath.abs(float -1.0) = " + PDIMath.abs(-1.0f)); 
        System.out.println("Math.abs(float -1.0) = " + Math.abs(-1.0f));
 
        System.out.println("PDIMath.abs(double -1.0) = " + PDIMath.abs(-1.0)); 
        System.out.println("Math.abs(double -1.0) = " + Math.abs(-1.0)); 
    }

    public static void printFloorPosA()
    {
        System.out.println("PDIMath.floor(double 1.9) = " + 
                            PDIMath.floor(1.9)); 
        System.out.println("Math.floor(double 1.9) = " + 
                            Math.floor(1.9));
    }

    public static void printFloorNegA()
    {
        System.out.println("PDIMath.floor(double -1.9) = " + 
                            PDIMath.floor(-1.9)); 
        System.out.println("Math.floor(double -1.9) = " + 
                            Math.floor(-1.9));
    }

    public static void printCeilPosA()
    {
        System.out.println("PDIMath.ceil(double 1.1) = " + 
                            PDIMath.ceil(1.1)); 
        System.out.println("Math.ceil(double 1.1) = " + 
                            Math.ceil(1.1));
    }

    public static void printCeilNegA()
    {
        System.out.println("PDIMath.ceil(double -1.1) = " + 
                            PDIMath.ceil(-1.1)); 
        System.out.println("Math.ceil(double -1.1) = " + 
                            Math.ceil(-1.1));
    }

    public static void printPowPosBasePosExp()
    {
        System.out.println("PDIMath.pow(double 2.0, int 2) = " + 
                            PDIMath.pow(2.0, 2));
        System.out.println("Math.pow(double 2.0, double 2.0) = " +
                            Math.pow(2.0, 2.0));
        System.out.println("PDIMath.pow(double 2.0, int 3) = " + 
                            PDIMath.pow(2.0, 3));
        System.out.println("Math.pow(double 2.0, double 3.0) = " +
                            Math.pow(2.0, 3.0));
        System.out.println("PDIMath.pow(double 2.0, int 4) = " + 
                            PDIMath.pow(2.0, 4));
        System.out.println("Math.pow(double 2.0, double 4.0) = " +
                            Math.pow(2.0, 4.0));
    }

    public static void printPowNegBasePosExp()
    {
        System.out.println("PDIMath.pow(double -2.0, int 2) = " + 
                            PDIMath.pow(-2.0, 2));
        System.out.println("Math.pow(double -2.0, double 2.0) = " +
                            Math.pow(-2.0, 2.0));
        System.out.println("PDIMath.pow(double -2.0, int 3) = " + 
                            PDIMath.pow(-2.0, 3));
        System.out.println("Math.pow(double -2.0, double 3.0) = " +
                            Math.pow(-2.0, 3.0));
        System.out.println("PDIMath.pow(double -2.0, int 4) = " + 
                            PDIMath.pow(-2.0, 4));
        System.out.println("Math.pow(double -2.0, double 4.0) = " +
                            Math.pow(-2.0, 4.0));
    }

    public static void printPowPosBaseNegExp()
    {
        System.out.println("PDIMath.pow(double 2.0, int -2) = " + 
                            PDIMath.pow(2.0, -2));
        System.out.println("Math.pow(double 2.0, double -2.0) = " +
                            Math.pow(2.0, -2.0));
        System.out.println("PDIMath.pow(double 2.0, int -3) = " + 
                            PDIMath.pow(2.0, -3));
        System.out.println("Math.pow(double 2.0, double -3.0) = " +
                            Math.pow(2.0, -3.0));
        System.out.println("PDIMath.pow(double 2.0, int -4) = " + 
                            PDIMath.pow(2.0, -4));
        System.out.println("Math.pow(double 2.0, double -4.0) = " +
                            Math.pow(2.0, -4.0));
    }

    public static void printPowNegBaseNegExp()
    {
        System.out.println("PDIMath.pow(double -2.0, int -2) = " + 
                            PDIMath.pow(-2.0, -2));
        System.out.println("Math.pow(double -2.0, double -2.0) = " +
                            Math.pow(-2.0, -2.0));
        System.out.println("PDIMath.pow(double -2.0, int -3) = " + 
                            PDIMath.pow(-2.0, -3));
        System.out.println("Math.pow(double -2.0, double -3.0) = " +
                            Math.pow(-2.0, -3.0));
        System.out.println("PDIMath.pow(double -2.0, int -4) = " + 
                            PDIMath.pow(-2.0, -4));
        System.out.println("Math.pow(double -2.0, double -4.0) = " +
                            Math.pow(-2.0, -4.0));
    }

    public static void printPi()
    {
        System.out.println("Math.PI = " + Math.PI);

        System.out.println("PDIMath.pi(int 20) = " + PDIMath.pi(20));
        System.out.println("PDIMath.pi(int 19) = " + PDIMath.pi(19));
        System.out.println("PDIMath.pi(int 18) = " + PDIMath.pi(18));
        System.out.println("PDIMath.pi(int 17) = " + PDIMath.pi(17));
        System.out.println("PDIMath.pi(int 16) = " + PDIMath.pi(16));
        System.out.println("PDIMath.pi(int 15) = " + PDIMath.pi(15));
        System.out.println("PDIMath.pi(int 14) = " + PDIMath.pi(14));
        System.out.println("PDIMath.pi(int 13) = " + PDIMath.pi(13));
        System.out.println("PDIMath.pi(int 12) = " + PDIMath.pi(12));
        System.out.println("PDIMath.pi(int 11) = " + PDIMath.pi(11));
        System.out.println("PDIMath.pi(int 10) = " + PDIMath.pi(10));
        System.out.println("PDIMath.pi(int 9) = " + PDIMath.pi(9));
        System.out.println("PDIMath.pi(int 8) = " + PDIMath.pi(8));
        System.out.println("PDIMath.pi(int 7) = " + PDIMath.pi(7));
        System.out.println("PDIMath.pi(int 6) = " + PDIMath.pi(6));
        System.out.println("PDIMath.pi(int 5) = " + PDIMath.pi(5));
    }

    public static void printE()
    {
        System.out.println("Math.E = " + Math.E);

        System.out.println("PDIMath.e(20) = " + PDIMath.e(20));
        System.out.println("PDIMath.e(19) = " + PDIMath.e(19));
        System.out.println("PDIMath.e(18) = " + PDIMath.e(18));
        System.out.println("PDIMath.e(17) = " + PDIMath.e(17));
        System.out.println("PDIMath.e(16) = " + PDIMath.e(16));
        System.out.println("PDIMath.e(15) = " + PDIMath.e(15));
        System.out.println("PDIMath.e(14) = " + PDIMath.e(14));
        System.out.println("PDIMath.e(13) = " + PDIMath.e(13));
        System.out.println("PDIMath.e(12) = " + PDIMath.e(12));
        System.out.println("PDIMath.e(11) = " + PDIMath.e(11));
        System.out.println("PDIMath.e(10) = " + PDIMath.e(10));
        System.out.println("PDIMath.e(9) = " + PDIMath.e(9));
        System.out.println("PDIMath.e(8) = " + PDIMath.e(8));
        System.out.println("PDIMath.e(7) = " + PDIMath.e(7));
        System.out.println("PDIMath.e(6) = " + PDIMath.e(6));
        System.out.println("PDIMath.e(5) = " + PDIMath.e(5));
    }
}
