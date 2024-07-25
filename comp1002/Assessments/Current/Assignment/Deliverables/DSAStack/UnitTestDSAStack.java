import java.util.*;

public class UnitTestDSAStack {
    public static void main(String[] args) {
        try {
            // Instantiation
            DSAStack stack = new DSAStack();
            
            // Accessors and Mutators
            System.out.println("push Test (View code)");
            pushTest(stack, 100);
            System.out.println("Number of elements: " + stack.getNumElements());
            System.out.println("peek Test\nElement 99: " + stack.peek());
            System.out.println("pop Test\nTOP OF THE STACK");
            popTest(stack);
            System.out.println("BOTTOM OF THE STACK");
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch(UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void pushTest(DSAStack stack, int numPushElements) {
        for (int i = 0; i < numPushElements; i++) {
            stack.push(Integer.valueOf(i));
        }
    }

    private static void popTest(DSAStack stack) {
        int numElements = stack.getNumElements();

        for (int i = numElements - 1; i >= 0; i--) {
            System.out.println("Element " + i + ": " + stack.pop());
        }
    }
}