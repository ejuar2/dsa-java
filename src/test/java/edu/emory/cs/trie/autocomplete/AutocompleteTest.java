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

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteTest {

    private final String dict_path = "C:/Users/Eric/IdeaProjects/dsa-java/src/main/resources/dict.txt";
    private final int max = 20;

    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        Autocomplete<?> ac;
        Eval eval;
        List<Eval> evalList = new ArrayList<>();

        // ASSUMES MAX = 20 AND DICT.TXT IS COMPLETE
        ac = new AutocompleteHW(dict_path, 20);
        eval = new Eval();
        evalList.add(eval);
        testAutocompleteZebr(ac, eval);

        ac = new AutocompleteHW(dict_path, max);
        eval = new Eval();
        evalList.add(eval);
        testAutocompleteASCII(ac, eval);

        eval = new Eval();
        evalList.add(eval);
        testAutocompleteSh(eval);

        eval = new Eval();
        evalList.add(eval);
        testAutocompleteEDGE(eval);

        viewEvalSummary(evalList);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////                                                                                     ///////////////
    ///////////////                                 TEST CASES                                          ///////////////
    ///////////////                                                                                     ///////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void testAutocompleteEDGE(Eval eval) {
        Autocomplete<?> ac;

        ac = new AutocompleteHW(dict_path, max);
        testGetCandidates(ac, eval, "", new ArrayList<>());
        testGetCandidates(ac, eval, "", new ArrayList<>());
        ac.pickCandidate("", "");
        testGetCandidates(ac, eval, "", new ArrayList<>());
        testGetCandidates(ac, eval, "", new ArrayList<>());
        ac.pickCandidate("", "dog");
        testGetCandidates(ac, eval, "", new ArrayList<>());

        ac = new AutocompleteHW(dict_path, 3);
        String prefix = "sun is down";
        String candidate = "freezing cold";
        ac.pickCandidate(prefix, candidate);
        testGetCandidates(ac, eval, prefix, List.of(candidate));

        List<String> expected = List.of("zebra", "zebras", "zebraic");
        prefix = "zebra";
        candidate = "zebra";
        ac.pickCandidate(prefix, candidate);
        testGetCandidates(ac, eval, prefix, expected);

        expected = List.of("zebra", "zebras", "zebrax");
        prefix = "zebr";
        candidate = "zebrax";
        ac.pickCandidate(prefix, candidate);
        testGetCandidates(ac, eval, "zebra", expected);

//        expected = List.of();
//        prefix = "";
//        candidate = null;
//        ac.pickCandidate(prefix, candidate);
//        testGetCandidates(ac, eval, "", expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testAutocompleteSh(Eval eval) {
        String prefix = "sh";
        List<String> expectedBase = getExpectedCandidates(prefix);
        List<String> chosenList;

        chosenList = generateSortedInput(1, 1);
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = generateSortedInput(10, 1);
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = generateSortedInput(10, 2);
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = generateRandomInput(10, 1);
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = generateRandomInput(10, 2);
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = List.of("she", "hit", "the", "floor", "next", "thing", "you", "know");
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        chosenList = List.of("1she1", "1she1", "hit", "hit hit", "hit hit", "the", "FLOOR", "~!@#$%^&*()_+`-={}|[]\\;':\",./<>?", "floor", "0next0", "9thing9", "AyouA", "ZknowZ");
        pickCandidateTest(eval, expectedBase, chosenList, prefix);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testAutocompleteASCII(Autocomplete<?> ac, Eval eval) {
        List<String> expected;
        for (byte c = 32; c <= 126; c++) {
            expected = getExpectedCandidates(String.valueOf((char) c));
            testGetCandidates(ac, eval, String.valueOf((char) c), expected);
        }
        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testAutocompleteZebr(Autocomplete<?> ac, Eval eval) {
        String prefix;
        List<String> expected;

        prefix = "";
        expected = List.of();
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        expected = List.of("zebra", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        expected = List.of("zebra", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "ship");
        expected = List.of("ship", "zebra", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "ship");
        expected = List.of("ship", "zebra", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "zebra");
        expected = List.of("zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "zebra");
        expected = List.of("zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "zebrafishes");
        expected = List.of("zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "zebrafishes");
        expected = List.of("zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "a");
        expected = List.of("a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "b");
        expected = List.of("b", "a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "c");
        expected = List.of("c", "b", "a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrinnies");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "d");
        expected = List.of("d", "c", "b", "a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "d");
        expected = List.of("d", "c", "b", "a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses", "zebrawood");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebr";
        ac.pickCandidate(prefix, "e");
        expected = List.of("e", "d", "c", "b", "a", "zebrafishes", "zebra", "ship", "zebras", "zebraic", "zebrass", "zebrina", "zebrine", "zebroid", "zebrula", "zebrule", "zebrinny", "zebrafish", "zebralike", "zebrasses");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebra";
        expected = List.of("zebra", "zebras", "zebraic", "zebrass", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        prefix = "zebra";
        ac.pickCandidate(prefix, "e");
        expected = List.of("e", "zebra", "zebras", "zebraic", "zebrass", "zebrafish", "zebralike", "zebrasses", "zebrawood", "zebrafishes");
        testGetCandidates(ac, eval, prefix, expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////                                                                                     ///////////////
    ///////////////                             AUXILIARY FUNCTIONS                                     ///////////////
    ///////////////                                                                                     ///////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += String.format("PASSSSS !!!!1!1!! ->\n\t\t\texpected = %s\n\t\t\treturned = %s", expected, candidates);
            }
            else
                log += String.format("FAIL (u got dis tho!) ->\n\t\t\texpected = %s\n\t\t\treturned = %s", expected, candidates);
        }
        catch (Exception e) {
            log += "ERROR";
        }
        System.out.println(log);
    }

    // Tests "ac.pickCandidate" given a list of chosen candidates
    private void pickCandidateTest(Eval eval, List<String> expected, List<String> chosenList, String prefix) {
        List<String> candidates = new ArrayList<>(expected);
        pickCandidates(candidates, chosenList);

        Autocomplete<?> ac = new AutocompleteHW(dict_path, max);
        for (String chosen: chosenList)
            ac.pickCandidate(prefix, chosen);

        testGetCandidates(ac, eval, prefix, candidates);
    }

    // Generates sorted input as test cases for pickCandidateTest
    private List<String> generateSortedInput(int length, int multiplicity) {
        List<String> input = new ArrayList<>();
        for (byte c = 97; c <= 122 && input.size() < length; c++)
            for (int m = 0; m < multiplicity && input.size() < length; m++)
                input.add(String.valueOf((char) c));
        return input;
    }

    // Generates random input as test cases for pickCandidateTest
    private List<String> generateRandomInput(int length, int multiplicity) {
        List<String> input = generateSortedInput(length, multiplicity);
        Collections.shuffle(input);
        return input;
    }

    // iteratively inserts a list of chosen inputs into a candidate list
    private void pickCandidates(List<String> candidateList, List<String> chosenList) {
        for (String candidate: chosenList)
            insertCandidate(candidateList, candidate);
    }

    // inserts a candidate into a candidate list
    private void insertCandidate(List<String> candidateList, String candidate) {
        if (candidateList.contains(candidate))
            candidateList.remove(candidate);
        candidateList.add(0, candidate);
        if (candidateList.size() > max)
            candidateList.remove(candidateList.size() - 1);
    }

    // Generates expected candidates for a prefix
    //      words not starting with the prefix are removed
    //      the resulting list is sorted by length then alphabetical order
    private List<String> getExpectedCandidates(String prefix) {
        List<String> dict = readDict();
        dict = filterPrefix(dict, prefix);
        autocompleteSort(dict);
        return dict.subList(0, (dict.size() < max) ? dict.size() : max);
    }

    // Sorts lists of strings by length then alphabetical order
    private void autocompleteSort(List<String> dict) {
        Comparator<String> autocompleteComparator = (o1, o2) -> {
            if (o1.length() > o2.length())
                return 1;
            else if (o1.length() < o2.length())
                return -1;
            else
                return o1.compareTo(o2);
        };
        Collections.sort(dict, autocompleteComparator);
    }

    // Words that do not contain the prefix are removed
    private List<String> filterPrefix(List<String> dict, String prefix) {
        List<String> newDict = new ArrayList<>();
        for (String s: dict) {
            if (s.length() >= prefix.length() && prefix.equals(s.substring(0, prefix.length())))
                newDict.add(s);
        }
        return newDict;
    }

    // Displays summary performance of test cases
    private void viewEvalSummary(List<Eval> evalList) {
        int correct = 0;
        int total = 0;
        System.out.println("\nSUMMARY");

        for (Eval e: evalList) {
            System.out.printf("Score: %d/%d\n", e.correct, e.total);
            correct += e.correct;
            total += e.total;
        }
        System.out.printf("TOTAL: %d/%d\n\n", correct, total);
    }

    // Reads an input file that contains words separated by a newline
    private List<String> readDict() {
        List<String> dict = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dict_path)));

            String line;

            while ((line = reader.readLine()) != null) {
                dict.add(line.trim());
            }
        } catch (IOException ignored) {}
        return dict;
    }
}