import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int virtualTop;
    private final int virtualBottom;
    private final WeightedQuickUnionUF data;
    // used only for avoiding "backwash problem" (see isFull() method below)
    private final WeightedQuickUnionUF dataWithNoBottom;
    // shows whether the site is open or not
    private final boolean[] openSitesMap;
    private int openSites = 0;
    // number of sites
    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw
                new IllegalArgumentException("n=" + n + "is smaller than 0.");
        // 0th - first virtual element, (n * n + 1)th - second virtual element
        data = new WeightedQuickUnionUF(n * n + 2);
        dataWithNoBottom = new WeightedQuickUnionUF(n * n + 2);
        virtualTop = 0;
        virtualBottom = n * n + 1;
        openSitesMap = new boolean[n * n + 2];
        for (int i = 0; i < openSitesMap.length; ++i) {
            openSitesMap[i] = false;
        }
        this.n = n;
        initializeVirtualSites();
    }

    private void initializeVirtualSites() {
        openSitesMap[virtualTop] = true;
        openSitesMap[virtualBottom] = true;
    }

    private int validateAndGetPosition(int row, int col) {
        if (row < 1 || col < 1 || row > n || col > n) {
            throw new IllegalArgumentException("rows or cols is out of range!");
        }
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // new element`s position
        int position = validateAndGetPosition(row, col);

        if (!openSitesMap[position]) {
            openSitesMap[position] = true;
            ++openSites;
        }

        // check neighbors` states (excluding virtual sites)

        // left side (index from 2 to (position - 1) and NOT on the left border)
        if (position > 1 && position % n != 1 && openSitesMap[position - 1]) {
            data.union(position, position - 1);
            dataWithNoBottom.union(position, position - 1);
        }
        // right side (index from (position + 1) to n * n) and NOT on the right border
        if (position < n * n && position % n != 0 && openSitesMap[position + 1]) {
            data.union(position, position + 1);
            dataWithNoBottom.union(position, position + 1);
        }
        // up side (index from 1 to position - n)
        if (position - n > virtualTop && openSitesMap[position - n]) {
            data.union(position, position - n);
            dataWithNoBottom.union(position, position - n);
        }
        // down side (index from position + n to n * n + 1)
        if (position + n < virtualBottom && openSitesMap[position + n]) {
            data.union(position, position + n);
            dataWithNoBottom.union(position, position + n);
        }

        // check virtual sides
        if (position <= n) {
            data.union(position, virtualTop);
            dataWithNoBottom.union(position, virtualTop);
        }
        if (position > n * n - n) data.union(position, virtualBottom);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openSitesMap[validateAndGetPosition(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int position = validateAndGetPosition(row, col);
        return dataWithNoBottom.find(position) == dataWithNoBottom.find(virtualTop)
                && data.find(position) == data.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return data.find(virtualTop) == data.find(virtualBottom);
    }

    // prints the map which shows if the site is open (prints '*') or not
    public void print() {
        int j = 1;
        for (int i = 1; i < openSitesMap.length - 1; ++i) {
            if (openSitesMap[i]) {
                System.out.print('*');
            }
            else {
                System.out.print(' ');
            }
            if (j == n) {
                System.out.print('\n');
                j = 0;
            }
            ++j;
        }
    }

    // prints the map which shows if the site is full (prints "true") or not
    public void printFullness() {
        for (int row = 1; row < n + 1; ++row) {
            for (int col = 1; col < n + 1; ++col) {
                System.out.print(isFull(row, col) + " ");
            }
            System.out.print('\n');
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation graph = new Percolation(5);

        graph.open(5, 1);
        graph.open(5, 2);
        graph.open(5, 5);
        graph.open(5, 4);
        graph.open(4, 1);
        graph.open(4, 3);
        graph.open(3, 2);
        graph.open(3, 4);
        graph.open(3, 5);
        graph.open(2, 4);
        graph.open(1, 1);
        graph.open(1, 2);
        graph.open(1, 4);
        graph.open(4, 5);

        graph.print();
        System.out.println("-----------------------");
        graph.printFullness();
        System.out.println(graph.percolates());
    }
}
