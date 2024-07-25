import java.util.*;

public class DSALinkedListTestHarness {
    public static void main(String[] args) {
        try {
            // Instantiation
            DSALinkedList linkedList = new DSALinkedList();

            // Insertion
            linkedList.insertFirst("abc");
            linkedList.insertFirst("def");
            linkedList.insertFirst("hij");
            linkedList.insertFirst("klm");
            linkedList.insertFirst("nop");
            linkedList.insertFirst("qrs");

            // Iteration
            Iterator linkedListIterator = linkedList.iterator();

            System.out.println("hasNext(): " + linkedListIterator.hasNext());

            System.out.println("next(): " + linkedListIterator.next());
            System.out.println("next(): " + linkedListIterator.next());
            System.out.println("next(): " + linkedListIterator.next());
            System.out.println("next(): " + linkedListIterator.next());
            System.out.println("next(): " + linkedListIterator.next());
            System.out.println("next(): " + linkedListIterator.next());

            System.out.println("hasNext(): " + linkedListIterator.hasNext());
        }
        catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
        catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
    }
}