import java.util.Arrays;
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

    public static void unbiased_shuffle(int[] shuffled) {
        int N = shuffled.length;

        for (int i = 0; i < N; i++) {
            shuffled[i] = i + 1;
        }

        Random random = new Random();

        for (int i = 0; i < N; i++) {
            int r = i + random.nextInt(N - i);
            int temporary = shuffled[i];
            shuffled[i] = shuffled[r];
            shuffled[r] = temporary;
        }
    }

    public static void main(String[] args) {
        int N = 1 << 20; // N = 2 ^ 20 = 1_048_576
        int usable_n = 950_000; // first 950_000 pairs

        // shuffling
        int[] keys = new int[N];
        unbiased_shuffle(keys);

        Pair[] data = new Pair[N];

        for (int i = 0; i < N; i++) {
            String key_string = Integer.toString(keys[i]);
            String value_string = Integer.toString(i + 1);
            data[i] = new Pair(key_string, value_string);
        }

        Pair[] used_data = Arrays.copyOfRange(data, 0, usable_n);

        if (used_data.length != usable_n) {
            throw new AssertionError("Un-Expected used_data length.");
        }
    }
}
