package controller;

import model.Composite;

import java.util.Map;
import java.util.Iterator;

public class Processor implements Option {
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
        return root.process();
    }

    @Override
    public void display(Composite root, Map<String, Double> categoryToTotalConsumption) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Composite root, String filename) {
        throw new UnsupportedOperationException();
    }
}
