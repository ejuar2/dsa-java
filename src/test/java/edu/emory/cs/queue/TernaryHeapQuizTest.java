package edu.emory.cs.queue;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class TernaryHeapQuizTest extends PriorityQueueTest {

    @Test
    public void testRobustness0() {
        List<Integer> keys = List.of();
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }
    @Test
    public void testRobustness1() {
        List<Integer> keys = List.of(1);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }
    @Test
    public void testRobustness2() {
        List<Integer> keys = List.of(1,1,1,1);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }
    @Test
    public void testRobustness3() {
        List<Integer> keys = List.of(-1,-1,-1,0,0,0,1,1,1);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }
    @Test
    public void testRobustness4() {
        List<Integer> keys = List.of(4, 1, 3, 2, 5, 6, 8, 3, 4, 7, 5, 9, 7);
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }

    @Test
    public void testRobustness5() {
        List<Integer> keys = new Random().ints(200000, -50000, 50000).boxed().collect(Collectors.toList());
        Comparator<Integer> natural = Comparator.naturalOrder();
        Comparator<Integer> reverse = Comparator.reverseOrder();

        testRobustness(new TernaryHeapQuiz<>(), keys, reverse);
        testRobustness(new TernaryHeapQuiz<>(reverse), keys, natural);
    }

    @Test
    public void testRuntime() {
        testRuntime(new BinaryHeap<>(), new TernaryHeapQuiz<>());
    }
}