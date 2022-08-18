import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials is out of range!");
        }
        results = new double[trials];
        int openSites = 0;
        Percolation sites;
        for (int i = 0; i < trials; ++i) {
            sites = new Percolation(n);
            while (!sites.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                if (!sites.isOpen(row, col)) {
                    sites.open(row, col);
                    ++openSites;
                }
            }
            results[i] = (double) openSites / (n * n);
            openSites = 0;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(results.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats experiment = new PercolationStats(n, t);
        System.out.println("mean = " + experiment.mean());
        System.out.println("stddev = " + experiment.stddev());
        System.out.println(
                "95% confidence interval = " + '[' + experiment.confidenceLo() +
                        ", " + experiment.confidenceHi() + ']');
    }
}
