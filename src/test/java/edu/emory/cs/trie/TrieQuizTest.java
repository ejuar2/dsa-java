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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;


/** @author Jinho D. Choi */
public class TrieQuizTest {
    @Test
    public void testGetEntities1() {
        final List<String> L = List.of("United States", "South Korea");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        // String input = "United States";
        String input = "I was born in South Korea and raised in the United States";
        List<Entity> entities = List.of(new Entity(44, 57, 0), new Entity(14, 25, 1));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities2() {
        final List<String> L = List.of("U");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        // String input = "United States";
        String input = "abU";
        List<Entity> entities = List.of(new Entity(2, 3, 0));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities3() {
        final List<String> L = List.of("U");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        // String input = "United States";
        String input = "ab";
        List<Entity> entities = new ArrayList<>();
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities4() {
        final List<String> L = List.of("United States", "South Korea");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        // String input = "United States";
        String input = "South Korea";
        List<Entity> entities = List.of(new Entity(0, 11, 1));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities5() {
        final List<String> L = new ArrayList<>();
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        // String input = "United States";
        String input = "ab";
        List<Entity> entities = new ArrayList<>();
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }

    @Test
    public void testGetEntities6() {
        final List<String> L = List.of("United States", "South Korea");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        String input = "South KoreaUnited States";
        List<Entity> entities = List.of(new Entity(0, 11, 1), new Entity(11, 24, 0));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
    @Test
    public void testGetEntities7() {
        final List<String> L = List.of("United States", "South Korea");
        TrieQuiz trie = new TrieQuiz();
        for (int i = 0; i < L.size(); i++)
            trie.put(L.get(i), i);

        String input = "South KoreaSouth Korea";
        List<Entity> entities = List.of(new Entity(0, 11, 1), new Entity(11, 22, 1));
        Set<String> expected = entities.stream().map(Entity::toString).collect(Collectors.toSet());
        Set<String> actual = trie.getEntities(input).stream().map(Entity::toString).collect(Collectors.toSet());
        assertEquals(expected, actual);
    }
}