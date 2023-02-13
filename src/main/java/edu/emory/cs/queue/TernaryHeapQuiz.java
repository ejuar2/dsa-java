package edu.emory.cs.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TernaryHeapQuiz<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    private final List<T> keys;

    // Default constructor calls the constructor with a priority comparator
    //    natural order is used as the default priority
    public TernaryHeapQuiz() {
        this(Comparator.naturalOrder());
    }

    // Ternary heap starts at index 1
    public TernaryHeapQuiz(Comparator<T> priority) {
        super(priority);
        keys = new ArrayList<>();
        keys.add(null);
    }

    // Add method inserts a key at the last index of the complete tree, and
    //    swims the value to the correct position of the heap
    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    // Remove method removes the key with the highest priority by swapping the first key with the last key
    // The new last key is removed, and it is returned after the new first key sinks to the correct priority
    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink();
        return max;
    }

    // The key at the last index is swapped with its parent as long as it has a higher priority
    private void swim(int k) {
        for (; k > 1 && compare((k+1)/3, k) < 0; k = (k+1)/3)
            Collections.swap(keys, (k+1)/3, k);
    }

    // The key at the first index is swapped with one of its three children as long as the parent has a lower priority
    // The child with the highest priority is swapped with its parent
    private void sink() {
        for (int k = 1, i = 2; i <= size(); k = i, i = i * 3 - 1) {
            // i is incremented by 1 if the middle child has the greatest priority
            if (i<size()-1 && compare(i, i+1) <=0 && compare(i+2, i+1) <= 0) i+=1;
            // i is incremented by 2 if the right child has the greatest priority
            else if (i<size()-1 && compare(i, i+2) <= 0 && compare(i+1, i+2) <= 0) i+=2;
            if (compare(k, i) >= 0) break;
            Collections.swap(keys, k, i);
        }
    }

    // Method returns keys.size() - 1 as the size because the first index in keys is a null value
    @Override
    public int size() {
        return keys.size() - 1;
    }

    // Compare uses the given priority to return the relationship between two values
    //     positive value returned if k1 has greater priority than k2
    //     negative value returned if k1 has lower priority than k2
    //     0 returned if k1 has the same priority as k2
    private int compare(int k1, int k2) {
        return priority.compare(keys.get(k1), keys.get(k2));
    }
}