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

    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}