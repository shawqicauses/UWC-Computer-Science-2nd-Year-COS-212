package Practical_16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class tryHeapSort {
    private static String clean_word(String word) {
        return word.replaceAll("^[...,;:_!\\-]+|[...,;:_!\\-]+$", "");
    }

    public static String[] load_words(String file_path) throws IOException {
        List<String> words = new ArrayList<>();

        File file = new File(file_path);

        if (!file.exists()) {
            file = new File("Practical_16/" + file_path);
        }

        if (!file.exists()) {
            file = new File("Practical_15/" + file_path);
        }

        if (!file.exists()) {
            throw new IOException("File not found: " + file_path);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.ISO_8859_1))) {

            String line;

            while ((line = reader.readLine()) != null) {
                for (String raw : line.split("\\s+")) {
                    String word = clean_word(raw).toLowerCase();

                    if (!word.isEmpty()) {
                        words.add(word);
                    }
                }
            }
        }

        return words.toArray(new String[0]);
    }

    public static void main(String[] args) {
        System.out.println("Heap Sort");
    }
}
