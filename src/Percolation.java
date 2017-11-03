import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private boolean[][] flag;
    private int openCnt;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        flag = new boolean[n + 1][n + 1]; // default false
        openCnt = 0;
    }  // create n-by-n grid, with all sites blocked

    public static void main(String[] args) {
        StdOut.println("hello algs4! ");
    }  // test client (optional)

    private int rc2pos(int r, int c) {
        return (r - 1) * n + c - 1;
    }

    private boolean isIllegal(int i) {
        return i < 1 || i > n;
    }

    public void open(int row, int col) {
        if (isIllegal(row) || isIllegal(col)) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) return;
        ++openCnt;
        flag[row][col] = true;

        int pos = rc2pos(row, col);
        if (pos < n) {
            uf.union(n * n, pos); // n*n for top
        } else if (pos >= n * (n - 1)) {
            uf.union(n * n + 1, pos); // n*n+1 for bottom
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(pos, pos - n); // up
        }
        if (row < n && isOpen(row + 1, col)) {
            uf.union(pos, pos + n); // down
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(pos, pos - 1);  // left
        }
        if (col < n && isOpen(row, col + 1)) {
            uf.union(pos, pos + 1); // right
        }

    }  // open site (row, col) if it is not open already

    public boolean isOpen(int row, int col) {
        if (isIllegal(row) || isIllegal(col)) {
            throw new IllegalArgumentException();
        }
        return flag[row][col];

    }  // is site (row, col) open?

    public boolean isFull(int row, int col) {
        if (isIllegal(row) || isIllegal(col)) {
            throw new IllegalArgumentException();
        }
        return uf.connected(rc2pos(row, col), n * n);

    }  // is site (row, col) full?

    public int numberOfOpenSites() {
        return openCnt;

    }  // number of open sites

    public boolean percolates() {
        return uf.connected(n * n, n * n + 1);
    }  // does the system percolate?
}
