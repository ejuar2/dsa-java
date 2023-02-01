package edu.emory.cs.algebraic;

import java.util.Arrays;

// @author: Eric Juarez
public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    // Method to add different signed long ints
    // Subtraction is done by taking the difference of the
    //      smallest absolute value long int from the largest absolute value long int
    // The sign is determined by the negativity/positivity of the largest absolute value long int
    @Override
    protected void addDifferentSign(LongInteger n) {
        int compare = compareAbs(n);

        byte[] largerAbs, smallerAbs;
        if (compare < 0) {
            largerAbs = n.digits;
            smallerAbs = digits;
        }
        else {
            largerAbs = digits;
            smallerAbs = n.digits;
        }
        digits = getAbsDiff(largerAbs, smallerAbs);

        if (compare < 0) sign = n.sign;
    }

    // Method to calculate the absolute difference
    protected byte[] getAbsDiff(byte[] largerAbs, byte[] smallerAbs) {
        byte[] result = new byte[largerAbs.length];
        System.arraycopy(largerAbs, 0, result, 0, largerAbs.length);

        for (int i = 0; i < smallerAbs.length; i++) {
            if (i < smallerAbs.length)
                result[i] -= smallerAbs[i];
            if (result[i] < 0) {
                result[i] += 10;
                result[i + 1] -= 1;
                // Check is implemented for cases where subtraction carries over
                int j = i;
                while (result[j + 1] == -1) {
                    result[j+1] += 10;
                    result[j+2] -= 1;
                    j++;
                }
            }
        }
        return removeTrailingZeros(result);
    }

    // Method for removing trailing zeros from the long integer
    protected byte[] removeTrailingZeros(byte[] input) {
        int firstNonZero = input.length - 1;
        while (firstNonZero > 0 && input[firstNonZero] == 0)
            firstNonZero--;
        return Arrays.copyOf(input, firstNonZero + 1);
    }
}
