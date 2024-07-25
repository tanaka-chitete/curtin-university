import java.io.*;
import java.util.*;

/**
 * A simple address book application.
 * @author Dave and ...
 */
public class AddressBookApp 
{
    /** Used to obtain user input. */
    private static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        String fileName, entryName;
        
        System.out.print("Enter address book filename: ");
        fileName = input.nextLine();
        
        try
        {
            AddressBook addressBook = readAddressBook(fileName);
            showMenu(addressBook);
        }
        catch(IOException e)
        {
            System.out.println("Could not read from " + fileName + ": " + e.getMessage());
        }
    }
    
    /**
     * Read the address book file, containing all the names and email addresses.
     *
     * @param fileName The name of the address book file.
     * @return A new AddressBook object containing all the information.
     * @throws IOException If the file cannot be read.
     */
    private static AddressBook readAddressBook(String fileName) throws IOException
    {
        AddressBook addressBook = new AddressBook();
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = reader.readLine();
        while(line != null)
        {
            String[] splitLine = line.split(":");

            String name = splitLine[0];
            ArrayList<String> emailAddresses = new ArrayList<String>();
            // Adds email addresses from splitLine[1] onwards to emailAddresses ArrayList
            for (int i = 1; i < splitLine.length; i++) {
                String currEmailAddress = splitLine[i];
                emailAddresses.add(currEmailAddress);
            }

            addressBook.add(name, emailAddresses);
            
            line = reader.readLine();
        }
        reader.close();
        
        return addressBook;
    }
    
    /**
     * Show the main menu, offering the user options to (1) search entries by 
     * name, (2) search entries by email, or (3) quit.
     *
     * @param addressBook The AddressBook object to search.
     */
    private static void showMenu(AddressBook addressBook)
    {
        boolean done = false;
        while(!done)
        {
            int option;
            System.out.println("(1) Search by name, (2) Search by email, (3) Quit");
            
            try
            {
                Entry gottenEntry;
                switch(Integer.parseInt(input.nextLine()))
                {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = input.nextLine();
                        
                        // Finds an entry by name and displays its emails.
                        gottenEntry = addressBook.getEntryByName(name);
                        System.out.println("Name: " + gottenEntry.getName());
                        System.out.println("Email addresses:");
                        for (String emailAddress : gottenEntry.getEmailAddresses()) {
                            System.out.println(" • " + emailAddress);
                        }
                        break;
                        
                    case 2:
                        System.out.print("Enter email address: ");
                        String email = input.nextLine();
                        
                        // Finds an entry by email and displays its emails.
                        gottenEntry = addressBook.getEntryByEmailAddress(email);
                        System.out.println("Email addresses:");
                        System.out.println("Name: " + gottenEntry.getName());
                        for (String emailAddress : gottenEntry.getEmailAddresses()) {
                            System.out.println(" • " + emailAddress);
                        }
                        break;
                        
                    case 3:
                        done = true;
                        break;
                }
            }
            catch(NumberFormatException e)
            {
                // The user entered something non-numerical.
                System.out.println("Enter a number");
            }
        }
    }
}
