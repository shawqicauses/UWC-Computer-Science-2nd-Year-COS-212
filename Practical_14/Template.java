// SHAWQI FARES
// 4515520
// PRACTICAL 14

package Practical_14;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Template {
    // key-value pairs class
    static class Pair {
        String key;
        String value;

        Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private static String[] build_queries(Pair[] used_data, int query_count) {
        Random random = new Random();
        String[] queries = new String[query_count];

        for (int i = 0; i < query_count; i++) {
            queries[i] = used_data[random.nextInt(used_data.length)].key;
        }

        return queries;
    }

    private static String format_k(int n) {
        if (n % 1000 == 0) {
            return (n / 1000) + "K";
        }

        return Integer.toString(n);
    }

    private static String format_one_over_one_minus_alpha(int alpha_percent) {
        return switch (alpha_percent) {
            case 75 -> "4";
            case 80 -> "5";
            case 85 -> "6 2/3";
            case 90 -> "10";
            case 95 -> "20";
            default -> {
                double alpha = alpha_percent / 100.0;
                yield String.format("%.4f", 1.0 / (1.0 - alpha));
            }
        };
    }

    public static void main(String[] args) {
        int m = 1_000_000;

        // shuffling
        Integer[] keys = new Integer[m];

        for (int i = 0; i < m; i++) {
            keys[i] = i + 1;
        }

        List<Integer> key_list = Arrays.asList(keys);
        Collections.shuffle(key_list);

        Pair[] data = new Pair[m];

        for (int i = 0; i < m; i++) {
            String key_string = key_list.get(i).toString();
            String value_string = Integer.toString(i + 1);
            data[i] = new Pair(key_string, value_string);
        }

        int repetitions = 30;
        int query_count = 100_000;
        int[] alphas = { 75, 80, 85, 90, 95 };

        System.out.printf("%-14s %-6s %-10s %-22s %-22s%n", "", "N", "1/(1-α)", "Average Time in Seconds", "");
        System.out.printf("%-14s %-6s %-10s %-22s %-22s%n", "", "", "", "Open Hash", "Chained Hash");

        for (int alpha_percent : alphas) {
            int n = (alpha_percent * m) / 100;

            Pair[] used_data = Arrays.copyOfRange(data, 0, n);

            openHash open_table = new openHash(m);

            for (Pair p : used_data) {
                open_table.insert(p.key, p.value);
            }

            chainHash chain_table = new chainHash(m);

            for (Pair p : used_data) {
                chain_table.insert(p.key, p.value);
            }

            String[] queries = build_queries(used_data, query_count);

            double open_sum_s = 0.0;
            double chain_sum_s = 0.0;

            for (int r = 0; r < repetitions; r++) {
                long start = System.currentTimeMillis();

                for (String q : queries) {
                    open_table.lookup(q);
                }

                long finish = System.currentTimeMillis();

                open_sum_s += (finish - start) / 1000.0;

                start = System.currentTimeMillis();

                for (String q : queries) {
                    chain_table.lookup(q);
                }

                finish = System.currentTimeMillis();

                chain_sum_s += (finish - start) / 1000.0;
            }

            double open_avg_s = open_sum_s / repetitions;
            double chain_avg_s = chain_sum_s / repetitions;

            System.out.printf("Hash α = %-5s %-6s %-10s %-22.6f %-22.6f%n",
                    alpha_percent + "%",
                    format_k(n),
                    format_one_over_one_minus_alpha(alpha_percent),
                    open_avg_s,
                    chain_avg_s);
        }
    }
}
