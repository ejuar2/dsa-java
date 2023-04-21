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
package edu.emory.cs.graph.flow;

import edu.emory.cs.graph.Edge;
import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.Subgraph;

import java.util.HashSet;
import java.util.Set;

/** @author Jinho D. Choi */
public class NetworkFlowQuiz {
    /**
     * Using depth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> subgraphSet = new HashSet<>();
        addAugmentingPaths(subgraphSet, new Subgraph(), graph, source, target); // recursively populate set of sub graphs
        return subgraphSet;
    }

    /**
     * Depth-first traversal of a graph that adds augmenting paths to a set of sub graphs
     *     Recursive implementation uses implicit stack frames
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @param subgraphSet set of sub graphs
     * @param sub subgraph being constructed
     */
    private void addAugmentingPaths(Set<Subgraph> subgraphSet, Subgraph sub, Graph graph, int source, int target){
        if (source == target) {         // base case to end a DFS traversal
            subgraphSet.add(sub);
            return;
        }

        Subgraph tmp;
        for (Edge iEdge: graph.getIncomingEdges(target)) {  // consider possible paths from all incoming edges
            if (sub.contains(iEdge.getSource())) continue;  // paths that have nodes that have been visited twice are ignored

            tmp = new Subgraph(sub);        // paths must be copied to obtain multiple possible augmenting paths
            tmp.addEdge(iEdge);

            addAugmentingPaths(subgraphSet, tmp, graph, source, iEdge.getSource());  // recursive call with new subgraph and edge source
        }
    }
}