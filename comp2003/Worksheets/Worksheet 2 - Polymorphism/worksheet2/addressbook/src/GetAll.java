import java.util.*;

public class GetAll implements Option {
    private AddressBook addressBook;

    public GetAll(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public boolean requiresText() {
        return false;
    }

    @Override 
    public String doOption(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String doOption() {
        HashMap<String, Entry> entries = addressBook.getAllEntries();

        StringBuilder allEmailAddressesStringBuilder = new StringBuilder();
        String allEmailAddressesPrefix = "";
        for (HashMap.Entry<String, Entry> hashMapEntry : entries.entrySet()) {
            allEmailAddressesStringBuilder.append(allEmailAddressesPrefix);

            Entry addressBookEntry = hashMapEntry.getValue();
            // Adapted from Jon Skeet, StackOverflow
            // https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder/3395329
            // Accessed 21/03/2021
            StringBuilder entryEmailAddressStringBuilder = new StringBuilder();
            entryEmailAddressStringBuilder.append(addressBookEntry.getName() + ": ");
            String entryEmailAddressesPrefix = "";
            for (String emailAddress : addressBookEntry.getEmailAddresses()) {
                entryEmailAddressStringBuilder.append(entryEmailAddressesPrefix);
                entryEmailAddressesPrefix = ", ";
                entryEmailAddressStringBuilder.append(emailAddress);
            }
            // End of code adapted from above source
            // Add email addresses of current entry to string builder of all email addresses
            allEmailAddressesPrefix = "\n";
            allEmailAddressesStringBuilder.append(entryEmailAddressStringBuilder);
        }

        return allEmailAddressesStringBuilder.toString();
    }
}
