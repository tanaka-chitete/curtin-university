public class SearchByEmail implements Option {
    private AddressBook addressBook;

    public SearchByEmail(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public boolean requiresText() {
        return true;
    }

    @Override
    public String doOption(String s) {
        // Finds an entry by email address and returns its emails.
        Entry gottenEntry = addressBook.getEntryByEmailAddress(s);

        String emailAddresses;
        if (gottenEntry == null) {
            emailAddresses = "No email addresses found";
        }
        else {
            // Obtained from Jon Skeet, StackOverflow
            // https://stackoverflow.com/questions/3395286/remove-last-character-of-a-stringbuilder/3395329
            // Accessed 21/03/2021
            StringBuilder stringBuilder = new StringBuilder();
            String prefix = "";
            for (String emailAddress : gottenEntry.getEmailAddresses()) {
                stringBuilder.append(prefix);
                prefix = ", ";
                stringBuilder.append(emailAddress);
            }
            // End of code obtained from above source
            emailAddresses = stringBuilder.toString();
        }

        return emailAddresses;
    }

    @Override
    public String doOption() {
        throw new UnsupportedOperationException();
    }
}
