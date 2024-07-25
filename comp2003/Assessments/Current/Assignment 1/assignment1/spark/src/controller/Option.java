package controller;

import model.Composite;

import java.util.Iterator;
import java.util.Map;

public interface Option {
    public Composite generate();
    public Iterator<String> readGeneratorFile(String filename);
    public Composite readInputFile(String filename);
    
    public Map<String, Double> process(Composite root);
    public void display(Composite root, Map<String, Double> categoryToTotalConsumption);
    public void write(Composite root, String filename);
}
