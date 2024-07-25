// Adapted from Tanaka Chitete
// Accessed 22/10/2020

import java.util.*;
import java.io.*;

public class DSAHashSet {
    // NESTED CLASSES

    public class DSAHashSetEntry {    
        // PRIVATE CLASS FIELDS
    
        String value;
        int state;
    
        // CONSTRUCTORS
    
        /*
         * DEFAULT CONSTRUCTOR
         * IMPORT(S): NONE
         * EXPORT(S): Address of new DSAHashSetEntry
         * PURPOSE: Create new DSAHashSetEntry in default state
         * CREATION: 22/09/2020
         * LAST MODIFICATION: 22/10/2020
         */
    
        private DSAHashSetEntry() {
            value = null;
            state = NEVER_USED;
        }
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inValue (String)
         * EXPORT(S): Address of new DSAHashTableEntry
         * PURPOSE: Create new DSAHashTableEntry in alternate state
         * CREATION: 26/09/2020
         * LAST MODIFICATION: 22/10/2020
         */
    
        private DSAHashSetEntry(String inValue) {
            value = inValue;
            state = IN_USE;
        }
    }

    // PRIVATE CLASS CONSTANTS

    private static final int INITIAL_CAPACITY = 3;

    private static final int IN_USE = 0;
    private static final int WAS_USED = 1;
    private static final int NEVER_USED = 2;

    private static final double MIN_LOAD = 0.65;
    private static final double OPTIMAL_LOAD = 0.75;
    private static final double MAX_LOAD = 0.85;

    // PRIVATE CLASS FIELDS

    DSAHashSetEntry[] entries;
    int count;
    double load;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAHashSet
     * PURPOSE: Create new DSAHashSet in default state
     * CREATION: 22/10/2020
     * LAST MODIFICATION: 22/10/2020
     */

    public DSAHashSet() {
        entries = new DSAHashSetEntry[INITIAL_CAPACITY];
        count = 0;
        load = 0.0;

        // Instantiates entries
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DSAHashSetEntry();
        }
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inCapacity (int)
     * EXPORT(S): Address of new DSAHashSet
     * PURPOSE: Create new DSAHashSet in alternate state
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 22/10/2020
     */

    public DSAHashSet(int inCapacity) {
        // Ensures hash table has a load factor of ~0.75 after adding
        inCapacity = (int) Math.round((double) inCapacity / OPTIMAL_LOAD);
        // Ensures capacity is a prime
        inCapacity = _findNextPrime(inCapacity);

        entries = new DSAHashSetEntry[inCapacity];
        count = 0;
        load = 0.0;

        // Instantiates entries
        for (int i = 0; i < inCapacity; i++) {
            entries[i] = new DSAHashSetEntry();
        }
    }

    // SETTERS (MUTATORS)

    public void add(String inValue) {
        if (inValue == null) {
            throw new IllegalArgumentException("Cannot call add with a null value");
        }
        else {
            int foundEntryHash = _findEntry(inValue);
            if (foundEntryHash != -1) {
                throw new IllegalArgumentException("Cannot call add with an existing value");
            }
            else {
                _add(inValue);
            }
        }
    }

    // GETTERS (ACCESSORS)

    public boolean contains(String inValue) {
        boolean contains;
        if (inValue == null) {
            throw new IllegalArgumentException("Cannot call contains with a null value");
        }
        else {
            int foundEntryHash = _findEntry(inValue);
            if (foundEntryHash == -1) {
                contains = false;
            }
            else {
                contains = true;
            }
        }

        return contains;
    }

    public void remove(String inValue) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call remove on an empty hash set");
        }
        else if (inValue == null) {
            throw new IllegalArgumentException("Cannot call remove with a null value");
        }
        else {
            int foundEntryHash = _findEntry(inValue);
            if (foundEntryHash == -1) {
                throw new IllegalArgumentException("Cannot call remove with a non-existing value");
            }
            else {
                _remove(foundEntryHash);
            }
        }
    }

    public DSAHashSetEntry getEntry(String inValue) {
        DSAHashSetEntry foundEntry;
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call get on an empty hash table");
        }
        else if (inValue == null) {
            throw new IllegalArgumentException("Cannot call get with a null value");
        }
        else {
            int foundEntryHash = _findEntry(inValue);
            if (foundEntryHash == -1) {
                throw new IllegalArgumentException("Cannot call get with a non-existing value");
            }
            else {
                foundEntry = entries[foundEntryHash];
            }
        }

        return foundEntry;
    }

    public int getCount() {
        return count;
    }

    public double getLoad() {
        return load;
    }

    // OPERATORS

    public boolean isEmpty() {
        boolean isEmpty;

        if (count == 0) {
            isEmpty = true;
        }
        else {
            isEmpty = false;
        }

        return isEmpty;
    }

    public boolean isFull() {
        boolean isFull;

        if (count == entries.length) {
            isFull = true;
        }
        else {
            isFull = false;
        }

        return isFull;
    }

    public void read(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Cannot call read with a null " + 
                "filename");
        }
        else if (filename.length() < 4) {
            throw new IllegalArgumentException("Cannot call read with a " +
                "filename with less than 4 characters");
        }
        else {
            if (filename.substring(filename.length() - 4, 
                filename.length()).equals(".csv")) {
                entries = _read(filename);
            }
            else {
                throw new IllegalArgumentException("Cannot call read " + 
                    "with a filename without a .csv extension");
            }
        }
    } 

    public void write(String filename) {
        if (filename == null) {
            throw new IllegalArgumentException("Cannot call write with a " +
                "null filename");
        }
        else if (filename.length() < 4 ) {
            throw new IllegalArgumentException("Cannot call write with a " +
                "filename with less than 4 characters");
        }
        else {
            if (filename.substring(filename.length() - 4, 
                filename.length()).equals(".csv")) {
                _write(filename);
            }
            else {
                throw new IllegalArgumentException("Cannot call write with a " + 
                    "filename without a .csv extension");
            }
        }
    }    

    public String toString() {
        String entriesString = "[";
        int remainingEntries = count;
        if (count != 0) {
            for (int i = 0; i < entries.length; i++) {
                DSAHashSetEntry currentEntry = entries[i];
                if (currentEntry.value != null) {
                    if (remainingEntries == 1) {
                        entriesString += "\"" + currentEntry.value + "\"";
                        remainingEntries--;
                    }
                    else {
                        entriesString += "\"" + currentEntry.value + "\", ";
                        remainingEntries--;
                    }   
                }
            }
        }
        entriesString += "]";

        return entriesString;
    }

    // PRIVATE SUBMODULES

    private void _add(String inValue) {
        int hash = _hashValue(inValue);
        int step = _toStep(hash);

        boolean foundFreeSlot = false;
        while (!foundFreeSlot) {
            // Prepares to stop if free slot has been found
            if (entries[hash].state != IN_USE) {
                foundFreeSlot = true;
            }
            // Continues probing for free slot
            else {
                hash += step;
                hash %= entries.length;
            }
        }

        // Adds new entry at previously found free slot
        DSAHashSetEntry newEntry = new DSAHashSetEntry(inValue);
        entries[hash] = newEntry;

        // Updates load factor
        count++;
        load = (double) count / (double) entries.length;

        if (load > MAX_LOAD) {
            entries = _resize();
        }
    }

    private void _remove(int foundEntryHash) {
        // Reinstantiates slot of previously found entry
        entries[foundEntryHash].value = null;
        entries[foundEntryHash].state = WAS_USED;

        // Updates load factor
        count--;
        load = (double) count / (double) entries.length;

        // Checks whether to shrink hash table
        if (load < MIN_LOAD) {
            entries = _resize();
        }
    }

    private int _findEntry(String inValue) {
        int hash = _hashValue(inValue);
        int originalHash = hash;
        int step = _toStep(hash);

        boolean foundEntry = false;
        boolean checkedAllEntries = false;
        while (!foundEntry && !checkedAllEntries) {
            // Prepares to stop if slot was never used
            if (entries[hash].state == NEVER_USED) {
                checkedAllEntries = true;
            }
            // Checks if current key is target key
            else if (entries[hash].value.equals(inValue)) {
                foundEntry = true;
            }
            // Continues probing for target entry
            else {
                hash += step;
                hash %= entries.length;
                // Prepares to stop if all entries have been checked
                if (hash == originalHash) {
                    checkedAllEntries = true;
                }
            }
        }

        if (!foundEntry) {
            hash = -1;
        }

        return hash;
    }

    private int _findNextPrime(int start) {
        int prime;

        // Ensures prime is odd as even numbers are never prime
        if (start % 2 == 0) {
            prime = start - 1;
        }
        else {
            prime = start;
        }
        // Ensures we include start prime
        prime -= 2;

        // Checks if prime candidate is actually a prime
        boolean isPrime;
        do {
            // Prepares to check next prime candidate
            prime += 2;

            int i = 3;
            isPrime = true;
            double primeRoot = Math.sqrt((double) prime);
            do {
                if (prime % i == 0) {
                    isPrime = false;
                }
                else {
                    // Prepares to check primality with next odd
                    i += 2;
                }
            }
            while (i <= primeRoot && isPrime);
        }
        while (!isPrime);

        return prime;
    }

    private int _hashValue(String inValue) {
        long valueHashLong = 0;

        for (int i = 0; i < inValue.length(); i++) {
            valueHashLong += (31 * valueHashLong) + (long) inValue.charAt(i);
        }
        int valueHashInt = (int) (valueHashLong % (long) entries.length);

        return valueHashInt;
    }

    private int _toStep(int valueHash) {
        return 5 - (valueHash % 5);
    }

    private DSAHashSetEntry[] _resize() {
        DSAHashSetEntry[] entriesCopy = Arrays.copyOf(entries, entries.length);

        // Ensures hash table has a load factor of ~0.75 after re-adding
        int capacity = (int) Math.round((double) count / OPTIMAL_LOAD);

        // Ensures capacity is a prime
        capacity = _findNextPrime(capacity);

        // Prevents arrays of size less than or equal to 0 from being initialised
        if (capacity < 1) {
            capacity = INITIAL_CAPACITY;
        }

        // Recalculates load factor
        load = (double) count / (double) capacity;

        entries = new DSAHashSetEntry[capacity];
        // Instantiates entries
        for (int i = 0; i < capacity; i++) {
            entries[i] = new DSAHashSetEntry();
        }

        // Re-hashes entries
        for (int i = 0; i < entriesCopy.length; i++) {
            String value = entriesCopy[i].value;
            int state = entriesCopy[i].state;

            if (state == IN_USE) {
                _addAfterResize(value);
            }
        }

        return entries;
    }

    private void _addAfterResize(String inValue) {
        int hash = _hashValue(inValue);
        int step = _toStep(hash);

        boolean foundSlot = false;
        while (!foundSlot) {
            // Prepares to stop if free slot has been found
            if (entries[hash].state != IN_USE) {
                foundSlot = true;
            }
            // Continues probing for free slot
            else {
                hash += step;
                hash %= entries.length;
            }
        }

        // Adds entry into hash table at free slot
        DSAHashSetEntry newEntry = new DSAHashSetEntry(inValue);
        entries[hash] = newEntry;
    }

    private DSAHashSetEntry[] _read(String filename) {
        FileInputStream fileStream = null;
        InputStreamReader streamReader;
        BufferedReader bufferedReader;

        String line;

        try {
            // Ensures hash table has a load factor of ~0.75 after reading
            int lineCount = _countLines(filename); 
            int capacity = (int) Math.round((double) lineCount / OPTIMAL_LOAD);
            // Ensures capacity is a prime
            capacity = _findNextPrime(capacity);

            fileStream = new FileInputStream(filename);
            streamReader = new InputStreamReader(fileStream);
            bufferedReader = new BufferedReader(streamReader);
            line = bufferedReader.readLine();

            // Reads lines
            while (line != null) {
                add(line);

                line = bufferedReader.readLine();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return entries;
    }

    private int _countLines(String filename) {
        FileInputStream fileStream = null;
        InputStreamReader streamReader;
        BufferedReader bufferedReader;
        String line;

        int count = 0;

        try {
            fileStream = new FileInputStream(filename);
            streamReader = new InputStreamReader(fileStream);
            bufferedReader = new BufferedReader(streamReader);
            line = bufferedReader.readLine();

            while (line != null) {
                count++;
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return count;
    } 

    private void _write(String filename) {
        FileOutputStream fileStream = null;
        PrintWriter printWriter;

        try {
            fileStream = new FileOutputStream(filename);
            printWriter = new PrintWriter(fileStream);

            for (int i = 0; i < entries.length; i++) {
                DSAHashSetEntry currentEntry = entries[i];
                
                if (currentEntry.state == IN_USE) {
                    String value = currentEntry.value;
                    printWriter.print(value + "\n");
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }    
}
