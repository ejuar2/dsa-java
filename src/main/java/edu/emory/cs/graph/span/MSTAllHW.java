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
package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;

import java.util.*;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    //    class Subtree {
//        PriorityQueue<Edge> queue;
//        Set<Integer> visited;
//        Set<Edge> edges;
//    }

    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        Set<Set<Edge>> treeSet = new HashSet<>();

        Deque<Integer> nodeStack = new ArrayDeque<>();
        Deque<Set<Integer>> visitedStack = new ArrayDeque<>();
        Deque<Set<Edge>> treeStack = new ArrayDeque<>();

        int target = 0;
        Set<Integer> visited = new HashSet<>();
        Set<Edge> tree = new HashSet<>();

        nodeStack.offerLast(target);
        visitedStack.offerLast(visited);
        treeStack.offerLast(tree);

        while (!nodeStack.isEmpty()) {
            target = nodeStack.pollLast();
            visited = visitedStack.pollLast();
            tree = treeStack.pollLast();

            if (tree.size() + 1 == graph.size()) {
                treeSet.add(tree);
                continue;
            }
            if (visited.contains(target)) continue;
            visited.add(target);

            // Set<Edge> edgeSet = new HashSet<>(); // replace with pqueue
            Queue<Edge> nextEdge = new PriorityQueue<>();
            for (int node : visited)
                for (Edge edge : graph.getIncomingEdges(node))
                    if (!visited.contains(edge.getSource()))
                        nextEdge.add(edge);


            double minWeight = nextEdge.peek().getWeight();

            Edge edge;
            Set<Edge> newTree;

            while (nextEdge.size() > 0 && nextEdge.peek().getWeight() == minWeight) {
                edge = nextEdge.poll();
                newTree = new HashSet<>(tree);
                newTree.add(edge);

                nodeStack.offerLast(edge.getSource());
                visitedStack.offerLast(new HashSet<>(visited));
                treeStack.offerLast(newTree);
            }
        }

        List<SpanningTree> treeList = new ArrayList<>();
        for (Set<Edge> t: treeSet) {
            SpanningTree st = new SpanningTree();
            for (Edge e: t) st.addEdge(e);
            treeList.add(st);
        }
        return treeList;
    }



//    @Override
//    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
//        this.graph = graph;
//        treeSet = new HashSet<>();
//        addTrees(0, new HashSet<>(), new HashSet<>());
//
//        List<SpanningTree> treeList = new ArrayList<>();
//        for (Set<Edge> tree: treeSet) {
//            SpanningTree st = new SpanningTree();
//            for (Edge e: tree) st.addEdge(e);
//            treeList.add(st);
//        }
//        return treeList;
//    }
//    private void addTrees2(int target, Set<Integer> visited, Set<Edge> tree) {
//        if (tree.size() + 1 == graph.size()) {
//            treeSet.add(tree);
//            return;
//        }
//        if (visited.contains(target)) return;
//        visited.add(target);
//
//        Set<Edge> edgeSet = new HashSet<>();
//        for (int node: visited)
//            for (Edge edge: graph.getIncomingEdges(node))
//                if (!visited.contains(edge.getSource()))
//                    edgeSet.add(edge);
//
//        Queue<Edge> nextEdge = new PriorityQueue<>(edgeSet);
//        double minWeight = nextEdge.peek().getWeight();
//
//        Edge edge;
//        Set<Edge> newTree;
//
//        while (nextEdge.size() > 0 && nextEdge.peek().getWeight() == minWeight) {
//            edge = nextEdge.poll();
//            newTree = new HashSet<>(tree);
//            newTree.add(edge);
//
//            addTrees2(edge.getSource(), new HashSet<>(visited), newTree);
//        }
//    }



    /*
     *
     * 1. PriorityQueue(root.edges())
     * 2. Do prims
     *
     * while (heap.notEmpty()) {
     *   val weight = heap.peek().weight;
     *
     *   do {
     *       heap.poll();
     *   } while(heap.peek() == weight);
     * }
     *
     *
     *
     * */


//    class Subtree {
//        PriorityQueue<Edge> queue;
//        Set<Integer> visited;
//        Set<Edge> edges;
//    }

//    @Override
//    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
//        this.graph = graph;
//        Set<SpanningTree> treeSet = new HashSet<>();
//
//        Deque<Set<Edge>> treeDeque = new ArrayDeque<>();
//        Deque<Set<Integer>> visitedDeque = new ArrayDeque<>();
//        Deque<PriorityQueue<Edge>> nextEdgeDeque = new ArrayDeque<>();
//
//        Set<Integer> visited = new HashSet<>();
//        Set<Integer> newVisited;
//
//        Set<Edge> tree;
//
//        visited.add(0);
//        PriorityQueue<Edge> nextEdge = new PriorityQueue<>(graph.getIncomingEdges(0));
//        double min = nextEdge.peek().getWeight();
//
//        for (Edge edge : nextEdge) {
//            if (!visited.contains(edge.getSource()) && edge.getWeight() == min) {
//                newVisited = new HashSet<>(visited);
//                newVisited.add(edge.getSource());
//                visitedDeque.offer(newVisited);
//
//                tree = new HashSet<>();
//                tree.add(edge);
//                treeDeque.offer(tree);
//            }
//        }
//
//        while(!treeDeque.isEmpty()) {
//
//        }
//        return null;
//    }



    //////////////////////////////////

//    private void addTree(int node, Set<Edge> tree, Set<Integer> visited) {
//        if (visited.contains(node)) return;
//        if (tree.size() + 1 == graph.size()) {
//            treeSet.add(tree);
//            return;
//        }
//        visited.add(node);
//
//        Queue<Edge> edgeQueue = new PriorityQueue<>(outgoingEdges.get(node)); // outgoing edges
//        double minWeight = edgeQueue.peek().getWeight();
//
//        while (!edgeQueue.isEmpty()) {
//            Edge edge = edgeQueue.poll();
//            if (edge.getWeight() == minWeight) {
//                // replace set of edges with Spanning tree
//                Set<Edge> newTree = new HashSet<>();
//                newTree.add(edge); // new tree with edge
//                addTree(edge.getSource(), new HashSet<>(newTree), new HashSet<>(visited));
//            }
//        }
//    }
//
//    private void add(PriorityQueue<Edge> queue, Set<Integer> visited, Graph graph, int target) {    // Function RT: O(V)
//        visited.add(target);                                                                    // HashSet.add RT: O(1)
//        for (Edge edge : graph.getIncomingEdges(target)) {                                      // loop traversal RT: O(V)
//            if (!visited.contains(edge.getSource()))                                            // HashSet.contains RT: O(1)
//                queue.add(edge);                                                                // PriorityQ RT: O(logE)
//        }
//    }
//

}


