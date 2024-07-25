package model;

import java.util.Map;

public abstract class Component {
    public abstract Component find(String label);
    public abstract Leaf findLeaf(String label);
    public abstract Composite findComposite(String label);
    public abstract String toString(String treeString, int depth);
    public abstract String toFile(String treeString);
    public abstract void process(Map<String, Double> categoryToTotalConsumption);

    /** Operators */

    @Override
    public String toString() {
        return toString("", 0);
    }

    public String toFile() {
        return toFile("");
    }

    /** Helpers */

    public String toSpaces(int depth) {
        String spaces = "";
        for (int i = 0; i < depth; i++) {
            spaces += "    ";
        }

        return spaces;
    }
}
