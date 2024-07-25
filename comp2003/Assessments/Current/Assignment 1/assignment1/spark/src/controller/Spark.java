/**
 * The class representing an instance of Spark, an application which models a city’s electricity 
 * network. It either reads or randomly generates a dataset representing the structure of the 
 * network. Thereafter, it either writes or displays the network. The user chooses which actions to 
 * perform via the command-line.
 * @author Tanaka Chitete
 */

/**
 * Adapted from Dave Cooper and Tanaka Chitete
 * AddressBookApp
 * Implemented for 3. The Strategy Pattern of Worksheet 2: Polymorphism
 * Accessed 13/04/2021
 */

package controller;

import controller.Option;
import model.Composite;

import java.util.Map;
import java.util.HashMap;

public class Spark {
    /** Private Fields */
    
    private Map<Character, Option> options;

    /** Default Constructor */

    public Spark() {
        options = new HashMap<Character, Option>();
    }

    /** Setters */

    public void addOption(char optionKey, Option option) {
        options.put(optionKey, option);
    }

    /** Getters */

    private Option getOption(char optionKey) {
        return options.get(optionKey);
    }

    /** Operators */

    public int identifyInvocation(String[] args) {
        int invocation;
        // ant -Darg0=g -Darg1=d generateanddisplay
        if (Validator.isGenerateAndDisplay(args)) {
            invocation = 1;
        }
        // ant -Darg0=g -Darg1=w -Darg2=<output filename> generateandwrite
        else if (Validator.isGenerateAndWrite(args)) {
            invocation = 2;
        }
        // ant -Darg0=r -Darg1=<input filename> -Darg2=d readanddisplay
        else if (Validator.isReadAndDisplay(args)) {
            invocation = 3;
        }
        // ant -Darg0=r -Darg1=<input filename> -Darg2=w -Darg3=<output filename> readandwrite
        else if (Validator.isReadAndWrite(args)) {
            invocation = 4;
        }
        // Content of args coincides with none of the above
        else {
            invocation = 0;
        }        

        return invocation;
    }

    public void launchInvocation(String[] args, int invocation) {
        Option generator = getOption('g');
        Option reader = getOption('r');
        Option processor = getOption('p');
        Option displayer = getOption('d');
        Option writer = getOption('w');

        switch (invocation) {
            case 1:
                generateAndDisplay();
                break;
            case 2:
                generateAndWrite(args[2]);
                break;
            case 3:
                readAndDisplay(args[1]);
                break;
            case 4:
                readAndWrite(args[1], args[3]);
                break;
            default:
                System.out.println(Validator.USAGE);
                break;
        }
    }

    private void generateAndDisplay() {
        Option generator = getOption('g');
        Option processor = getOption('p');
        Option displayer = getOption('d');

        Composite root = generator.generate();
        Map<String, Double> categoryToTotalConsumption = processor.process(root);
        displayer.display(root, categoryToTotalConsumption);
    }

    private void generateAndWrite(String outputFilename) {
        Option generator = getOption('g');
        Option writer = getOption('w');

        Composite root = generator.generate();
        writer.write(root, outputFilename);
    }

    private void readAndDisplay(String inputFilename) {
        Option reader = getOption('r');
        Option processor = getOption('p');
        Option displayer = getOption('d');

        if (Validator.areValidInputFileContents(inputFilename)) {
            Composite root = reader.readInputFile(inputFilename);
            Map<String, Double> categoryToTotalConsumption = processor.process(root);
            displayer.display(root, categoryToTotalConsumption);
        }
    }

    private void readAndWrite(String inputFilename, String outputFilename) {
        Option reader = getOption('r');
        Option writer = getOption('w');

        if (Validator.areValidInputFileContents(inputFilename)) {
            Composite root = reader.readInputFile(inputFilename);
            writer.write(root, outputFilename);
        }
    }
}
