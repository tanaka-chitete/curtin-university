public class DSAHashTableEntry {
    // PRIVATE CLASS CONSTANTS

    private static final int IN_USE = 0;
    private static final int NEVER_USED = 2;

    // PRIVATE CLASS FIELDS

    String key;
    Object value;
    int state;

    // CONSTRUCTORS

    /*
     * DEFAULT CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new DSAHashTableEntry
     * PURPOSE: Create new DSAHashTableEntry in default state
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 26/09/2020
     */

    public DSAHashTableEntry() {
        key = "";
        value = null;
        state = NEVER_USED;
    }

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inKey (String), inValue (Object)
     * EXPORT(S): Address of new DSAHashTableEntry
     * PURPOSE: Create new DSAHashTableEntry in alternate state
     * CREATION: 26/09/2020
     * LAST MODIFICATION: 26/09/2020
     */

    public DSAHashTableEntry(String inKey, Object inValue) {
        key = inKey;
        value = inValue;
        state = IN_USE;
    }

    // SETTERS (MUTATORS)

    public void setKey(String inKey) {
        key = inKey;
    }

    public void setValue(Object inValue) {
        value = inValue;
    }

    public void setState(int inState) {
        state = inState;
    }

    // GETTERS (ACCESSORS)

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public int getState() {
        return state;
    }
}
