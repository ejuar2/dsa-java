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
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.util.*;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {

    private final byte StartASCII, EndASCII;
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
        if (max < 0)
            throw new RuntimeException("Invalid MAX value. MAX must be >= 0.");
        StartASCII = 32;
        EndASCII = 126;
    }

    /**
     * @return          List of candidates is returned using stored metadata and BFS at an existing node
     * @param prefix    Prefix is the word in autocomplete that candidates will be returned for
     */
    @Override
    public List<String> getCandidates(String prefix) {
        // Edge case handling
        TrieNode<List<String>> node = find(prefix);
        if (node == null || prefix.length() == 0 || getMax() == 0)
            return new ArrayList<>();

        List<String> metaData = node.getValue(); // the list of candidates at a node is returned
        List<String> candidateList = (metaData != null) ? new ArrayList<>(metaData) : new ArrayList<>();
        addCandidates(candidateList, node); // additional candidates are added to the candidates list with BFS
        return candidateList;
    }

    /**
     * @return                  void method that uses BFS to append candidates to a candidates list
     * @param node              Last node of an existing prefix
     * @param candidateList     List of candidates that will be returned, may contain metadata
     */
    private void addCandidates(List<String> candidateList, TrieNode<List<String>> node) {
        Deque<TrieNode<List<String>>> queue = new ArrayDeque<>(); // queue used for BFS
        queue.add(node);
        while (!queue.isEmpty() && candidateList.size() < getMax()) { // BFS stopping conditions
            TrieNode<List<String>> parent = queue.poll();

            // conditions used to append candidates to the candidates list
            if (parent.isEndState()) {
                String word = getWord(parent); // Node is traversed to obtain word
                if (!candidateList.contains(word))
                    candidateList.add(word);
            }

            // All children from the parent is added to the queue, if it exists
            for (byte c = StartASCII; c <= EndASCII; c++)
                if (parent.getChild((char) c) != null)
                    queue.add(parent.getChild((char) c));
        }
    }

    /**
     * @return          A node is traversed by its parent until the root is reached
     *                      The obtained characters are reversed to return the word at the node
     * @param node      The end state of the node is true, meaning that it represents a word
     */
    private String getWord(TrieNode<List<String>> node) {
        StringBuilder str = new StringBuilder();
        // Node is traversed until the root is reached
        for (; node.getParent() != null; node = node.getParent())
            str.append(node.getKey());
        return str.reverse().toString(); // Output is reversed so the word is in order
    }

    /**
     * @return              Pick candidate represents a user's choice in autocomplete
     *                          Metadata for a chosen word is updated to store the most recently chosen candidates
     * @param prefix        Prefix is the input word that the user will obtain autocomplete results for
     *                          Prefix can be an incomplete word or a full word
     *                          Prefixes that do not exist are treated as incomplete words
     * @param candidate     Candidate is the word chosen by the user for a given prefix
     *                          Any word that is chosen is treated as a full word
     */
    @Override
    public void pickCandidate(String prefix, String candidate) {
        // trailing spaces are removed
        prefix = prefix.trim();
        candidate = candidate.trim();

        TrieNode<List<String>> node = find(prefix);

        if (node == null) {
            put(prefix, null); // prefix is inserted into the trie if it doesn't exist
            node = find(prefix); // new pointer for the prefix
            node.setEndState(false); // prefix is not assumed to be a full word
        }

        if (find(candidate) == null)
            put(candidate, null); // candidate is inserted in trie if it does not exist

        List<String> metadata = node.getValue();
        List<String> candidateList = (metadata != null) ? metadata : new ArrayList<>();
        insertCandidate(candidateList, candidate); // metadata for the prefix is updated
        if (metadata == null) // if the prefix originally did not contain metadata, metadata is set for it
            node.setValue(candidateList);
    }

    /**
     * @return                  A node's metadata is updated with a new candidate and size constraints
     * @param candidateList     The list of strings containing the node's metadata
     * @param candidate         The candidate that will be inserted into the node's metadata
     */
    private void insertCandidate(List<String> candidateList, String candidate) {
        if (candidateList.contains(candidate)) // Candidate is removed from the list if it exists, so it can be moved to the front
            candidateList.remove(candidate);
        candidateList.add(0, candidate); // All candidates become the most recently chosen word (inserted at index 0)
        if (candidateList.size() > getMax()) // If the metadata is greater than max number of candidates, the last element is removed
            candidateList.remove(candidateList.size() - 1);
    }
}