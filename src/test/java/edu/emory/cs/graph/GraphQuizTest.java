/*
 * Copyright 2020 Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** @author Jinho D. Choi */
public class GraphQuizTest {

    @Test
    public void testQuiz0() {
        GraphQuiz g = new GraphQuiz(5);
        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(0, 2, 0);
        g.setDirectedEdge(2, 1, 0);
        g.setDirectedEdge(2, 3, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 2, 0);

        assertEquals(1, g.numberOfCycles());
    }

    @Test
    public void testQuiz1() {
        GraphQuiz g = new GraphQuiz(5);
        g.setDirectedEdge(0, 2, 0);
        g.setDirectedEdge(1, 0, 0);
        g.setDirectedEdge(2, 1, 0);
        g.setDirectedEdge(2, 3, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 2, 0);

        assertEquals(2, g.numberOfCycles());
    }

    @Test
    public void testQuiz2() {
        GraphQuiz g = new GraphQuiz(6);
        g.setDirectedEdge(0, 2, 0);
        g.setDirectedEdge(1, 0, 0);
        g.setDirectedEdge(2, 1, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 5, 0);
        g.setDirectedEdge(5, 3, 0);

        assertEquals(2, g.numberOfCycles());
    }

    @Test
    public void testQuiz3() {
        GraphQuiz g = new GraphQuiz(5);
        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(1, 3, 0);
        g.setDirectedEdge(2, 0, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 2, 0);

        assertEquals(2, g.numberOfCycles());
    }

    @Test
    public void testQuiz4() {
        GraphQuiz g = new GraphQuiz(6);
        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(1, 3, 0);
        g.setDirectedEdge(2, 0, 0);
        g.setDirectedEdge(2, 4, 0);
        g.setDirectedEdge(3, 4, 0);
        g.setDirectedEdge(4, 1, 0);
        g.setDirectedEdge(4, 5, 0);
        g.setDirectedEdge(5, 2, 0);

        assertEquals(5, g.numberOfCycles());
    }

    @Test
    public void testQuiz5() {
        GraphQuiz g = new GraphQuiz(4);
        g.setUndirectedEdge(0, 1, 0);
        g.setUndirectedEdge(1, 2, 0);
        g.setUndirectedEdge(2, 3, 0);
        g.setUndirectedEdge(3, 0, 0);

        assertEquals(6, g.numberOfCycles());
    }

    @Test
    public void testQuiz6() {
        GraphQuiz g = new GraphQuiz(6);
        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(0, 4, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(1, 4, 0);
        g.setDirectedEdge(2, 0, 0);
        g.setDirectedEdge(2, 5, 0);
        g.setDirectedEdge(3, 0, 0);
        g.setDirectedEdge(4, 2, 0);
        g.setDirectedEdge(4, 3, 0);
        g.setDirectedEdge(5, 4, 0);

        assertEquals(7, g.numberOfCycles());
    }


    // empty graph
    @Test
    public void testQuizA() {
        GraphQuiz g = new GraphQuiz(0);

        assertEquals(0, g.numberOfCycles());
    }

    // graph with no edges
    @Test
    public void testQuizB() {
        GraphQuiz g = new GraphQuiz(10);

        assertEquals(0, g.numberOfCycles());
    }

    // single-self loop
    @Test
    public void testQuizC() {
        GraphQuiz g = new GraphQuiz(1);

        g.setDirectedEdge(0, 0, 0);

        assertEquals(1, g.numberOfCycles());
    }

    // two nodes, single directed
    @Test
    public void testQuizD() {
        GraphQuiz g = new GraphQuiz(2);

        g.setDirectedEdge(0, 1, 0);

        assertEquals(0, g.numberOfCycles());
    }

    // two nodes, double directed
    @Test
    public void testQuizE() {
        GraphQuiz g = new GraphQuiz(2);

        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 0, 0);

        assertEquals(1, g.numberOfCycles());
    }

    // simple 3 nodes, directed
    @Test
    public void testQuizF() {
        GraphQuiz g = new GraphQuiz(3);

        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(2, 0, 0);

        assertEquals(1, g.numberOfCycles());
    }

    // complete 3 node graph with self-looping nodes
    @Test
    public void testQuizG() {
        GraphQuiz g = new GraphQuiz(3);

        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 0, 0);

        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(2, 1, 0);

        g.setDirectedEdge(2, 0, 0);
        g.setDirectedEdge(0, 2, 0);

        g.setDirectedEdge(0, 0, 0);
        g.setDirectedEdge(1, 1, 0);
        g.setDirectedEdge(2, 2, 0);

        assertEquals(8, g.numberOfCycles());
    }

    // cyclic 4 nodes, with single directed diagonal
    @Test
    public void testQuizH() {
        GraphQuiz g = new GraphQuiz(4);

        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(2, 3, 0);
        g.setDirectedEdge(3, 0, 0);
        g.setDirectedEdge(0, 2, 0);

        assertEquals(2, g.numberOfCycles());
    }

    // two triangularly adjacent "cyclic 4 nodes, with single directed diagonal"
    @Test
    public void testQuizI() {
        GraphQuiz g = new GraphQuiz(5);

        g.setDirectedEdge(0, 1, 0);
        g.setDirectedEdge(1, 2, 0);
        g.setDirectedEdge(2, 3, 0);
        g.setDirectedEdge(3, 0, 0);

        g.setDirectedEdge(4, 1, 0);
        g.setDirectedEdge(4, 3, 0);

        g.setDirectedEdge(2, 0, 0);
        g.setDirectedEdge(0, 4, 0);

        assertEquals(5, g.numberOfCycles());
    }

    // 3 nodes, directed, sparse graph
    @Test
    public void testQuizJ() {
        GraphQuiz g = new GraphQuiz(100);

        g.setDirectedEdge(90, 91, 0);
        g.setDirectedEdge(91, 92, 0);
        g.setDirectedEdge(92, 90, 0);

        assertEquals(1, g.numberOfCycles());
    }
}
