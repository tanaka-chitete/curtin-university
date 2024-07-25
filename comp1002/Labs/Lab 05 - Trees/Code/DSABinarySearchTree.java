import java.io.Serializable;
import java.util.*;

// Part of this file comprises externally-obtained code

public class DSABinarySearchTree implements Serializable {
    // NESTED CLASSES

    private class DSATreeNode implements Serializable {
        // PRIVATE CLASS MEMBERS
        
        private String key;
        private Object value;
        private DSATreeNode leftChild;
        private DSATreeNode rightChild;

        // CONSTRUCTORS

        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inKey (String), inValue (Object)
         * EXPORT(S): Address of new DSATreeNode object
         * PURPOSE: Create new DSATreeNode object with fields as follows: 
         *          key = inKey and
         *          value = inValue
         * CREATION: 21/09/2020
         * LAST MODIFICATION: 21/09/2020
         */
        
        public DSATreeNode(String inKey, Object inValue) {
            if (inKey == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            else {
                setKey(inKey);
                setValue(inValue);
                setLeft(null);
                setRight(null);
            }
        }
        
        // SETTERS (MUTATORS)

        public void setKey(String inKey) {
            key = inKey;
        }

        public void setValue(Object inValue) {
            value = inValue;
        }

        public void setLeft(DSATreeNode inLeftChild) {
            leftChild = inLeftChild;
        }

        public void setRight(DSATreeNode inRightChild) {
            rightChild = inRightChild;
        }

        // GETTERS (ACCESSORS)

        public String getKey() {
            return key;
        }

        public Object getValue() {
            return value;
        }

        public DSATreeNode getLeft() {
            return leftChild;
        }

        public DSATreeNode getRight() {
            return rightChild;
        }
    }

    // PRIVATE CLASS FIELDS

    private DSATreeNode root;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSABinarySearchTree object
     * PURPOSE: Create new DSABinarySearchTree in with fields as follows:
     *          root = null
     * CREATION: 21/09/2020
     * LAST MODIFICATION: 21/09/2020
     */

    public DSABinarySearchTree() {
        root = null;
    }

    // OPERATORS

    public void insert(String key, Object value) {
        root = insertRec(key, value, root);
    }

    public Object find(String key) {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call find on an " + 
                "empty tree");
        }
        else {
            return findRec(key, root);
        }
    }  

    public void delete(String key) {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call delete on an " + 
                "empty tree");
        }
        else {
            deleteRec(key, root);
        }
    }

    public int height() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call height on an " + 
                "empty tree");
        }
        else {
            return heightRec(root);
        }
    }

    public String min() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call min on an " +
                "empty tree");
        }
        else {
            // minIter(root);
            return minRec(root);
        }
    }

    public String max() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call max on an " +
                "empty tree");
        }
        else {
            // maxIter(root);
            return maxRec(root);
        }
    }

    public int count() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call count on an " + 
                "empty tree");
        }
        else {
            return countRec(root);
        }
    }

    public double balance() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call balance on an " + 
                "empty tree");
        }
        else {
            return balancePriv(root);
        }
    }

    public DSAQueue traversePreorder() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call " + 
                "traversePreorder on an empty tree");
        }
        else {
            DSAQueue bstQueue = new DSAQueue();
            traversePreorderRec(root, bstQueue);
            return bstQueue;
        }
    }

    public DSAQueue traverseInorder() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call " + 
                "traverseInorder on an empty tree");
        }
        else {
            DSAQueue bstQueue = new DSAQueue();
            traverseInorderRec(root, bstQueue);
            return bstQueue;
        }
    }

    public DSAQueue traversePostorder() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call " + 
                "traversePostorder on an empty tree");
        }
        else {
            DSAQueue bstQueue = new DSAQueue();
            traversePostorderRec(root, bstQueue);
            return bstQueue;
        }
    }

    public void display() {
        if (root == null) {
            throw new IllegalArgumentException("Cannot call display on an " + 
                "empty tree");
        }
        else {
            displayRec(root, 0);
        }
    }

    // PRIVATE SUBMODULES

    private DSATreeNode insertRec(String key, Object value, 
        DSATreeNode currNode) {
        DSATreeNode updateNode = currNode;

        // Base case: Found insertion point
        if (currNode == null) {
            updateNode = new DSATreeNode(key, value); 
        }
        // Base case: Duplicate key found
        else if (key.equals(currNode.getKey())) {
            throw new IllegalArgumentException("Key '" + key + "' is present");
        }
        // Recursive case: Go left
        else if (key.compareTo(currNode.getKey()) < 0) {
            currNode.setLeft(insertRec(key, value, currNode.getLeft()));
        }
        // Recursive case: Go right
        else {
            currNode.setRight(insertRec(key, value, currNode.getRight()));
        }

        return updateNode;
    }

    private Object findRec(String key, DSATreeNode currNode) {
        Object value = null;

        // Base case: key not found
        if (currNode == null) {
            throw new NoSuchElementException("Key '" + key + "' not found");
        }
        // Base case: key found
        else if (key.equals(currNode.getKey())) {
            value = currNode.getValue();
        }
        // Recursive case: go left
        else if (key.compareTo(currNode.getKey()) < 0) {
            value = findRec(key, currNode.getLeft());
        }
        // Recursive case: go right
        else {
            value = findRec(key, currNode.getRight());
        }

        return value;
    }    

    private DSATreeNode deleteRec(String key, DSATreeNode currNode) {
        DSATreeNode updateNode = currNode;

        // Base case: no key found
        if (currNode == null) {
            throw new NoSuchElementException("Key '" + key + 
                "' is not present");
        }
        // Base case: key found
        else if (key.equals(currNode.getKey())) {
            updateNode = deleteNode(key, currNode);
        }
        // Recursive case: go left
        else if (key.compareTo(currNode.getKey()) < 0) {
            currNode.setLeft(deleteRec(key, currNode.getLeft()));
        }
        // Recursive case: go right
        else {
            currNode.setRight(deleteRec(key, currNode.getRight()));
        }

        return updateNode;
    }

    private DSATreeNode deleteNode(String key, DSATreeNode delNode) {
        DSATreeNode updateNode = null;

        // Case 1: No children
        if (delNode.getLeft() == null && delNode.getRight() == null) {
            updateNode = null;
        }
        // Case 2: 1 child (left)
        else if (delNode.getLeft() != null && delNode.getRight() == null) {
            updateNode = delNode.getLeft();
        }
        // Case 3: 1 child (right)
        else if (delNode.getLeft() == null && delNode.getRight() != null) {
            updateNode = delNode.getRight();
        }
        // Case 4: 2 children
        else {
            updateNode = promoteSuccessor(delNode.getRight());
            // Ensures we don't have any cycles
            if (updateNode != delNode.getRight()) {
                updateNode.setRight(delNode.getRight());
            }
            updateNode.setLeft(delNode.getLeft());
        }

        return updateNode;
    }

    private DSATreeNode promoteSuccessor(DSATreeNode currNode) {
        DSATreeNode succNode = currNode;

        if (currNode.getLeft() != null) {
            succNode = promoteSuccessor(currNode.getLeft());
            // currNode.getLeft is the parent of succNode
            if (succNode == currNode.getLeft()) {
                // Makes currNode adopt right child of succNode
                currNode.setLeft(succNode.getRight());
            }
        }

        return succNode;
    }

    private int heightRec(DSATreeNode currNode) {
        int currHgt, leftHgt, rightHgt;

        // Base case 1: No more branches from currNode
        if (currNode == null) {
            currHgt = -1;
        }
        else {
            leftHgt = heightRec(currNode.getLeft());
            rightHgt = heightRec(currNode.getRight());

            if (leftHgt > rightHgt) {
                currHgt = leftHgt + 1;
            }
            else {
                currHgt = rightHgt + 1;
            }
        }

        return currHgt;
    }    

    private String minRec(DSATreeNode currNode) {
        String minKey;
        if (currNode.getLeft() != null) {
            minKey = minRec(currNode.getLeft());
        }
        else {
            minKey = currNode.getKey();
        }
        return minKey;
    }

    private String maxRec(DSATreeNode currNode) {
        String maxKey;
        if (currNode.getRight() != null) {
            maxKey = maxRec(currNode.getRight());
        }
        else {
            maxKey = currNode.getKey();
        }
        return maxKey;
    }

    private int countRec(DSATreeNode currNode) {
        int numElems;

        if (currNode == null) {
            numElems = 0;
        }
        else {
            numElems = 1;
            numElems += countRec(currNode.getLeft());
            numElems += countRec(currNode.getRight());
        }

        return numElems;
    }

    private double balancePriv(DSATreeNode currNode) {
        // Converts to 1-based index
        int height = height() + 1; 
        int numElems = count();
        double idealNumElems = Math.pow(2.0, (double) height) - 1.0;
        double pctge = ((double) numElems / idealNumElems) * 100.0;

        return pctge;
    }

    private void traversePreorderRec(DSATreeNode currNode, 
        DSAQueue bstQueue) {
        if (currNode != null) {
            String keyValue = currNode.getKey() + "," + currNode.getValue();
            bstQueue.enqueue(keyValue);
            traversePreorderRec(currNode.getLeft(), bstQueue);
            traversePreorderRec(currNode.getRight(), bstQueue);
        }
    }

    private void traverseInorderRec(DSATreeNode currNode, 
        DSAQueue bstQueue) {
        if (currNode != null) {
            traverseInorderRec(currNode.getLeft(), bstQueue);
            String keyValue = currNode.getKey() + "," + currNode.getValue();
            bstQueue.enqueue(keyValue);
            traverseInorderRec(currNode.getRight(), bstQueue);
        }
    }

    private void traversePostorderRec(DSATreeNode currNode, 
        DSAQueue bstQueue) {
        if (currNode != null) {
            traversePostorderRec(currNode.getLeft(), bstQueue);
            traversePostorderRec(currNode.getRight(), bstQueue);
            String keyValue = currNode.getKey() + "," + currNode.getValue();
            bstQueue.enqueue(keyValue);
        }
    }

    private void displayRec(DSATreeNode currNode, int currLvl) {
        String spaces = currLvlToSpaces(currLvl);
        
        System.out.println(spaces + "key: '" + currNode.getKey() + 
            "', value: '" + currNode.getValue() + "'");

        if (currNode.getLeft() != null) {
            displayRec(currNode.getLeft(), currLvl + 1);
        }
        if (currNode.getRight() != null) {
            displayRec(currNode.getRight(), currLvl + 1);
        }
    }

    // Adapted from Syler
    // https://stackoverflow.com/questions/2635076/convert-integer-to-equivalent-number-of-blank-spaces
    // Accessed 22/09/2020

    private String currLvlToSpaces(int currLvl) {
        StringBuilder sb = new StringBuilder(currLvl);
        for (int i = 0; i < currLvl; i++) {
            sb.append(" ");
        }
        String spaces = sb.toString();

        return spaces;
    }

    // End of code adapted from Syler
}
