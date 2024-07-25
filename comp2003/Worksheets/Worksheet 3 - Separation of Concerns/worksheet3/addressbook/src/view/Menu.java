package view;

import model.Extractor;
import controller.Option;

public class Menu {
    /**
     * Show the main menu, offering the user options to (1) search entries by 
     * name, (2) search entries by email, or (3) quit.
     *
     * @param addressBook The AddressBook object to search.
     */
    public static void showMenu(Extractor application)
    {
        boolean done = false;
        while(!done)
        {
            System.out.print("Menu\n\n" +
                "1. Search By Name\n" + 
                "2. Search By Email\n" +
                "3. Get All\n" +
                "0. Quit\n\n");

            String emailAddresses;
            try
            {
                int selection = Integer.parseInt(UserInterface.userInput("Selection: "));
                System.out.println(); // Formatting purposes

                if (selection == 0) {
                    done = true;
                }
                else {
                    Option option = application.getOption(selection);
                    if (option == null) {
                        System.out.println("No option corresponding to " + selection + " found");
                    }
                    else {
                        if (option.requiresText()) {
                            String searchTerm = UserInterface.userInput("Search term: ");

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
}
