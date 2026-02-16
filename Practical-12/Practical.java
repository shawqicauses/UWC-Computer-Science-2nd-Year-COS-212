// Shawqi Fares
// 4515520
// Practical 12

import java.util.Random;

class Practical {
    static long countOn3 = 0;
    static long countOn2A = 0;
    static long countOn2B = 0;
    static long countOn = 0;

    public static int mcsOn3(int[] X) {
        int n = X.length;

        int maxSoFar = 0;

        countOn3 = 0;

        for (int low = 0; low < n; low++) {

            for (int high = low; high < n; high++) {
                int sum = 0;

                for (int r = low; r <= high; r++) {
                    sum += X[r];
                    countOn3++;
                }

                if (sum > maxSoFar) {
                    maxSoFar = sum;
                }
            }
        }

        return maxSoFar;
    }

    public static int mcsOn2A(int[] X) {
        int n = X.length;

        int maxSoFar = 0;

        countOn2A = 0;

        for (int low = 0; low < n; low++) {
            int sum = 0;

            for (int r = low; r < n; r++) {
                sum += X[r];
                countOn2A++;

                if (sum > maxSoFar) {
                    maxSoFar = sum;
                }
            }
        }

        return maxSoFar;
    }

    // using prefix sum
    public static int mcsOn2B(int[] X) {
        int n = X.length;

        int[] sumTo = new int[n + 1];

        sumTo[0] = 0;

        for (int i = 0; i < n; i++) {
            sumTo[i + 1] = sumTo[i] + X[i];
        }

        int maxSoFar = 0;

        countOn2B = 0;

        for (int low = 0; low < n; low++) {

            for (int high = low; high < n; high++) {

                int sum = sumTo[high + 1] - sumTo[low];

                countOn2B++;

                if (sum > maxSoFar) {
                    maxSoFar = sum;
                }
            }
        }

        return maxSoFar;
    }

    public static int mcsOn(int[] X) {
        int n = X.length;

        int maxSoFar = 0;

        int maxToHere = 0;

        countOn = 0;

        for (int i = 0; i < n; i++) {
            maxToHere = Math.max(maxToHere + X[i], 0);
            maxSoFar = Math.max(maxSoFar, maxToHere);
            countOn++;
        }

        return maxSoFar;
    }

    public static int[] generateRandomArray(int n, Random random) {
        int[] X = new int[n];

        for (int i = 0; i < n; i++) {
            int value = random.nextInt(n) + 1;

            if (random.nextBoolean()) {
                value = -value;
            }

            X[i] = value;
        }

        return X;
    }

    public static void main(String[] args) {
        int[] numbers = { 100, 1000, 10000, 100000, 1000000 };

        System.out.printf("%-12s%-24s%-24s%-24s%-24s%n", "n", "O(n^3)", "O(n^2)", "O(n^2)b", "O(n)");

        Random random = new Random(42);

        for (int n : numbers) {
            int[] X = generateRandomArray(n, random);

            long mainCountOn3 = -1;

            mcsOn3(X);
            mainCountOn3 = countOn3;

            mcsOn2A(X);
            long mainCountOn2A = countOn2A;

            mcsOn2B(X);
            long mainCountOn2B = countOn2B;

            mcsOn(X);
            long mainCountOn = countOn;

            System.out.printf("%-12d%-24s%-24d%-24d%-24d%n", n, mainCountOn3, mainCountOn2A, mainCountOn2B,
                    mainCountOn);
        }
    }
}