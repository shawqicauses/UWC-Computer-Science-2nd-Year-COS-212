import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

    public static int N = 1; // placeholder number

    public static void main(String[] args) {
        DecimalFormat two_D = new DecimalFormat("0.00");
        DecimalFormat four_D = new DecimalFormat("0.0000");
        DecimalFormat five_D = new DecimalFormat("0.00000");

        long start, finish;
        double run_time = 0, run_time_2 = 0, time;
        double total_time = 0.0;
        int n = N;
        int repetition, repetitions = 30;

        run_time = 0;

        for (repetition = 0; repetition < repetitions; repetition++) {
            start = System.currentTimeMillis();

            // linear_search(n);
            // binary_search(n);

            finish = System.currentTimeMillis();

            time = (double) (finish - start);
            run_time += time;
            run_time_2 += (time * time);
        }

        double ave_runtime = run_time / repetitions;
        double std_deviation = Math.sqrt(run_time_2 - repetitions * ave_runtime * ave_runtime) / (repetitions - 1);

        System.out.printf("\n\n\fStatistics\n");

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
}