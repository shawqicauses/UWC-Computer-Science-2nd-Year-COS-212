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

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}