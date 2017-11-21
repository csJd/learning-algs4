import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials; // variable 'trials' can be made 'final'; it is initialized only in the declaration or constructor.
    private final double[] records;
    private final double MUL = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        records = new double[trials];

        for (int i = 0; i < trials; ++i) {
            records[i] = 0;
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    records[i] += 1;
                }
            }
            records[i] /= (n * n);
        }

    } // perform trials independent experiments on an n-by-n grid

    public static void main(String[] args) {
        StdOut.println("hello algs4 again!");
        if (args.length != 2) {
            StdOut.println("usage: PercolationStats n trials");
            return;
        }
        int n = Integer.valueOf(args[0]);
        int trials = Integer.valueOf(args[1]);
        StdOut.printf("n = %d, trials = %d\n", n, trials);
        // int n = StdIn.readInt();
        // int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.printf("mean\t = %f\n", stats.mean());
        StdOut.printf("stddev\t = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = [%f, %f]", stats.confidenceLo(), stats.confidenceHi());
    } // test client (described below)

    public double mean() {
        return StdStats.mean(records);
    } // sample mean of percolation threshold

    public double stddev() {
        return StdStats.stddev(records);
    } // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return mean() - MUL * stddev() / Math.sqrt(trials);
    } // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return mean() + MUL * stddev() / Math.sqrt(trials);
    } // high endpoint of 95% confidence interval
}