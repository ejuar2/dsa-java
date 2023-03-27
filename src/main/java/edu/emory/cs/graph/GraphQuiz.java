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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {

    private List<Deque<Edge>> outgoingEdges = null;
    public GraphQuiz(int size) { super(size); }
    public GraphQuiz(Graph g) { super(g); }

    /** @return the total number of cycles in this graph. */

    /**
    public int numberOfCycles() {
        outgoingEdges = getOutgoingEdges();
        HashSet<HashSet<Integer>> cycleSet = new HashSet<>();
        for (int i = 0; i < size(); i++) {
            addCycles(i, cycleSet, new HashSet<>());
        }
        cycleSet = filterHangingCycles(cycleSet);
        cycleSet = filterAtomicCycles(cycleSet);
        return cycleSet.size();
    }

    private void addCycles(int target, HashSet<HashSet<Integer>> cycleSet, HashSet<Integer> cycle) {
        if (getIncomingEdges(target).size() == 0) return;
        if (outgoingEdges.get(target).size() == 0) return;
        if (isHanging(cycle)) break;

        cycle.add(target);
        for (Edge edge: getIncomingEdges(target)) {
            HashSet<Integer> newCycle = new HashSet<>(cycle);



            boolean selfCycle = edge.getSource() == target;
            boolean isCycle = selfCycle || newCycle.contains(edge.getSource());
            boolean isNewCycle = !cycleSet.contains(newCycle);

            if (isCycle) {
                if (isNewCycle) {
                    for (Integer i: newCycle) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                    cycleSet.add(newCycle);
                }
                else return;
            }
            else addCycles(edge.getSource(), cycleSet, newCycle);
        }
    }

    private

    private HashSet<HashSet<Integer>> filterHangingCycles(HashSet<HashSet<Integer>> cycleSet) {
        HashSet<HashSet<Integer>> newCycleSet = new HashSet<>();

        for (HashSet<Integer> cycle: cycleSet) {
            for (Integer target: cycle) {
                if (!isHangingNode(target, cycle))
                    newCycleSet.add(cycle);
            }
        }

        return newCycleSet;
    }

    private boolean isHangingNode(int target, HashSet<Integer> cycle) {
        for (Edge edge: getIncomingEdges(target)) {
            if (!cycle.contains(edge.getTarget())) {
                return true;
            }
        }
        return false;
    }

    private HashSet<HashSet<Integer>> filterAtomicCycles(HashSet<HashSet<Integer>> cycleSet) {
        HashSet<HashSet<Integer>> newCycleSet = new HashSet<>();

        for (HashSet<Integer> cycle: cycleSet) {
            if (isAtomicCycle(cycle, cycleSet)) {
                newCycleSet.add(cycle);
            }
        }

        return newCycleSet;
    }

    private boolean isAtomicCycle(HashSet<Integer> test, HashSet<HashSet<Integer>> cycleSet) {
        for (HashSet<Integer> cycle: cycleSet) {
            if (!test.equals(cycle) && test.containsAll(cycle))
                return false;
        }
        return true;
    }
     **/


//    private boolean isCycle(HashSet<Edge> traversal) {
//        HashSet<Integer> visited = new HashSet<>();
//        for (Edge edge: traversal) {
//            if (visited.size() == 0) visited.add(edge.getSource());
//            if (visited.contains(edge.getTarget())) return true;
//            else visited.add(edge.getTarget());
//        }
//        return false;
//    }


}