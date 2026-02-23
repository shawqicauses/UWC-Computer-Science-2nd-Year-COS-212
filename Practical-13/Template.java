import java.text.DecimalFormat;

public class Template {
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