import java.io.*;
import java.util.*;

/**
 * A simple address book application.
 * @author Dave and Tanaka
 */
public class AddressBookApp 
{
    /** Used to obtain user input. */
    private static Scanner input = new Scanner(System.in);

    /** Private fields. */
    private AddressBook addressBook;
    private HashMap<Integer, Option> options;

    /** Main method. */
    public static void main(String[] args)
    {
        String fileName;
        
        System.out.print("Enter address book filename: ");
        fileName = input.nextLine();
        
        try
        {
            AddressBookApp addressBookApp = new AddressBookApp(fileName);
            addressBookApp.addOption(1, new SearchByName(addressBookApp.getAddressBook()));
            addressBookApp.addOption(2, new SearchByEmail(addressBookApp.getAddressBook()));
            addressBookApp.addOption(3, new GetAll(addressBookApp.getAddressBook()));
            addressBookApp.showMenu();
        }
        catch(IllegalArgumentException e)
        {
            System.out.println("Could not read from " + fileName + ": " + e.getMessage());
        }
    }

    /** Constructors. */
    private AddressBookApp(String filename) {
        try {
            addressBook = readAddressBook(filename);
            options = new HashMap<Integer, Option>();
        }
        catch (IOException e) {
            throw new IllegalArgumentException("Invalid filename.");
        }
    }

    /** Setters. */
    private void addOption(int label, Option option) {
        if (options.containsKey(label)) {
            throw new IllegalArgumentException("Label already associated with option");
        }
        else {
            options.put(label, option);
        }
    }

    /** Getters. */
    private AddressBook getAddressBook() {
        return addressBook;
    }

    /** Operators. */
    /**
     * Show the main menu, offering the user options to (1) search entries by 
     * name, (2) search entries by email, or (3) quit.
     *
     * @param addressBook The AddressBook object to search.
     */
    private void showMenu()
    {
        boolean done = false;
        while(!done)
        {
            System.out.print("Menu\n\n" +
                "1. Search By Name\n" + 
                "2. Search By Email\n" +
                "3. Get All\n" +
                "0. Quit\n\n" +
                "Selection: ");

            String emailAddresses;
            try
            {
                int selection = Integer.parseInt(input.nextLine());
                System.out.println(); // Formatting purposes

                if (selection == 0) {
                    done = true;
                }
                else {
                    Option option = options.get(selection);
                    if (option == null) {
                        System.out.println("No option corresponding to " + selection + " found");
                    }
                    else {
                        if (option.requiresText()) {
                            System.out.print("Search term: ");
                            String searchTerm = input.nextLine();

                            // Find an entry by search term
                            emailAddresses = option.doOption(searchTerm);
                        }
                        else {
                            emailAddresses = option.doOption();
                        }
                        System.out.println("Email addresses:");
                        System.out.println(emailAddresses);
                        System.out.println(); // Formatting purposes
                    }
                }
            }
            catch(NumberFormatException e)
            {
                // The user entered something non-numerical.
                System.out.println("Enter a number");
            }
        }
    }

    /**
     * Read the address book file, containing all the names and email addresses.
     *
     * @param fileName The name of the address book file.
     * @return A new AddressBook object containing all the information.
     * @throws IOException If the file cannot be read.
     */
    private AddressBook readAddressBook(String fileName) throws IOException
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
}