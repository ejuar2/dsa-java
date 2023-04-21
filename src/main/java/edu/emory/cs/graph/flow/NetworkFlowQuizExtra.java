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

import java.util.*;

/** @author Jinho D. Choi */
public class NetworkFlowQuizExtra {
    /**
     * Using breadth-first traverse.
     * @param graph  a directed graph.
     * @param source the source vertex.
     * @param target the target vertex.
     * @return a set of all augmenting paths between the specific source and target vertices in the graph.
     */
    public Set<Subgraph> getAugmentingPaths(Graph graph, int source, int target) {
        Set<Subgraph> subgraphSet = new HashSet<>();                // set of augmenting paths
        Queue<Subgraph> subgraphQueue = new LinkedList<>();         // queue of sub graphs used to traverse with BFS
        Queue<Integer> nodeQueue = new LinkedList<>();              // queue of current node index

        Subgraph sub, tmp;
        int node;

        // initialize queues for BFS traversal
        subgraphQueue.offer(new Subgraph());
        nodeQueue.offer(target);

        while (!subgraphQueue.isEmpty()) {  // stopping condition for BFS traversal
            sub = subgraphQueue.poll();
            node = nodeQueue.poll();

            if (source == target || sub.contains(source)) { // condition to detect a complete augmented path
                subgraphSet.add(sub);
                continue;
            }

            for (Edge iEdge: graph.getIncomingEdges(node)) {    // each incoming edge of the current node is traversed
                if (sub.contains(iEdge.getSource())) continue;  // condition to detect cycles

                tmp = new Subgraph(sub);    // paths must be copied to obtain multiple possible augmenting paths
                tmp.addEdge(iEdge);

                subgraphQueue.offer(tmp); // queue is populated with path being built
                nodeQueue.offer(iEdge.getSource()); // queue is populated with corresponding node of the path
            }
        }

        return subgraphSet;
    }
}
