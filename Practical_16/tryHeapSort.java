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

    private static void sift_down(String[] array, int i, int heap_size) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < heap_size && array[left].compareTo(array[largest]) > 0) {
            largest = left;
        }

        if (right < heap_size && array[right].compareTo(array[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            String temporary = array[i];

            array[i] = array[largest];

            array[largest] = temporary;

            sift_down(array, largest, heap_size);
        }
    }

    private static void sift_up(String[] array, int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;

            if (array[i].compareTo(array[parent]) <= 0) {
                break;
            }

            String temporary = array[i];

            array[i] = array[parent];

            array[parent] = temporary;

            i = parent;
        }
    }

    public static void build_heap_bottom_up(String[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            sift_down(array, i, n);
        }
    }

    public static void build_heap_top_down(String[] array) {
        for (int i = 1; i < array.length; i++) {
            sift_up(array, i);
        }
    }

    public static void heap_sort_from_heap(String[] array) {
        int n = array.length;

        for (int i = n - 1; i > 0; i--) {
            String temporary = array[0];

            array[0] = array[i];

            array[i] = temporary;

            sift_down(array, 0, i);
        }
    }

    public static void heap_sort_bottom_up(String[] array) {
        build_heap_bottom_up(array);
        heap_sort_from_heap(array);
    }

    public static void heap_sort_top_down(String[] array) {
        build_heap_top_down(array);
        heap_sort_from_heap(array);
    }

    public static void main(String[] args) {
        System.out.println("Heap Sort");
    }
}
