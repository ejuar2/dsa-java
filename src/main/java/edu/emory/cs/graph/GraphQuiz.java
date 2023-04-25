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
import java.util.stream.IntStream;

/** @author Jinho D. Choi */
public class GraphQuiz extends Graph {
    HashSet<HashSet<Edge>> cycleSet = null;
    List<Deque<Edge>> outgoingEdges = null;
    List<Boolean> isAcyclicList = null;

    public GraphQuiz(int size) {super(size);}
    public GraphQuiz(Graph g) {super(g);}

    /**
     * @return int     The total number of cycles in this graph
     */
    public int numberOfCycles() { //                                                             [Function RT: O(V^3)]
        cycleSet = new HashSet<>(); // initialize global variable that stores cycles in this graph
        outgoingEdges = getOutgoingEdges(); // initialize global variable that stores outgoing edges        [RT: O(E)]
        isAcyclicList = IntStream.range(0, size())
                .mapToObj(node -> getIncomingEdges(node).size() * outgoingEdges.get(node).size() == 0)
                .toList();

        // isAcyclicList = getAcyclicNodes(); // initialize global variable that stores if a node is acyclic   [RT: O(V)]

        for (int node = 0; node < size(); node++) // each node in the graph is traversed for cycles         [RT: O(V)]
            if (!isAcyclicList.get(node)) // acyclic nodes are not traversed                                [RT: O(1)]
                addCycles(node, node, new HashSet<>(), new HashSet<>()); //                               [RT: O(V^2)]
        return cycleSet.size(); // the size of the cycle set represents the number of cycles in the graph
    }

    /**
     * @return void     Cycles are added to cycleSet by using DFS to build a cycle
     *                      In this implementation, cycles are sets of edges
     *                      Cycles are built if the source node of an edge is equivalent to the start node
     * @param start     Initial node that is traversed
     * @param node      Current node that is being traversed
     * @param cycle     Set of visited edges
     * @param visited   Set of visited vertices
     **/
    private void addCycles(int start, int node, Set<Edge> cycle, Set<Integer> visited) { //      [Function RT: O(V^2)]
        if (visited.contains(node)) return; // base case to end DFS traversal                               [RT: O(1)]
        visited.add(node); // traversed nodes are stored in the set of visited nodes                        [RT: O(1)]

        for (Edge edge: getIncomingEdges(node)) { // a node's incoming edges are traversed                  [RT: O(V)]
            if (isAcyclicList.get(edge.getSource())) continue; // acyclic nodes are not traversed           [RT: O(1)]

            HashSet<Edge> newCycle = new HashSet<>(cycle);
            newCycle.add(edge); // edges are added to the set of cycle as a candidate for a new cycle       [RT: O(1)]

            if (edge.getSource() != start) // cycle is built once starting node is reached                  [RT: O(1)]
                addCycles(start, edge.getSource(), newCycle, new HashSet<>(visited)); // can only be iterated V times
            else if (!cycleSet.contains(newCycle)) // adds cycle to cycleSet only if it is a new cycle      [RT: O(1)]
                cycleSet.add(newCycle); //                                                                  [RT: O(1)]
        }
    }

    /**
     * @return List     Traverses vertices in graph and stores if vertex is acyclic or not
     **/
//    private List<Boolean> getAcyclicNodes(){ //                                                    [Function RT: O(V)]
//        List<Boolean> acyclicNodes = new ArrayList<>();
//        for (int node = 0; node < size(); node++) //                                                        [RT: O(V)]
//            // node is acyclic if it does not contain at least one incoming edge and at least one outgoing edge
//            if (getIncomingEdges(node).size() * outgoingEdges.get(node).size() == 0) //                     [RT: O(1)]
//                acyclicNodes.add(true); // node is acyclic                                                  [RT: O(1)]
//            else
//                acyclicNodes.add(false); // node is not acyclic                                             [RT: O(1)]
//
//        return acyclicNodes;
//    }
}