package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;

import model.AddressBook;

public class FileIO {
    /**
     * Read the address book file, containing all the names and email addresses.
     *
     * @param fileName The name of the address book file.
     * @return A new AddressBook object containing all the information.
     * @throws IOException If the file cannot be read.
     */

    public static AddressBook readAddressBook(String fileName) throws IOException
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
