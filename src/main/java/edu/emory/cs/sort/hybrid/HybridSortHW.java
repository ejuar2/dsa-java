package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;

import java.lang.reflect.Array;

public class HybridSortHW<T extends Comparable<T>> implements HybridSort<T> {

    private final short SIZESMALL = 42; // rows that have less than 42 elements are considered small
    private final double RATIOSMALL = 0.6; // small rows greater than 60% shuffled are considered partially ordered
    private final AbstractSort<T> shellEngine, insertionEngine, introEngine; // sorting algorithms that are used

    public HybridSortHW() {
        introEngine = new IntroSort<T>(new ShellSortKnuth<T>());
        shellEngine = new ShellSortKnuth<T>();
        insertionEngine = new InsertionSort<T>();
    }

    /**
     * sort             Sorts a 2D jagged array of comparable elements into an ascending 1D array
     *                  Contains rows that may be ascending, descending, partially ascending,
     *                      partially ascending, or random
     * @return T[]      An ascending sorted 1D array of comparable elements
     * @param input     An unsorted 2D jagged array of comparable elements
     */
    @Override
    @SuppressWarnings("unchecked")
    public T[] sort(T[][] input) {
        // Simple check for empty 2D array
        if (input == null || (input.length == 1 && input[0].length == 0))
            return null;
        // Individual rows in the jagged array are sorted
        for (T[] row: input)
            if (row.length > 1) // Rows that have lengths <= 1 are considered sorted
                sortEngine(row);
        // Sorted rows are merged using an iterative version of the merge algorithm in "mergesort"
        return merge(input);
    }

    /**
     * sortEngine       Simple metadata of a 1D array is collected by counting instances of sequential elements
     *                      that are ascending or equal
     *                  The counts for ascending and equality are used to determine sorting algorithm for a row
     * @return void     Input 1D array of comparable elements is sorted in ascending order
     * @param row       A potentially unsorted 1D array of comparable elements
     */
    private void sortEngine(T[] row) {
        int asc = 0, equal = 0; // Counters for sequential ascending and equal elements
        for (int i = 1; i < row.length; i++) {
            int compare = row[i - 1].compareTo(row[i]);
            if (compare == 0) equal++;
            else if(compare < 0) asc++;
        }
        // Rows that are not already ascending are sorted
        if (asc + equal != row.length - 1)
            sortRow(row, asc, equal);
    }

    /**
     * sortRow          Different sorting algorithms are used to sort each
     *                      row of a jagged 2D array based on row size and proportion
     *                      of data that is considered ascending and descending
     * @return void     Input 1D array of comparable elements is sorted in ascending order
     * @param row       An unsorted 1D array of comparable elements
     * @param asc       The count of sequential data in a row that is ascending
     * @param equal     The count of sequential data in a row that is equal
     */
    private void sortRow(T[] row, int asc, int equal){
        // Case when row is completely descending
        if (asc == 0) {
            reverse(row);
            return;
        }
        int total = row.length - 1;
        double ascRatio = (double) (asc + equal) / total;
        double descRatio = (double) (total - asc) / total;
        if (row.length < SIZESMALL) {
            // Case when small row is either partially ascending or random
            if (ascRatio > RATIOSMALL || descRatio < RATIOSMALL)
                insertionEngine.sort(row, 0, row.length);
            // Case when small row is partially descending
            else shellEngine.sort(row, 0, row.length);
        }
        // Case for large rows
        else introEngine.sort(row, 0, row.length);
    }

    /**
     * reverse          An O(n) algorithm to sort rows that are strictly descending
     *                  The first and last elements are swapped until the indices for the first
     *                      and last elements are equal to the indices of the middle elements
     * @return void     Input 1D array is sorted in ascending order
     * @param row       A descending sorted 1D array of comparable elements
     */
    private void reverse(T[] row) {
        for (int i = 0; i < row.length / 2; i++) {
            T t = row[i];
            row[i] = row[row.length - i - 1];
            row[row.length - i - 1] = t;
        }
    }

    /**
     * merge            An iterative implementation of the merge algorithm in "mergesort"
     *                  The inner loop merges pairs of sorted rows and inserts each merged row into the first
     *                      "last/2" indices of the existing 2D array
     *                  The outer loop repeats the merging process of the inner loop until all rows have been
     *                      merged into index 0 of the existing 2D array
     *                  Visual example of merge: Let "rowArray" be an array of 8 sorted rows
     *                          inner loop 0 -> rowArray = {0,        1,     2,  3, 4, 5, 6, 7}, last = 8
     *                          inner loop 1 -> rowArray = {01,       23,   45, 67, 4, 5, 6, 7}, last = 4
     *                          inner loop 2 -> rowArray = {0123,     4567, 45, 67, 4, 5, 6, 7}, last = 2
     *                          inner loop 3 -> rowArray = {01234567, 4567, 45, 67, 4, 5, 6, 7}, last = 1
     * @return T[]      An ascending sorted 1D array of comparable elements
     * @param rowArray  An array of sorted 1D arrays of comparable elements
     */
    private T[] merge(T[][] rowArray) {
        // int m        The index where merged rows are stored
        // int last     The upper bound of rows that are merged
        for (int last = rowArray.length, m = 0; last > 1; last = m, m = 0) {
            // Merged rows are placed into the first "last/2" indices of the existing 2D array
            for (int i = 0; i < last; i += 2) {
                // If the last merge does not have two rows to merge, input[m] = input[last - 1]
                rowArray[m++] = (i + 1 == last) ? rowArray[i] : mergeRows(rowArray[i], rowArray[i + 1]);
            }
        }
        return rowArray[0];
    }

    /**
     * mergeRows        An O(m + n) algorithm that merges two sorted 1D arrays into one sorted 1D array
     * @return T[]      An ascending sorted 1D array (size = m + n) of comparable elements
     * @param left      An ascending sorted 1D array (size = m) of comparable elements
     * @param right     An ascending sorted 1D array (size = n) of comparable elements
     */
    private T[] mergeRows(T[] left, T[] right) {
        // Space is allocated to store an array of size = left.length + right.length
        T[] merged = (T[]) Array.newInstance(left.getClass().getComponentType(), left.length + right.length);
        int l = 0, r = 0, m = 0; // Indices that will be used for traversing the arrays
        // Elements are inserted from both arrays into "merged" until one of the arrays is fully traversed
        while (l < left.length && r < right.length) {
            // If left[l] has a lower or equal value compared to right[r], then left[l] is inserted into merged[m]
            // Otherwise, right[r] is inserted into merged[m]
            merged[m++] = (left[l].compareTo(right[r]) <= 0) ? left[l++] : right[r++];
        }
        // For the array that was not fully traversed, the rest of its elements are inserted into "merged"
        while (l < left.length) merged[m++] = left[l++];
        while (r < right.length) merged[m++] = right[r++];
        return merged;
    }
}