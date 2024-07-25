import java.util.*;
import java.io.*;

public class DSAHashTable {
    // PRIVATE CLASS CONSTANTS

    private static final int IN_USE = 0;
    private static final int WAS_USED = 1;
    private static final int NEVER_USED = 2;

    private static final double MIN_LOAD = 0.65;
    private static final double OPTIMAL_LOAD = 0.76;
    private static final double MAX_LOAD = 0.85;

    // PRIVATE CLASS FIELDS

    DSAHashTableEntry[] entries;
    int count;
    int capacity;
    double load;

    // CONSTRUCTORS

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inCapacity (int)
     * EXPORT(S): Address of new DSAHashTable
     * PURPOSE: Create new DSAHashTable in alternate state
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 26/09/2020
     */

    public DSAHashTable(int inCapacity) {
        // Ensures hash table has a load factor of ~0.75 after adding
        inCapacity = (int) Math.round((double) inCapacity / OPTIMAL_LOAD);
        // Ensures capacity is a prime
        inCapacity = _findNextPrime(inCapacity);

        entries = new DSAHashTableEntry[inCapacity];
        count = 0;
        capacity = inCapacity;
        load = 0.0;

        // Instantiates entries
        for (int i = 0; i < capacity; i++) {
            entries[i] = new DSAHashTableEntry();
        }
    }

    // SETTERS (MUTATORS)

    public void addEntry(String inKey, Object inValue) {
        if (inKey == null) {
            throw new IllegalArgumentException("Cannot call addEntry with a " + 
                "null key");
        }
        else {
            _addEntry(inKey, inValue);
            // Checks whether to expand hash table
            if (load > MAX_LOAD) {
                entries = _resize();
            }
        }
    }

    public void removeEntry(String inKey) {
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call removeEntry on an " + 
                "empty hash table");
        }
        else if (inKey == null) {
            throw new IllegalArgumentException("Cannot call removeEntry with " + 
                "a null key");
        }
        else {
            int foundEntryHash = _findEntry(inKey);
            // Reinstantiates slot of previously found entry
            entries[foundEntryHash].setKey("");
            entries[foundEntryHash].setValue(null);
            entries[foundEntryHash].setState(WAS_USED);

            // Updates load factor
            count--;
            load = (double) count / (double) capacity;

            // Checks whether to shrink hash table
            if (load < MIN_LOAD) {
                entries = _resize();
            }
        }
    }

    // GETTERS (ACCESSORS)

    public DSAHashTableEntry getEntry(String inKey) {
        int foundEntryHash;
        DSAHashTableEntry foundEntry;
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call getEntry on an " + 
                "empty hash table");
        }
        else if (inKey == null) {
            throw new IllegalArgumentException("Cannot call getEntry with a " + 
                "null key");
        }
        else {
            foundEntryHash = _findEntry(inKey);
            foundEntry = entries[foundEntryHash];
        }

        return foundEntry;
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getLoad() {
        return load;
    }

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

        if (count == capacity) {
            isFull = true;
        }
        else {
            isFull = false;
        }

        return isFull;
    }

    // OPERATORS

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

    public void display() {
        for (int i = 0; i < capacity; i++) {
            DSAHashTableEntry entry = entries[i];

            if (entry.getState() == NEVER_USED) {
                System.out.println("NEVER USED");
            }
            else if (entry.getState() == WAS_USED) {
                System.out.println("WAS USED");
            }
            else {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    // PRIVATE SUBMODULES

    private void _addEntry(String inKey, Object inValue) {
        int hash = _hashKey(inKey);
        int step = _hashStep(hash);

        boolean foundSlot = false;
        while (!foundSlot) {
            // Prepares to stop if free slot has been found
            if (entries[hash].getState() != IN_USE) {
                foundSlot = true;
            }
            // Continues probing for free slot
            else {
                hash += step;
                hash %= capacity;
            }
        }

        // Adds new entry at previously found free slot
        DSAHashTableEntry newEntry = new DSAHashTableEntry(inKey, inValue);
        entries[hash] = newEntry;

        // Updates load factor
        count++;
        load = (double) count / (double) capacity;
    }

    private int _findEntry(String inKey) {
        int hash = _hashKey(inKey);
        int originalHash = hash;
        int step = _hashStep(hash);

        boolean foundEntry = false;
        boolean checkedAllEntries = false;
        while (!foundEntry && !checkedAllEntries) {
            // Prepares to stop if slot was never used
            if (entries[hash].getState() == NEVER_USED) {
                checkedAllEntries = true;
            }
            // Checks if current key is target key
            else if (entries[hash].getKey().equals(inKey)) {
                foundEntry = true;
            }
            // Continues probing for target entry
            else {
                hash += step;
                hash %= capacity;
                // Prepares to stop if all entries have been checked
                if (hash == originalHash) {
                    checkedAllEntries = true;
                }
            }
        }

        if (!foundEntry) {
            throw new IllegalArgumentException("Cannot call removeEntry with " + 
                "a key that was not previously assigned to an entry");
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

    private int _hashKey(String inKey) {
        int keyHash = 0;

        for (int i = 0; i < inKey.length(); i++) {
            keyHash += (31 * keyHash) + (int) inKey.charAt(i);
        }

        return keyHash % capacity;
    }

    private int _hashStep(int keyHash) {
        return 5 - (keyHash % 5);
    }

    private DSAHashTableEntry[] _resize() {
        DSAHashTableEntry[] entriesCopy = Arrays.copyOf(entries, capacity);

        // Ensures hash table has a load factor of ~0.75 after re-adding
        capacity = (int) Math.round((double) count / OPTIMAL_LOAD);
        // Ensures capacity is a prime
        capacity = _findNextPrime(capacity);

        // Recalculates load factor
        load = (double) count / (double) capacity;

        entries = new DSAHashTableEntry[capacity];

        // Instantiates entries
        for (int i = 0; i < capacity; i++) {
            entries[i] = new DSAHashTableEntry();
        }

        // Re-hashes entries
        for (int i = 0; i < entriesCopy.length; i++) {
            String key = entriesCopy[i].getKey();
            Object value = entriesCopy[i].getValue();
            int state = entriesCopy[i].getState();

            if (state == IN_USE) {
                _resizeAddEntry(key, value);
            }
        }

        return entries;
    }

    private void _resizeAddEntry(String inKey, Object inValue) {
        int hash = _hashKey(inKey);
        int step = _hashStep(hash);

        boolean foundSlot = false;
        while (!foundSlot) {
            // Prepares to stop if free slot has been found
            if (entries[hash].getState() != IN_USE) {
                foundSlot = true;
            }
            // Continues probing for free slot
            else {
                hash += step;
                hash %= capacity;
            }
        }

        // Adds entry into hash table at free slot
        DSAHashTableEntry newEntry = new DSAHashTableEntry(inKey, inValue);
        entries[hash] = newEntry;
    }

    private DSAHashTableEntry[] _read(String filename) {
        FileInputStream fileStream = null;
        InputStreamReader streamReader;
        BufferedReader bufferedReader;

        String line;
        String[] splitLine = new String[2];

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
                splitLine = line.split(",");
                String key = splitLine[0];
                Object value = splitLine[1];
                addEntry(key, value);

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
        String ln;

        int count = 0;

        try {
            fileStream = new FileInputStream(filename);
            streamReader = new InputStreamReader(fileStream);
            bufferedReader = new BufferedReader(streamReader);
            ln = bufferedReader.readLine();

            while (ln != null) {
                count++;
                ln = bufferedReader.readLine();
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

            for (int i = 0; i < capacity; i++) {
                DSAHashTableEntry currentEntry = entries[i];
                
                if (currentEntry.getState() == IN_USE) {
                    String key = currentEntry.getKey();
                    Object value = currentEntry.getValue();
                    printWriter.print(key + "," + value + "\n");
                }
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
