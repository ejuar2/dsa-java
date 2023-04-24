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
import java.util.stream.Collectors;

/** @author Jinho D. Choi */
public class MSTAllHW implements MSTAll {
    private Graph graph;
    private double weightMST;
    private List<SpanningTree> treeList;

    /**
     * @param graph     undirected graph represented with bidirectional edges
     * @return          returns a list of minimum spanning trees
     */
    @Override
    public List<SpanningTree> getMinimumSpanningTrees(Graph graph) {
        // global variable instantiation
        this.graph = graph;
        treeList = new ArrayList<>();

        // weight of the graph's MST is calculated with Kruskal's algorithm
        MSTKruskal kruskal = new MSTKruskal();
        weightMST = kruskal.getMinimumSpanningTree(graph).getTotalWeight();

        PriorityQueue<Edge> edgeHeap = new PriorityQueue<>();
        Set<Integer> visited = new HashSet<>();

        // populate edgeHeap PQ and visited set with initial values starting on root node
        add(edgeHeap, visited, 0);

        // recursively populate the treeList with MSTs
        addMinimumSpanningTrees(edgeHeap, visited, new SpanningTree());

        return treeList;
    }

    /**
     * Searches for spanning trees and adds spanning tree with weight of minimum spanning tree to list of MSTs
     * @param edgeHeap  priority queue of edges used to find local optimum edge to add
     * @param visited   set of nodes that have been visited in a traversal
     * @param tree      spanning tree that is being constructed
     */
    private void addMinimumSpanningTrees(PriorityQueue<Edge> edgeHeap, Set<Integer> visited, SpanningTree tree) {
        if (tree.size() + 1 == graph.size()) { // Spanning tree is completed once every node has been visited
            if (tree.getTotalWeight() == weightMST)
                treeList.add(tree); // If the spanning tree has weight of MST, it is added to treeList
            return;
        }
        if (edgeHeap.isEmpty()) return; // end traversal if empty edgeHeap

        Edge edge = edgeHeap.poll(); // evaluate path with new edge
        if (!visited.contains(edge.getSource())) { // prevents traversal of cycle
            // set up traversal of a new MST path without overwriting future backtracking paths
            PriorityQueue<Edge> newEdgeHeap = new PriorityQueue<>(edgeHeap); // copy of edgeHeap for backtracking
            add(newEdgeHeap, visited, edge.getSource());
            SpanningTree newTree = new SpanningTree(tree);
            newTree.addEdge(edge);
            // new MST path is searched from tree with new added edge
            addMinimumSpanningTrees(newEdgeHeap, visited, newTree);
            visited.remove(edge.getSource()); // remove previous visited node for backtracking
        }
        // backtracking of new possible MST path
        if (!edgeHeap.isEmpty() && edgeHeap.peek().getWeight() == edge.getWeight())
            addMinimumSpanningTrees(edgeHeap, visited, tree);
    }

    /**
     * Adds the target to the visited set, and adds the incoming edges of the target vertex that have not been visited to the edgeHeap.
     * @param edgeHeap   priority queue of incoming edges to explore.
     * @param visited set of visited vertices.
     * @param target  the target vertex to be added.
     */
    private void add(PriorityQueue<Edge> edgeHeap, Set<Integer> visited, int target) {
        visited.add(target);
        graph.getIncomingEdges(target).stream()
                .filter(edge -> !visited.contains(edge.getSource()))
                .collect(Collectors.toCollection(() -> edgeHeap));
    }
}

