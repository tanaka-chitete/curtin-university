import java.util.*;

/**
 * Contains all the address book entries.
 * 
 * @author Tanaka Chitete
 */
public class AddressBook {
    // PRIVATE CLASS FIELDS

    private HashMap<String, Entry> nameHashedEntries; 
    private HashMap<String, Entry> emailAddressHashedEntries;

    /*
    * DEFAULT CONSTRUCTOR
    * IMPORT(S): None
    * EXPORT(S): Address of new AddressBook
    * PURPOSE: Create new AddressBook
    * CREATION: 10/03/2021
    * LAST MODIFICATION: 21/03/2021
    */

    public AddressBook() 
    {
        nameHashedEntries = new HashMap<String, Entry>();
        emailAddressHashedEntries = new HashMap<String, Entry>();   
    }

    // SETTERS

    public void add(String name, ArrayList<String> emailAddresses) 
    {
        Entry newEntry = new Entry(name, emailAddresses);

        // Maps new entry to name's name
        nameHashedEntries.put(name, newEntry);

        // Maps new entry to all of names's email addresses
        for (String emailAddress : emailAddresses) {
            emailAddressHashedEntries.put(emailAddress, newEntry);
        }
    }

    // GETTERS

    public Entry getEntryByName(String name) 
    {
        return nameHashedEntries.get(name);
    }

    public Entry getEntryByEmailAddress(String emailAddress) 
    {
        return emailAddressHashedEntries.get(emailAddress);
    }
}
