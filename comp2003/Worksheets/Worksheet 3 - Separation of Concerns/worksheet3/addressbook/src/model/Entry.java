package model;

import java.util.*;
        
/**
 * Represents a single address book entry.
 * 
 * @author Tanaka Chitete
 */
public class Entry 
{
    // PRIVATE CLASS FIELDS

    private String name;
    private ArrayList<String> emailAddresses;

    /*
    * ALTERNATE CONSTRUCTOR
    * IMPORT(S): name (String), emailAddresses (ArrayList<String>)
    * EXPORT(S): Address of new Entry
    * PURPOSE: Create new Entry
    * CREATION: 10/03/2021
    * LAST MODIFICATION: 10/03/2021
    */

    public Entry(String name, ArrayList<String> emailAddresses) {
        this.name = name;
        this.emailAddresses = emailAddresses;
    }

    // GETTERS

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getEmailAddresses() {
        return this.emailAddresses;
    }
}
