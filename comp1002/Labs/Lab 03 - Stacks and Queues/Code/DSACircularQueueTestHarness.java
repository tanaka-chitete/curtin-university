import java.util.*;

public class DSACircularQueueTestHarness {
    public static void main(String[] args) {
        try {
            DSACircularQueue[] queues = new DSACircularQueue[3];

            // Object instantiation
            queues[0] = new DSACircularQueue();
            queues[1] = new DSACircularQueue(10);
            
            // Accessors and Mutators
            System.out.println("queues[0]");
            System.out.println("enqueue Test (View code)");
            enqueueTest(queues[0]);
            System.out.println("Number of elements: " + 
                               queues[0].getNumElements() + 
                               "\nMaximum number of elements: " + 
                               queues[0].getMaxNumElements());
            System.out.println("peek Test\nElement 99: " + queues[0].peek());
            System.out.println("dequeue Test\nFRONT OF THE QUEUE");
            dequeueTest(queues[0]);
            System.out.println("BACK OF THE QUEUE\n");

            System.out.println("queues[1]");
            System.out.println("enqueue Test (View code)");
            enqueueTest(queues[1]);
            System.out.println("Number of elements: " + 
                               queues[1].getNumElements() + 
                               "\nMaximum number of elements: " + 
                               queues[1].getMaxNumElements());
            System.out.println("peek Test\nElement 9: " + queues[1].peek());
            System.out.println("dequeue Test\nFRONT OF THE QUEUE");
            dequeueTest(queues[1]);
            System.out.println("BACK OF THE QUEUE");
        }
        catch(IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
    private static void enqueueTest(DSACircularQueue queue) {
        for (int i = 0; i < queue.getMaxNumElements(); i++) {
            queue.enqueue(Integer.valueOf(i));
        }
    }

    private static void dequeueTest(DSACircularQueue queue) {
        int numElements = queue.getNumElements();
        for (int i = 0; i < numElements; i++) {
            System.out.println("Element " + i + ": " + queue.dequeue());
        }
    }
}