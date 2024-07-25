import java.util.*;

public class DSABinarySearchTreeTestHarness {

    public static void main(String[] args) {
        try {
            DSABinarySearchTree bst = new DSABinarySearchTree();
            
            testInsert(bst);
            testDisplay(bst);
            testHeight(bst);
            testMin(bst);
            testMax(bst);
            testCount(bst);
            testBalance(bst);
            testTraversePreorder(bst);
            testTraverseInorder(bst);
            testTraversePostorder(bst);
            testFind(bst);
            testDelete(bst);
            testDisplay(bst);
        }
        catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void testInsert(DSABinarySearchTree bst) {
        bst.insert("h", 8);
        bst.insert("c", 3);
        bst.insert("j", 10);
        bst.insert("a", 1);
        bst.insert("f", 6);
        bst.insert("n", 14);
        bst.insert("d", 4);
        bst.insert("g", 7);
        bst.insert("m", 13);
        bst.insert("o", 15);
    }

    private static void testHeight(DSABinarySearchTree bst) {
        System.out.println("height");
        System.out.println("height: " + bst.height() + "\n");
    }

    private static void testMin(DSABinarySearchTree bst) {
        System.out.println("min");
        System.out.println("min key: " + bst.min() + "\n");
    }
    
    private static void testMax(DSABinarySearchTree bst) {
        System.out.println("max");
        System.out.println("max key: " + bst.max() + "\n");
    }

    private static void testCount(DSABinarySearchTree bst) {
        System.out.println("count");
        System.out.println("count: " + bst.count() + "\n");
    }

    private static void testBalance(DSABinarySearchTree bst) {
        System.out.println("balance");
        System.out.println("percentage: " + bst.balance() + "\n");
    }

    private static void testTraversePreorder(DSABinarySearchTree bst) {
        System.out.println("traversePreorder");
        DSAQueue bstQueue = bst.traversePreorder();
        System.out.println(bstQueue.toString() + "\n");
    }

    private static void testTraverseInorder(DSABinarySearchTree bst) {
        System.out.println("traverseInorder");
        DSAQueue bstQueue = bst.traverseInorder();
        System.out.println(bstQueue.toString() + "\n");
    }

    private static void testTraversePostorder(DSABinarySearchTree bst) {
        System.out.println("traversePostorder");
        DSAQueue bstQueue = bst.traversePostorder();
        System.out.println(bstQueue.toString() + "\n");
    }    

    private static void testFind(DSABinarySearchTree bst) {
        System.out.println("find");
        try {
            Object foundKeyValue = (Object) bst.find("g");
            System.out.println("FOUND: key: 'g' value: " + foundKeyValue);

            foundKeyValue = (Object) bst.find("c");
            System.out.println("FOUND: key: 'j' value: " + foundKeyValue);

            foundKeyValue = (Object) bst.find("c");
            System.out.println("FOUND: key: 'c' value: " + foundKeyValue);

            foundKeyValue = (Object) bst.find("w");
            System.out.println("FOUND: key: 'w' value: " + foundKeyValue);
        } 
        catch (NoSuchElementException e) {
            System.out.println("NOT FOUND: key: 'w'");
        }
        System.out.println();
    }

    private static void testDelete(DSABinarySearchTree bst) {
        System.out.println("delete");
        try {
            bst.delete("g");
            System.out.println("DELETED: key: 'g' value: 7");

            bst.delete("c");
            System.out.println("DELETED: key: 'c' value: 3");

            bst.delete("j");
            System.out.println("DELETED: key: 'j' value: 10");

            bst.delete("w");
            System.out.println("DELETED: key: 'j' value: 10");
        }
        catch (NoSuchElementException e) {
            System.out.println("NOT DELETED: key 'w'");
        }
        System.out.println();
    }

    private static void testDisplay(DSABinarySearchTree bst) {
        System.out.println("display");
        bst.display();
        System.out.println();
    }
}