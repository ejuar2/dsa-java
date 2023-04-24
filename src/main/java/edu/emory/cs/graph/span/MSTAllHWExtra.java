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

import edu.emory.cs.graph.Graph;

public class MSTAllHWExtra {
    Graph getGraphReflexiveComplete3() {
        Graph graph = new Graph(3);

        graph.setUndirectedEdge(0, 1, 1);
        graph.setUndirectedEdge(0, 2, 1);
        graph.setUndirectedEdge(1, 2, 1);

        graph.setUndirectedEdge(0, 0, 1);
        graph.setUndirectedEdge(1, 1, 1);
        graph.setUndirectedEdge(2, 2, 1);

        return graph;
    }
}