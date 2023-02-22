package edu.emory.cs.sort.comparison;

import java.util.Collections;
import java.util.Comparator;


public class ShellSortQuiz<T extends Comparable<T>> extends ShellSort<T> {
    public ShellSortQuiz() {
        this(Comparator.naturalOrder());
    }

    public ShellSortQuiz(Comparator<T> comparator) {
        super(comparator);
    }

    // Gap sequence is generated for the shell sort
    // The max value of the sequence used is n/3
    @Override
    protected void populateSequence(int n) {
        n /= 3;

        for (int t = sequence.size() + 1; ; t++) {
            int h = (int) (Math.pow(2, t) - 1); // Hibbard sequence is used as the gap for the shell sort
            if (h <= n) sequence.add(h);
            else break;
        }
    }

    // Starting index for the gap sequence is calculated
    @Override
    protected int getSequenceStartIndex(int n) {
        int index = Collections.binarySearch(sequence, n / 3);
        if (index < 0) index = -(index + 1); // if n/3 was not found in the sequence a theoretical position is given
        if (index == sequence.size()) index--; // Occurs if theoretical position is a greater value than in the sequence
        return index;
    }
}