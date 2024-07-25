package edu.curtin.comp2003.operator.main;

import edu.curtin.comp2003.operator.observer.RoverOperator;

public class Main {
    /**
     * The entry point for the Mars rover's operator.
     * 
     * @author Tanaka Chitete (20169321)
     */
    public static void main(String[] args) {
        RoverOperator rover = new RoverOperator();
        rover.run();
    }
}
