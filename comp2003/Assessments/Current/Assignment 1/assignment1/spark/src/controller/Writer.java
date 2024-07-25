package controller;

import model.Composite;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;

public class Writer implements Option {
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
        throw new UnsupportedOperationException();
    }


    @Override
    public void write(Composite root, String filename) {
        try {
            String treeString = root.toFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(treeString);

            System.out.format("Write success: Network written to %s%n", filename);

            bufferedWriter.close();
        }
        catch (IOException e) {
            System.out.format("Write fail: Could not write network to %s%n", filename);
        }
    }
}
