// SHAWQI FARES
// 4515520
// PRACTICAL 16

package Practical_16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Heap sort implementation using words from James Joyce's Ulysses.
 * Supports two heap construction strategies: bottom-up (O(n)) and top-down (O(n
 * log n)).
 */
public class tryHeapSort {

    /** Strip leading/trailing punctuation from a word. */
    private static String clean_word(String word) {
        return word.replaceAll("^[...,;:_!\\-]+|[...,;:_!\\-]+$", "");
    }

    /**
     * Load words from a text file. Tries current dir, then Practical_16, then
     * Practical_15.
     */
    public static String[] load_words(String file_path) throws IOException {
        List<String> words = new ArrayList<>();

        File file = new File(file_path);

        // Fall-back paths for lab environment
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

    /**
     * Restore max-heap property by sifting node i down. Children are at 2 * i + 1
     * and 2 * i + 2.
     */
    private static void sift_down(String[] array, int i, int heap_size) {
        int largest = i;
        int left = 2 * i + 1; // left child index
        int right = 2 * i + 2; // right child index

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

    /**
     * Restore max-heap property by sifting node i up toward its parent.
     */
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

    /**
     * Build max-heap from bottom up. Start from last non-leaf (n/2 - 1) and sift
     * down.
     * Time: O(n).
     */
    public static void build_heap_bottom_up(String[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            sift_down(array, i, n);
        }
    }

    /**
     * Build max-heap top down by inserting each element and sifting up.
     * Time: O(n log n).
     */
    public static void build_heap_top_down(String[] array) {
        for (int i = 1; i < array.length; i++) {
            sift_up(array, i);
        }
    }

    /**
     * Extract max repeatedly and place at end. Produces ascending alphabetical
     * order.
     * Shared by both bottom-up and top-down heap sorts.
     */
    public static void heap_sort_from_heap(String[] array) {
        int n = array.length;

        for (int i = n - 1; i > 0; i--) {
            // Swap root (max) with last element, then restore heap on reduced range
            String temporary = array[0];
            array[0] = array[i];
            array[i] = temporary;

            sift_down(array, 0, i);
        }
    }

    /** Full heap sort: build heap bottom-up, then extract. */
    public static void heap_sort_bottom_up(String[] array) {
        build_heap_bottom_up(array);
        heap_sort_from_heap(array);
    }

    /** Full heap sort: build heap top-down, then extract. */
    public static void heap_sort_top_down(String[] array) {
        build_heap_top_down(array);
        heap_sort_from_heap(array);
    }

    /** Print timing statistics (average, std dev, etc.). */
    private static void print_timings(String label, DecimalFormat four_D, DecimalFormat five_D, double run_time,
            double run_time_2, int n, int repetitions) {
        double ave_run_time = run_time / repetitions;

        double variance = (run_time_2 - repetitions * ave_run_time * ave_run_time) / (repetitions - 1);

        double std_deviation = Math.sqrt(Math.max(0.0, variance));

        System.out.println("\n________________________________________________");
        System.out.println("Statistics (" + label + ")");

        System.out.println("________________________________________________");
        System.out.println("Total Time = " + run_time / 1000 + " s.");

        System.out.println(
                "Average Time = " + five_D.format(ave_run_time / 1000) + " s. ± " + four_D.format(std_deviation)
                        + " ms.");

        System.out.println("Standard Deviation = " + four_D.format(std_deviation) + " ms.");

        System.out.println("n = " + n);

        System.out.println("Average Time / Run = " + five_D.format(ave_run_time / n * 1000) + " µs.");

        System.out.println("Repetitions = " + repetitions);

        System.out.println("________________________________________________");
    }

    public static void main(String[] args) {
        DecimalFormat four_D = new DecimalFormat("0.0000");
        DecimalFormat five_D = new DecimalFormat("0.00000");

        // (c) Test with short array first (~ 20 words)
        String[] test_words = { "the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog", "ulysses",
                "joyce", "dublin", "bloom", "stephen", "molly", "ireland", "river", "sea" };

        System.out.println("=== Test with short array (20 words) ===\n");
        System.out.println("Original: " + Arrays.toString(test_words));

        String[] copy_1 = test_words.clone();
        heap_sort_bottom_up(copy_1);
        System.out.println("Bottom-up heap sort: " + Arrays.toString(copy_1));

        String[] copy_2 = test_words.clone();
        heap_sort_top_down(copy_2);
        System.out.println("Top-down heap sort:  " + Arrays.toString(copy_2));

        if (!Arrays.equals(copy_1, copy_2)) {
            System.err.println("ERROR: Bottom-up and top-down sorts produced different results!");
        } else {
            System.out.println("\nBoth sorts produce identical alphabetical order. ✓");
        }

        // Load Ulysses data for full timing run
        String input_file = "ulysses.text";

        if (args.length >= 1) {
            input_file = args[0];
        }

        String[] words;

        try {
            words = load_words(input_file);
        } catch (IOException e) {
            System.err.println("Could not load " + input_file + ": " + e.getMessage());
            System.err.println("Using test words for timing demo.");
            words = test_words;
        }

        int n = words.length;
        int repetitions = 30;

        System.out.println("\n=== Timings on " + n + " words (" + repetitions + " repetitions) ===\n");

        // (d) & (e) Time both sorts and display results
        double run_time_bottom_up = 0, run_time_bottom_up_2 = 0;
        double run_time_top_down = 0, run_time_top_down_2 = 0;

        for (int r = 0; r < repetitions; r++) {
            String[] array_1 = words.clone();

            long start = System.nanoTime();

            heap_sort_bottom_up(array_1);

            long finish = System.nanoTime();

            double time = (finish - start) / 1_000_000.0;

            run_time_bottom_up += time;
            run_time_bottom_up_2 += (time * time);

            String[] array_2 = words.clone();

            start = System.nanoTime();

            heap_sort_top_down(array_2);

            finish = System.nanoTime();

            time = (finish - start) / 1_000_000.0;

            run_time_top_down += time;
            run_time_top_down_2 += (time * time);
        }

        System.out.println("                    HEAP SORT TIMING COMPARISON");
        print_timings("Bottom-up heap sort", four_D, five_D, run_time_bottom_up, run_time_bottom_up_2, n, repetitions);
        print_timings("Top-down heap sort", four_D, five_D, run_time_top_down, run_time_top_down_2, n, repetitions);

        System.out.println("\nSummary:");
        System.out.printf("  Bottom-up: %s ms average%n", five_D.format(run_time_bottom_up / repetitions));
        System.out.printf("  Top-down:  %s ms average%n", five_D.format(run_time_top_down / repetitions));
    }
}
