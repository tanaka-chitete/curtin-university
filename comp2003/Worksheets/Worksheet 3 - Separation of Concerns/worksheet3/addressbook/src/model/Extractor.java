package model;

import java.io.IOException;
import java.util.HashMap;

import controller.FileIO;
import controller.Option;


public class Extractor 
{
    /** Private fields. */
    private AddressBook addressBook;
    private HashMap<Integer, Option> options;

    /** Constructors. */
    public Extractor(String filename) {
        try {
            addressBook = FileIO.readAddressBook(filename);
            options = new HashMap<Integer, Option>();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Invalid filename.");
        }
    }

    /** Setters. */
    public void addOption(int label, Option option) {
        if (options.containsKey(label)) {
            throw new IllegalArgumentException("Label already associated with option");
        }
        else {
            options.put(label, option);
        }
    }

    /** Getters. */
    public Option getOption(Integer ID) {
        return options.get(ID);
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }
}