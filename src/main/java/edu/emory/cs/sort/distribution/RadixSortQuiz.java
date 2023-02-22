package edu.emory.cs.sort.distribution;

public class RadixSortQuiz extends RadixSort {
    // MSD radix sort is recursively implemented
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        int div = (int)Math.pow(10, maxBit - 1); // 10^(maxBit-1) divides the most significant digit
        sort(array, beginIndex, endIndex, div); // recursively calls the sort defined below
    }

    // The sort function sorts the array in place
    // The array is sorted
    public void sort(Integer[] array, int beginIndex, int endIndex, int factor) {
        if (endIndex - beginIndex <= 0 || factor == 0) return; // base cases for the recursive calls
        sort(array, beginIndex, endIndex, key -> (key / factor) % 10); // Subsets of the array are sorted
        // For loop is used to find the subsets of the array to sort
        // If the array[start]/factor) % 10 != (array[end]/factor) % 10, then
        //  array[start] and array[end] belong to different sub buckets
        int start = beginIndex;
        for (int end = start + 1; end <= endIndex; end++) {
            if (end == endIndex || (array[start]/factor) % 10 != (array[end]/factor) % 10) {
                sort(array, start, end, factor/10);
                start = end; // Once a new sub bucket is found, end becomes the new start of the next bucket
            }
        }
    }
}
