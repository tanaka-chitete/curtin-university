// Adapted from Tanaka Chitete
// Accessed 03/11/2020

import java.util.*;

public class DSAHashTable<K, V> extends AbstractMap<K, V> {
    // NESTED CLASSES

    public class DSAHashTableEntry<A, B> {    
        // PRIVATE CLASS FIELDS
    
        A key;
        B value;
        int state;

        // CONSTRUCTORS
    
        /*
         * DEFAULT CONSTRUCTOR
         * IMPORT(S): NONE
         * EXPORT(S): Address of new DSAHashSetEntry
         * PURPOSE: Create new DSAHashSetEntry in default state
         * CREATION: 22/09/2020
         * LAST MODIFICATION: 03/11/2020
         */
    
        private DSAHashTableEntry() {
            key = null;
            value = null;
            state = NEVER_USED;
        }
    
        /*
         * ALTERNATE CONSTRUCTOR
         * IMPORT(S): inValue (String)
         * EXPORT(S): Address of new DSAHashTableEntry
         * PURPOSE: Create new DSAHashTableEntry in alternate state
         * CREATION: 26/09/2020
         * LAST MODIFICATION: 03/11/2020
         */
    
        private DSAHashTableEntry(A inKey, B inValue) {
            key = inKey;
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

    DSAHashTableEntry<K, V>[] entries;
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

    @SuppressWarnings("unchecked")
    public DSAHashTable() {
        entries = new DSAHashTableEntry[INITIAL_CAPACITY];
        count = 0;
        load = 0.0;

        // Instantiates entries
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DSAHashTableEntry<K, V>();
        }
    }
 
    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inCapacity (int)
     * EXPORT(S): Address of new DSAHashTable
     * PURPOSE: Create new DSAHashTable in alternate state
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 26/09/2020
     */

    @SuppressWarnings("unchecked")
    public DSAHashTable(int inCapacity) {
        // Ensures hash table has a load factor of ~0.75 after adding
        inCapacity = (int) Math.round((double) inCapacity / OPTIMAL_LOAD);
        // Ensures capacity is a prime
        inCapacity = _findNextPrime(inCapacity);

        entries = new DSAHashTableEntry[inCapacity];
        count = 0;
        load = 0.0;

        // Instantiates entries
        for (int i = 0; i < entries.length; i++) {
            entries[i] = new DSAHashTableEntry<K, V>();
        }
    }

    // SETTERS (MUTATORS)

    @Override
    public V put(K inKey, V inValue) {
        if (inKey == null) {
            throw new IllegalArgumentException("Cannot call put with a null key");
        }
        else {
            String inKeyString = inKey.toString();
            int foundEntryHash = _findEntry(inKeyString);
            if (foundEntryHash != -1) {
                throw new IllegalArgumentException("Cannot call put with an existing key");
            }
            else {
                _put(inKey, inValue);
            }
        }

        return inValue;
    }

    // GETTERS (ACCESSORS)

    @Override
    public V remove(Object inKey) {
        V removedEntryValue;

        if (isEmpty()) {
            throw new IllegalStateException("Cannot call remove on an empty hash table");
        }
        else if (inKey == null) {
            throw new IllegalArgumentException("Cannot call remove with a null key");
        }
        else {
            String inKeyString = inKey.toString();
            int foundEntryHash = _findEntry(inKeyString);
            if (foundEntryHash == -1) {
                throw new IllegalArgumentException("Cannot call remove with a non-existing key");
            }
            else {
                removedEntryValue = _remove(foundEntryHash);
            }
        }

        return removedEntryValue;
    }

    @Override
    public V get(Object inKey) {
        DSAHashTableEntry<K, V> foundEntry;
        
        if (isEmpty()) {
            throw new IllegalStateException("Cannot call get on an empty hash table");
        }
        else if (inKey == null) {
            throw new IllegalArgumentException("Cannot call get with a null key");
        }
        else {
            String inKeyString = inKey.toString();
            int foundEntryHash = _findEntry(inKeyString);
            if (foundEntryHash == -1) {
                throw new IllegalArgumentException("Cannot call get with a non-existing key");
            }
            else {
                foundEntry = entries[foundEntryHash];
            }
        }

        V gottenEntryValue = foundEntry.value;

        return gottenEntryValue;
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return entries.length;
    }

    public double getLoad() {
        return load;
    }

    // OPERATORS

    public Set <Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
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

    // OPERATORS

    public String toString() {
        String entriesString = "{";
        int remainingEntries = count;
        if (count != 0) {
            for (int i = 0; i < entries.length; i++) {
                DSAHashTableEntry<K, V> currentEntry = entries[i];
                if (currentEntry.value != null) {
                    if (remainingEntries == 1) {
                        entriesString += "\"" + currentEntry.key + "\": " + "\"" + 
                            currentEntry.value + "\"";
                        remainingEntries--;
                    }
                    else {
                        entriesString += "\"" + currentEntry.key + "\": " + "\"" + 
                            currentEntry.value + "\", ";
                        remainingEntries--;
                    }   
                }
            }
        }
        entriesString += "}";

        return entriesString;
    }

    // PRIVATE SUBMODULES

    private void _put(K inKey, V inValue) {
        String inKeyString = inKey.toString();

        int hash = _hashKey(inKeyString);
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
        DSAHashTableEntry<K, V> newEntry = new DSAHashTableEntry<K, V>(inKey, inValue);
        entries[hash] = newEntry;

        // Updates load factor
        count++;
        load = (double) count / (double) entries.length;

        if (load > MAX_LOAD) {
            entries = _resize();
        }
    }

    private V _remove(int foundEntryHash) {
        V removedEntryValue = entries[foundEntryHash].value;

        // Reinstantiates slot of previously found entry
        entries[foundEntryHash].key = null;
        entries[foundEntryHash].value = null;
        entries[foundEntryHash].state = WAS_USED;

        // Updates load factor
        count--;
        load = (double) count / (double) entries.length;

        // Checks whether to shrink hash table
        if (load < MIN_LOAD) {
            entries = _resize();
        }

        return removedEntryValue;
    }

    private int _findEntry(String inKey) {
        int hash = _hashKey(inKey);
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
            else if (entries[hash].key.equals(inKey)) {
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

    private int _hashKey(String inKeyString) {
        long keyHashLong = 0;

        for (int i = 0; i < inKeyString.length(); i++) {
            keyHashLong += (31 * keyHashLong) + (long) inKeyString.charAt(i);
        }
        int keyHashInt = (int) (keyHashLong % (long) entries.length);

        return keyHashInt;
    }

    private int _toStep(int keyHashInt) {
        return 5 - (keyHashInt % 5);
    }

    @SuppressWarnings("unchecked")
    private DSAHashTableEntry<K, V>[] _resize() {
        DSAHashTableEntry<K, V>[] entriesCopy = Arrays.copyOf(entries, entries.length);

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

        entries = new DSAHashTableEntry[capacity];
        // Instantiates entries
        for (int i = 0; i < capacity; i++) {
            entries[i] = new DSAHashTableEntry<K, V>();
        }

        // Re-hashes entries
        for (int i = 0; i < entriesCopy.length; i++) {
            K key = entriesCopy[i].key;
            V value = entriesCopy[i].value;
            int state = entriesCopy[i].state;

            if (state == IN_USE) {
                _addAfterResize(key, value);
            }
        }

        return entries;
    }

    private void _addAfterResize(K inKey, V inValue) {
        String inKeyString = inKey.toString();
        
        int hash = _hashKey(inKeyString);
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
        DSAHashTableEntry<K, V> newEntry = new DSAHashTableEntry<K, V>(inKey, inValue);
        entries[hash] = newEntry;
    }
}
