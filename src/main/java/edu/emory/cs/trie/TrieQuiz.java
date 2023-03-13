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

package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */
    List<Entity> getEntities(String input) {
        TrieNode<Integer> node = getRoot();
        List<Entity> entities = new ArrayList<>();
        // Verify the input or trie is not empty
        if (node == null || input.length() * node.getChildrenMap().size() == 0)
            return entities;

        // Two index pointers are used to track the existence of input.substring(s,i) within the trie
        //      i starts at 0 because charAt(i) scans for chars at substring(i, i + 1)
        for (int i = 0, s = -1; i < input.length(); i++) {
            char c = input.charAt(i);
            node = node.getChild(c);

            // If the node key is equal to the character and is an endState, a new entity is added,
            //      node is reset to the root, and s = i to begin a new word
            if (node != null && node.getKey() == c && node.isEndState()) {
                entities.add(new Entity(s + 1, i + 1, node.getValue()));
                node = getRoot();
                s = i;
            }
            // If the node is null or the key is not found, the node is reset to the root and
            //      s = i to not include the index that didn't contain a character in the input
            else if (node == null || node.getKey() != c) {
                node = getRoot();
                s = i;
            }
            // If the node key is equal to the character and is not an endState, s remains fixed while i is incremented
        }
        return entities;
    }
}