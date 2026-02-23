// SHAWQI FARES
// 4515520
// PRACTICAL 13

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Template {
    static class Node {
        int key;
        String data;

        Node(int key, String data) {
            this.key = key;
            this.data = data;
        }
    }

    public static Node[] load_nodes(String file_name) throws IOException {
        List<Node> list = new ArrayList<>();
        Path path = Paths.get(file_name);

        if (!Files.exists(path)) {
            Path fall_back = Paths.get("Practical-13", file_name);

            if (Files.exists(fall_back)) {
                path = fall_back;
            }
        }

        BufferedReader buffered_reader = Files.newBufferedReader(path);

        String line;

        while ((line = buffered_reader.readLine()) != null) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            int first_space = line.indexOf(' ');

            if (first_space == -1) {
                continue;
            }

            int key = Integer.parseInt(line.substring(0, first_space));

            String data = line.substring(first_space + 1);

            list.add(new Node(key, data));
        }

        buffered_reader.close();

        return list.toArray(new Node[0]);
    }

    public static int linear_search(Node[] array, int key) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].key == key) {
                return i;
            }
        }

        return -1;
    }

    public static int binary_search(Node[] array, int key) {
        int low = 0, high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;

            if (array[mid].key == key) {
                return mid;
            } else if (array[mid].key < key) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }

    private static void print_statistics(String label, DecimalFormat four_D, DecimalFormat five_D, double run_time,
            double run_time_2,
            int n,
            int repetitions) {
        double ave_runtime = run_time / repetitions;
        double variance = (run_time_2 - repetitions * ave_runtime * ave_runtime) / (repetitions - 1);
        double std_deviation = Math.sqrt(Math.max(0.0, variance));

        System.out.printf("\n\n\fStatistics (%s)\n", label);

        System.out.println("________________________________________________");

        System.out.println("Total Time = " + run_time / 1000 + "s.");

        System.out.println("Total Time\u00B2 = " + run_time_2);

        System.out.println("Average Time = " + five_D.format(ave_runtime / 1000) + "s. " + '\u00B1' + " "
                + four_D.format(std_deviation) + "ms.");

        System.out.println("Standard Deviation = " + four_D.format(std_deviation));

        System.out.println("n = " + n);

        System.out.println("Average Time / Run = " + five_D.format(ave_runtime / n * 1000) + '\u00B5' + "s. ");

        System.out.println("Repetitions = " + repetitions);

        System.out.println("________________________________________________");

        System.out.println();

        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        DecimalFormat four_D = new DecimalFormat("0.0000");
        DecimalFormat five_D = new DecimalFormat("0.00000");

        Node[] nodes = load_nodes("ulysses.numbered");
        int n = nodes.length;

        int repetitions = 30;
        int[] keys = new int[repetitions];

        Random random = new Random();

        for (int i = 0; i < repetitions; i++) {
            keys[i] = random.nextInt(32654) + 1;
        }

        long start, finish;

        double time;
        double run_time_linear = 0, run_time_2_linear = 0;
        double run_time_binary = 0, run_time_2_binary = 0;

        for (int repetition = 0; repetition < repetitions; repetition++) {
            int key = keys[repetition];

            start = System.nanoTime();

            linear_search(nodes, key);

            finish = System.nanoTime();

            time = (finish - start) / 1_000_000.0;
            run_time_linear += time;
            run_time_2_linear += (time * time);

            start = System.nanoTime();

            binary_search(nodes, key);

            finish = System.nanoTime();

            time = (finish - start) / 1_000_000.0;
            run_time_binary += time;
            run_time_2_binary += (time * time);
        }

        print_statistics("Linear Search", four_D, five_D, run_time_linear, run_time_2_linear, n, repetitions);
        print_statistics("Binary Search", four_D, five_D, run_time_binary, run_time_2_binary, n, repetitions);
    }
}