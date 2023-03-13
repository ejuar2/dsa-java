package edu.emory.cs.tree;

import edu.emory.cs.tree.balanced.BalancedBinarySearchTreeQuiz;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalancedBinarySearchTreeQuizTest {
    @Test
    public void testCase1() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BalancedBinarySearchTreeQuiz<Integer> bbstq = new BalancedBinarySearchTreeQuiz<>();

        bst.add(4);
        bst.add(5);
        bst.add(2);
        bst.add(1);
        bst.add(3);

        bbstq.add(3);
        bbstq.add(2);
        bbstq.add(5);
        bbstq.add(1);
        bbstq.add(4);

        assertEquals(bst.toString(), bbstq.toString());
    }
    @Test
    public void testCase2() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BalancedBinarySearchTreeQuiz<Integer> bbstq = new BalancedBinarySearchTreeQuiz<>();

        bst.add(4);
        bst.add(5);
        bst.add(2);
        bst.add(1);
        bst.add(3);

        bbstq.add(3);
        bbstq.add(1);
        bbstq.add(4);
        bbstq.add(2);
        bbstq.add(5);

        assertEquals(bst.toString(), bbstq.toString());
    }

    @Test
    public void testCase3() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BalancedBinarySearchTreeQuiz<Integer> bbstq = new BalancedBinarySearchTreeQuiz<>();

        bst.add(4);
        bst.add(5);
        bst.add(2);
        bst.add(1);
        bst.add(3);

        bbstq.add(3);
        bbstq.add(1);
        bbstq.add(5);
        bbstq.add(2);
        bbstq.add(4);

        assertEquals(bst.toString(), bbstq.toString());
    }

    @Test
    public void testCase4() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        BalancedBinarySearchTreeQuiz<Integer> bbstq = new BalancedBinarySearchTreeQuiz<>();

        bst.add(4);
        bst.add(5);
        bst.add(2);
        bst.add(1);
        bst.add(3);

        bbstq.add(3);
        bbstq.add(2);
        bbstq.add(4);
        bbstq.add(1);
        bbstq.add(5);

        assertEquals(bst.toString(), bbstq.toString());
    }
}
