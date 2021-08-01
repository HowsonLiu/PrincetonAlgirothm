import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private double[] results = null;
    private int trials = 0;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if(trials <= 0 || n <= 0) throw new IllegalArgumentException();
        this.trials = trials;
        results = new double[trials];
        for(int i = 0; i < trials; ++i) {
            Percolation p = new Percolation(n);
            while(!p.percolates()) {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                if(!p.isOpen(x, y)) p.open(x, y);
            }
            results[i] = (double) p.numberOfOpenSites() / (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double allTrials = 0;
        for(int i = 0; i < trials; ++i)
            allTrials += results[i];
        allTrials /= trials;
        return allTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double s = 0;
        double meanVal = mean();
        for(int i = 0; i < trials; ++i) {
            double v = results[i]-meanVal;
            s += v*v;
        }
        s /= (trials-1);
        s = Math.sqrt(s);
        return s;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double meanVal = mean();
        double s = stddev();
        double res = meanVal - 1.96 * s / Math.sqrt(trials);
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double meanVal = mean();
        double s = stddev();
        double res = meanVal + 1.96 * s / Math.sqrt(trials);
        return res;
    }

    // test client (see below)
    public static void main(String[] args) {
        if(args.length < 2) throw new IllegalArgumentException();
        PercolationStats p = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        // PercolationStats p = new PercolationStats(2, 10000);
        StdOut.println("mean                    = "+ p.mean());
        StdOut.println("stddev                  = "+ p.stddev());
        StdOut.println("95% confidence interval = ["+ p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }

}