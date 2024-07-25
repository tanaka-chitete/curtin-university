import java.util.*;

public class UnitTestDSAQueue {
    public static void main(String[] args) {
        try {
            //  Instantiation
            DSAQueue queue = new DSAQueue();
            
            // Accessors and Mutators
            System.out.println("enqueue Test (View code)");
            enqueueTest(queue, 100);
            System.out.println("Number of elements: " + queue.getNumElements());
            System.out.println("peek Test\nElement 0: " + queue.peek());
            System.out.println("dequeue Test\nFRONT OF THE QUEUE");
            dequeueTest(queue);
            System.out.println("BACK OF THE QUEUE");
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch(UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void enqueueTest(DSAQueue queue, int numEnqueueElements) {
        for (int i = 0; i < numEnqueueElements; i++) {
            queue.enqueue(Integer.valueOf(i));
        }
    }

    private static void dequeueTest(DSAQueue queue) {
        int numElements = queue.getNumElements();

        for (int i = 0; i < numElements; i++) {
            System.out.println("Element " + i + ": " + queue.dequeue());
        }
    }
}