/*
 * NAME: Set
 * AUTHOR: Tanaka Chitete
 * PURPOSE: Implement generic Set
 * CREATION: 22/09/2020
 * LAST MODIFICATION: 06/03/2021
 */

import java.util.*;

public class Set<E> extends AbstractSet<E> {
    // PRIVATE CLASS FIELDS

    private HashMap<E, E> set;

    // CONSTRUCTORS

    /*
     * CONSTRUCTOR
     * IMPORT(S): NONE
     * EXPORT(S): Address of new Set
     * PURPOSE: Create new Set
     * CREATION: 22/10/2020
     * LAST MODIFICATION: 06/03/2021
     */

    public Set() {
        this.set = new HashMap<E, E>();
    }

    // SETTERS (MUTATORS)

    @Override
    public boolean add(E element) {
        this.set.put(element, element);
        return true; // put() always associates K with V
    }

    // GETTERS (ACCESSORS)

    @Override
    public boolean contains(Object element) {
        return this.set.containsKey(element);
    }

    @Override
    public boolean remove(Object element) {
        return this.set.remove(element, element);
    }

    @Override
    public int size() {
        return this.set.size();
    }

    // OPERATORS

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return this.set.values().toString();
    }

    @Override
    public boolean isEmpty() {
        return this.set.isEmpty();
    }
}
