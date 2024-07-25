import java.util.*;

public class UnitTestDSAQueue {
    public static void main(String[] args) {
        try {
            //  Instantiation
            DSAQueue<Object> queue = new DSAQueue<Object>();
            
            // Accessors and Mutators
            System.out.println("enqueue Test (View code)");
            offerTest(queue, 100);
            System.out.println("Number of elements: " + queue.size());
            System.out.println("peek Test\nElement 0: " + queue.peek());
            System.out.println("dequeue Test\nFRONT OF THE QUEUE");
            pollTest(queue);
            System.out.println("BACK OF THE QUEUE");
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch(UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void offerTest(DSAQueue<Object> queue, int numEnqueueElements) {
        for (int i = 0; i < numEnqueueElements; i++) {
            queue.offer(Integer.valueOf(i));
        }
    }

    private static void pollTest(DSAQueue<Object> queue) {
        int numElements = queue.size();

        for (int i = 0; i < numElements; i++) {
            System.out.println("Element " + i + ": " + queue.poll());
        }
    }
}