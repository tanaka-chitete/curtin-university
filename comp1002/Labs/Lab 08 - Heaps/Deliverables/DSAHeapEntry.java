/*
 * NAME: DSAHeapEntry
 * CREATOR: Tanaka Chitete
 * STUDENT_ID: 20169321
 * UNIT: COMP1002
 * PURPOSE: Implement entries for DSAHeap
 * CREATION: 16/10/2020
 * LAST MODIFICATION: 16/10/2020
 */

public class DSAHeapEntry {
    // PRIVATE CLASS FIELDS

    int priority;
    Object value;

    // PROTECTED CLASS FIELDS

    // NONE

    // CONSTRUCTORS

    /*
     * ALTERNATE CONSTRUCTOR
     * IMPORT(S): inPriority (int), inValue (Object)
     * EXPORT(S): Address of new DSAHeapEntry
     * PURPOSE: Create new DSAHeapEntry in alternate state
     * CREATION: 16/10/2020
     * LAST MODIFICATION: 16/10/2020
     */

    public DSAHeapEntry(int inPriority, Object inValue) {
        priority = inPriority;
        value = inValue;
    }

    // SETTERS (MUTATORS)

    // NONE

    // GETTERS (ACCESSORS)

    public int getPriority() {
        return priority;
    }

    public Object getValue() {
        return value;
    }
}
