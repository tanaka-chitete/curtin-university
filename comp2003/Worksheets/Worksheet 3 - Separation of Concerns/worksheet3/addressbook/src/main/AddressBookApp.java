package main;

import model.Extractor;
import controller.SearchByName;
import controller.SearchByEmail;
import controller.GetAll;
import view.UserInterface;
import view.Menu;

/**
 * A simple address book application.
 * @author Dave and Tanaka
 */

public class AddressBookApp {
    /** Main method. */
    public static void main(String[] args)
    {
        String fileName = UserInterface.userInput("Enter address book filename: ");
        
        try
        {
            Extractor extractor = new Extractor(fileName);
            extractor.addOption(1, new SearchByName(extractor.getAddressBook()));
            extractor.addOption(2, new SearchByEmail(extractor.getAddressBook()));
            extractor.addOption(3, new GetAll(extractor.getAddressBook()));
            Menu.showMenu(extractor);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Could not read from " + fileName + ": " + e.getMessage());
        }
    }    
}
