// SHAWQI FARES
// 4515520
// PRACTICAL 15

package Practical_15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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

        List<String> anagram_lines = new ArrayList<>();

        for (List<String> words : anagrams_only.values()) {
            String anagram_list = String.join(" ", words);

            anagram_lines.add(anagram_list);

            for (int repeat = 0; repeat < words.size() - 1; repeat++) {
                int space = anagram_list.indexOf(' ');

                anagram_list = anagram_list.substring(space + 1) + " " + anagram_list.substring(0, space);

                anagram_lines.add(anagram_list);
            }
        }

        Collections.sort(anagram_lines);

        String output_directory = file.getParent();

        String output_file = output_directory != null
                ? output_directory + File.separator + "theAnagrams.tex"
                : "theAnagrams.tex";

        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(output_file), StandardCharsets.UTF_8))) {

            char letter = 0;

            for (String lemma : anagram_lines) {
                char initial = lemma.charAt(0);

                if (Character.toLowerCase(initial) != letter) {
                    letter = Character.toLowerCase(initial);

                    out.println();

                    out.println("\\vspace{14pt}");

                    out.println("\\noindent\\textbf{\\Large " + Character.toUpperCase(initial) + "}\\\\*[+12pt]");
                }

                out.print(lemma);

                out.println("\\\\");
            }

        } catch (IOException e) {
            System.err.println("Error writing " + output_file + ": " + e.getMessage());

            return;
        }

        System.out.println("Anagram dictionary built. Found " + anagrams_only.size() + " anagram groups.");

        System.out.println("Output written to " + output_file);
    }
}