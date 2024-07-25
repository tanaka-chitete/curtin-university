package controller;

import model.Composite;
import model.Component;
import model.Leaf;

import java.util.Map;
import java.util.Random;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Generator implements Option {
    /** Fields */

    private Random random;
    private List<Iterator<String>> names;

    /** Constants */

    private static final String[] categories = {
        "dm", "da", "de", "em", "ea", "ee", "h", "s"
    };

    /** Default Constructor */

    public Generator() {
        random = new Random();
        names = populateNames();
    }

    @Override
    public Composite generate() {
        String city = names.get(0).next();
        Composite root = new Composite(city);
        int depth = random.nextInt(5) + 1;
        if (depth > 1) {
            int childrenCount = random.nextInt(4) + 2;
            for (int i = 0; i < childrenCount; i++) {
                Component child = generate(depth - 1, city, 1);
                root.addChild(child, city);
            }
        }

        try {
            System.out.println("Generate success: Network generated");
            Thread.sleep(1_000);
            System.out.println(); // Formatting purposes
        }
        catch (InterruptedException e) {}

        return root;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Composite root, String filename) {
        throw new UnsupportedOperationException();
    }

    /** Helpers */

    private Component generate(int depth, String parentLabel, int namesIndex) {
        Component component;

        String name = names.get(namesIndex).next();
        if (depth == 1) {
            Leaf leaf = new Leaf(String.format("%s,%s", name, parentLabel));
            populateConsumptions(leaf);
            component = leaf;
        }
        else {
            Composite composite = new Composite(String.format("%s,%s", name, parentLabel));
            int childrenCount = random.nextInt(4) + 2;
            for (int i = 0; i < childrenCount; i++) {
                composite.addChild(generate(depth - 1, name, namesIndex + 1), name);
            }
            component = composite;
        }

        return component;
    }

    private List<Iterator<String>> populateNames() {
        Reader reader = new Reader();

        Iterator<String> cities = 
            reader.readGeneratorFile("resources/generatorfiles/cities.txt");
        Iterator<String> postcodes = 
            reader.readGeneratorFile("resources/generatorfiles/postcodes.txt");
        Iterator<String> suburbs = 
            reader.readGeneratorFile("resources/generatorfiles/suburbs.txt");
        Iterator<String> addresses = 
            reader.readGeneratorFile("resources/generatorfiles/addresses.txt");
        Iterator<String> subaddresses =
            reader.readGeneratorFile("resources/generatorfiles/subaddresses.txt");

        List<Iterator<String>> names = new ArrayList<>();
        names.add(0, cities);
        names.add(1, postcodes);
        names.add(2, suburbs);
        names.add(3, addresses);
        names.add(4, subaddresses);

        return names;
    }

    private void populateConsumptions(Leaf leaf) {
        for (String category : categories) {
            leaf.addToData(",");
            double consumption = random.nextDouble() * 1_000.0;
            leaf.addToData(String.format("%s=%f", category, consumption));
        }
    }
}
