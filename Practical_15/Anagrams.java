package Practical_15;

import java.util.Arrays;

public class Anagrams {
    private static String signature(String word) {
        char[] chars = word.toCharArray();

        Arrays.sort(chars);

        return new String(chars);
    }

    private static String clean_word(String word) {
        return word.replaceAll("^[...,;:_!\\-]+|[...,;:_!\\-]+$", "");
    }
}