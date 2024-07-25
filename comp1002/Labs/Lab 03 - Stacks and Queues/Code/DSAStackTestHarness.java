import java.util.*;

public class DSAStackTestHarness {
    public static void main(String[] args) {
        try {
            DSAStack[] stacks = new DSAStack[3];

            // Object instantiation
            stacks[0] = new DSAStack(); 
            stacks[1] = new DSAStack(10);
            
            // Accessors and Mutators
            System.out.println("stacks[0]");
            System.out.println("push Test (View code)");
            pushTest(stacks[0]);
            System.out.println("Number of elements: " + 
                               stacks[0].getNumElements() + 
                               "\nMaximum number of elements: " + 
                               stacks[0].getMaxNumElements());
            System.out.println("peek Test\nElement 99: " + stacks[0].peek());
            System.out.println("pop Test\nTOP OF THE STACK");
            popTest(stacks[0]);
            System.out.println("BOTTOM OF THE STACK\n");

            System.out.println("stacks[1]");
            System.out.println("push Test (View code)");
            pushTest(stacks[1]);
            System.out.println("Number of elements: " + 
                               stacks[1].getNumElements() + 
                               "\nMaximum number of elements: " + 
                               stacks[1].getMaxNumElements());
            System.out.println("peek Test\nElement 9: " + stacks[1].peek());
            System.out.println("pop Test\nTOP OF THE STACK");
            popTest(stacks[1]);
            System.out.println("BOTTOM OF THE STACK");
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void pushTest(DSAStack stack) {
        for (int i = 0; i < stack.getMaxNumElements(); i++) {
            stack.push(Integer.valueOf(i));
        }
    }

    private static void popTest(DSAStack stack) {
        for (int i = stack.getNumElements() - 1; i >= 0; i--) {
            System.out.println("Element " + i + ": " + stack.pop());
        }
    }
}