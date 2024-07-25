package view;

import controller.Option;
import model.Composite;

import java.util.Map;
import java.util.Iterator;

public class Displayer implements Option {
    @Override
    public Composite generate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> readGeneratorFile(String filename) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Composite readInputFile(String filename) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Double> process(Composite root) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void display(Composite root, Map<String, Double> categoryToTotalConsumption) {
        if (root != null) {
            System.out.println(root);

            if (root.isLeaf()) {
                System.out.println("No electricity usage information is available for a network " + 
                                   "consisting of only a root node as potentially saving the " + 
                                   "above generated network with electricity data would violate " +
                                   "the expected format for a line representing a root node " + 
                                   "(which should only be in the format [name])");
            }
            else {
                System.out.format("Weekday morning   : %,.2f%n", 
                                  categoryToTotalConsumption.get("dm"));
                System.out.format("Weekday afternoon : %,.2f%n", 
                                  categoryToTotalConsumption.get("da"));
                System.out.format("Weekday evening   : %,.2f%n", 
                                  categoryToTotalConsumption.get("de"));
                System.out.format("Weekend morning   : %,.2f%n", 
                                  categoryToTotalConsumption.get("em"));
                System.out.format("Weekend afternoon : %,.2f%n", 
                                  categoryToTotalConsumption.get("ea"));
                System.out.format("Weekend evening   : %,.2f%n", 
                                  categoryToTotalConsumption.get("ee"));
                System.out.format("Heatwave          : %,.2f%n", 
                                  categoryToTotalConsumption.get("h"));
                System.out.format("Special event     : %,.2f%n", 
                                  categoryToTotalConsumption.get("s"));
            }
        }
    }

    @Override 
    public void write(Composite root, String filename) {
        throw new UnsupportedOperationException();
    }
}
