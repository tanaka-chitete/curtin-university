import java.util.*;

public class DSAGraphVertex {
    // PRIVATE CLASS FIELDS

    String label;
    DSALinkedList neighbours;
    boolean visited;

    // CONSTRUCTORS

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inLabel (String)
     * EXPORT(S): Address of new DSAGraphVertex
     * PURPOSE: Create new DSAGraphVertex in alternate state
     * CREATION: 25/09/2020
     * LAST MODIFICATION: 25/09/2020
     */

    public DSAGraphVertex(String inLabel) {
        if (inLabel == null) {
            throw new IllegalArgumentException("Cannot initialise " + 
                "with a null label");
        }
        else {
            label = inLabel;
            neighbours = new DSALinkedList();
            visited = false;
        }
    }

    // SETTERS (MUTATORS)

    public void addNeighbour(DSAGraphVertex inNeighbour) {
        neighbours.insertLast(inNeighbour);
    }

    public void markVisited() {
        if (!visited) {
            visited = true;
        }
    }

    public void markUnvisited() {
        if (visited) {
            visited = false;
        }
    }

    // GETTERS (ACCESSORS)

    public boolean wasVisited() {
        boolean wasVisited;

        if (!getVisited()) {
            wasVisited = false;
        }
        else {
            wasVisited = true;
        }

        return wasVisited;
    }

    public String getLabel() {
        return label;
    }

    public DSALinkedList getNeighbours() {
        return neighbours;
    }

    public boolean getVisited() {
        return visited;
    }
}
