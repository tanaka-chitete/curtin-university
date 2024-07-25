import java.util.*;

public interface Collection<E> extends Iterable<E> {
    // Basic operations
    int size();
    boolean isEmpty();
    boolean contains(Object element);
    boolean add(E element); //optional
    boolean remove(Object element); //optional
    Iterator<E> iterator();

    // Bulk operations
    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c); //optional
    boolean removeAll(Collection<?> c); //optional
    boolean retainAll(Collection<?> c); //optional
    void clear(); //optional

    // Array operations
    Object[] toArray();
    <T> T[] toArray(T[] a);
}