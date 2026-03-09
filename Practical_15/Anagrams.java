package Practical_15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Anagrams {
    private static String signature(String word) {
        char[] chars = word.toCharArray();

        Arrays.sort(chars);

        return new String(chars);
    }

    private static String clean_word(String word) {
        return word.replaceAll("^[...,;:_!\\-]+|[...,;:_!\\-]+$", "");
    }

    public static void main(String[] args) {
        String input_file = "ulysses.text";

        if (args.length >= 1) {
            input_file = args[0];
        }

        File file = new File(input_file);

        if (!file.exists()) {
            file = new File("Practical_15/" + input_file);
        }

        if (!file.exists()) {
            System.err.println(
                    "File not found: "
                            + input_file
                            + " (tried current directory and Practical_15 directory.)");

            return;
        }

        Map<String, List<String>> anagram_dictionary = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1))) {

            String line = reader.readLine();

            while (line != null) {
                String[] words = line.split("\\s+");

                for (String word : words) {
                    word = clean_word(word);

                    word = word.toLowerCase();

                    if (word.isEmpty()) {
                        continue;
                    }

                    String key = signature(word);

                    if (!anagram_dictionary.containsKey(key)) {
                        anagram_dictionary.put(key, new ArrayList<>());
                    }

                    List<String> list = anagram_dictionary.get(key);

                    if (!list.contains(word)) {
                        list.add(word);
                    }
                }

                line = reader.readLine();
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());

            return;
        }

        Map<String, List<String>> anagrams_only = new TreeMap<>();

        for (Map.Entry<String, List<String>> e : anagram_dictionary.entrySet()) {
            if (e.getValue().size() >= 2) {
                List<String> sorted = new ArrayList<>(e.getValue());

                Collections.sort(sorted);

                anagrams_only.put(sorted.get(0), sorted);
            }
        }
    }
}
