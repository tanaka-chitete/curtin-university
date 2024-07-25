package model;

import java.util.Map;

public class Leaf extends Component {
    /** Class Fields */

    private String label;
    private String data;

    /** Alternate Constructor */

    public Leaf (String row) {
        String[] delimitedRow = row.split(",");

        label = delimitedRow[0];
        data = row;
    }

    /** Setters */

    public void addToData(String addition) {
        data += addition;
    }

    /** Getters */

    public Component find(String label) {
        Component found;

        if (label.equals(this.label)) {
            found = this;
        }
        else {
            found = null;
        }

        return found;
    }

    public Leaf findLeaf(String label) {
        Leaf found;

        if (label.equals(this.label)) {
            found = this;
        }
        else {
            found = null;
        }
        
        return found;
    }

    public Composite findComposite(String label) {
        return null;
    }

    /** Operators */

    public void process(Map<String, Double> categoryToTotalConsumption) {
        String[] delimitedData = data.split(",");

        for (int i = 2; i < delimitedData.length; i++) {
            String[] categoryAndConsumption = delimitedData[i].split("=");

            String category = categoryAndConsumption[0];
            double consumption = Double.parseDouble(categoryAndConsumption[1]);

            categoryToTotalConsumption.put(category, categoryToTotalConsumption.get(category) + consumption);
        }
    }

    /** Helpers */

    @Override
    public String toString(String treeString, int depth) {
        treeString += String.format("%s%s%n", toSpaces(depth), label);

        return treeString;
    }

    @Override
    public String toFile(String treeString) {
        treeString += String.format("%s%n", data);

        return treeString;
    }
}
