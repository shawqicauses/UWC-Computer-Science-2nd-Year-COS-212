// SHAWQI FARES
// 4515520
// PRACTICAL 11

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class ShuffleClass {
    public static void slow_shuffle(int[] shuffled, boolean[] isNotPresent) {
        int N = shuffled.length;

        Arrays.fill(isNotPresent, true);

        Random random = new Random();

        int i = 0;

        while (i < N - 1) {
            int r = random.nextInt(N) + 1;

            if (isNotPresent[r - 1]) {
                shuffled[i] = r;
                isNotPresent[r - 1] = false;
                i++;
            }
        }

        for (int j = 0; j < N; j++) {
            if (isNotPresent[j]) {
                shuffled[N - 1] = j + 1;
                break;
            }
        }
    }

    public static void biased_shuffle(int[] shuffled) {
        int N = shuffled.length;

        for (int i = 0; i < N; i++) {
            shuffled[i] = i + 1;
        }

        Random random = new Random();

        for (int i = 0; i < N; i++) {
            int r = random.nextInt(N);
            int temporary = shuffled[i];
            shuffled[i] = shuffled[r];
            shuffled[r] = temporary;
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

    public static void permutationExperiment(String shuffler, int N, int times) {
        Map<String, Integer> counts = new HashMap<>();

        int[] arr = new int[N];

        for (int t = 0; t < times; t++) {
            if (shuffler.equals("biased"))
                biased_shuffle(arr);
            else if (shuffler.equals("unbiased"))
                unbiased_shuffle(arr);
            else
                throw new IllegalArgumentException("not known shuffler: " + shuffler);

            StringBuilder key = new StringBuilder();

            for (int x : arr) {
                key.append(x);
            }

            counts.put(key.toString(), counts.getOrDefault(key.toString(), 0) + 1);
        }

        for (String key : counts.keySet()) {
            System.out.println(key + " " + counts.get(key));
        }
    }

    public static void main(String[] args) {
        int N = 5;

        int[] shuffled = new int[N];
        boolean[] isNotPresent = new boolean[N];
        slow_shuffle(shuffled, isNotPresent);

        System.out.print("slow_shuffle: ");

        for (int v : shuffled) {
            System.out.print(v + " ");
        }

        System.out.println();

        System.out.println("biased_shuffle: N = 3, 60000 times:");
        permutationExperiment("biased", 3, 60000);

        System.out.println("unbiased_shuffle: N = 3, 60000 times:");
        permutationExperiment("unbiased", 3, 60000);
    }
}