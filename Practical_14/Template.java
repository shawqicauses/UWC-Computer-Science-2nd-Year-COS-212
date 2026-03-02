package Practical_14;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    public static void main(String[] args) {
        int N = 1 << 20; // N = 2 ^ 20 = 1_048_576
        int usable_n = 950_000; // first 950_000 pairs

        // shuffling
        Integer[] keys = new Integer[N];

        for (int i = 0; i < N; i++) {
            keys[i] = i + 1; // keys go from 1 to N
        }

        List<Integer> key_list = Arrays.asList(keys);
        Collections.shuffle(key_list);

        Pair[] data = new Pair[N];

        for (int i = 0; i < N; i++) {
            String key_string = key_list.get(i).toString();
            String value_string = Integer.toString(i + 1);
            data[i] = new Pair(key_string, value_string);
        }

        Pair[] used_data = Arrays.copyOfRange(data, 0, usable_n);

        if (used_data.length != usable_n) {
            throw new AssertionError("Un-Expected used_data length.");
        }
    }

}
