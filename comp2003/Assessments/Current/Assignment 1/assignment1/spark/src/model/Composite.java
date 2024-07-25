package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Composite extends Component {
    /** Class Fields */

    private String          label;
    private String          data;
    private List<Component> children;

    /** Alternate Constructor */

    public Composite (String row) {
        String[] delimitedRow = row.split(",");

        this.label = delimitedRow[0];
        this.data = row;
        children = new ArrayList<Component>();
    }

    /** Setters */

    public void addChild(Component child, String parentLabel) {
        Composite parent = findComposite(parentLabel);
        if (parent != null) {
            parent.children.add(child);
        }
    }

    /** Getters */

    @Override
    public Component find(String label) {
        Component found;

        if (label.equals(this.label)) {
            found = this;
        }
        else {
            int i = 0;
            found = null;
            while (found == null && i < children.size()) {
                Component child = children.get(i);
                found = child.find(label);

                i++;
            }
        }

        return found;
    }

    @Override
    public Leaf findLeaf(String label) {
        int i = 0;
        Leaf found = null;
        while (found == null && i < children.size()) {
            Component child = children.get(i);
            found = child.findLeaf(label);

            i++;
        }

        return found;
    }

    @Override
    public Composite findComposite(String label) {
        Composite found;

        if (label.equals(this.label)) {
            found = this;
        }
        else {
            int i = 0;
            found = null;
            while (found == null && i < children.size()) {
                Component child = children.get(i);
                found = child.findComposite(label);

                i++;
            }
        }

        return found;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    /** Operators */

    public Map<String, Double> process() {
        Map<String, Double> allCategoryToConsumption = prepareCategoryToTotalConsumption();
        for (Component child : children) {
            child.process(allCategoryToConsumption);
        }

        return allCategoryToConsumption;
    }    

    /** Helpers */

    @Override
    public String toString(String treeString, int depth) {
        treeString += String.format("%s%s%n", toSpaces(depth), label);
        for (Component child : children) {
            treeString = child.toString(treeString, depth + 1);
        }

        return treeString;
    }

    @Override
    public String toFile(String treeString) {
        treeString += String.format("%s%n", data);
        for (Component child : children) {
            treeString = child.toFile(treeString);
        }

        return treeString;
    }

    @Override
    public void process(Map<String, Double> allCategoryToConsumption) {
        for (Component child : children) {
            child.process(allCategoryToConsumption);
        }
    }

    private Map<String, Double> prepareCategoryToTotalConsumption() {
        Map<String, Double> categoryToTotalConsumption = new HashMap<>();

        categoryToTotalConsumption.put("dm", 0.0);
        categoryToTotalConsumption.put("da", 0.0);
        categoryToTotalConsumption.put("de", 0.0);
        categoryToTotalConsumption.put("em", 0.0);
        categoryToTotalConsumption.put("ea", 0.0);
        categoryToTotalConsumption.put("ee", 0.0);
        categoryToTotalConsumption.put("h",  0.0);
        categoryToTotalConsumption.put("s",  0.0);

        return categoryToTotalConsumption;
    }
}
