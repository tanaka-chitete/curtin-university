package controller;

import model.Component;
import model.Composite;
import model.Leaf;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

public class Reader implements Option {
    @Override
    public Composite generate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<String> readGeneratorFile(String filename) {
        Set<String> set = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String row;
            while ((row = bufferedReader.readLine()) != null) {
                set.add(row);
            }
            bufferedReader.close();

        }
        catch (IOException e) {
            set = null;
            System.out.println("Generate fail: Could not generate network");
        }

        return set.iterator();
    }

    @Override
    public Composite readInputFile(String filename) {
        Composite root = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            String row = bufferedReader.readLine();
            root = (Composite) makeComponent(row);
            while ((row = bufferedReader.readLine()) != null) {
                Component component = makeComponent(row);
                addComponent(row, root, component);
            }   
            bufferedReader.close();

            System.out.format("Read success: Network read from %s%n", filename);
            Thread.sleep(1_000);
            System.out.println(); // Formatting purposes
        }
        catch (IOException | InterruptedException e) {
            root = null;
            System.out.format("Read fail: Could not read network from %s%n", filename);
        }

        return root;
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

    private Component makeComponent(String row) {
        String[] delimitedRow = row.split(",");
        
        Component component = null;
        if (delimitedRow.length == 1 || delimitedRow.length == 2) {
            component = new Composite(row);
        }
        else if (delimitedRow.length > 2) {
            component = new Leaf(row);
        }

        return component;
    }

    private void addComponent(String row, Composite root, Component component) {
        String[] delimitedRow = row.split(",");

        String parentLabel = delimitedRow[1];

        root.addChild(component, parentLabel);
    }
}
