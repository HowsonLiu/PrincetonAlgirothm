public class Percolation {
    private int n = 0;
    private int[] vec = null;
    private int topNodeIndex;
    private int bottomNodeIndex;
    private int openCnt;

    private boolean isValidInternal(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    private int getIndexInternal(int row, int col) {
        if(!isValidInternal(row, col))
            throw new IllegalArgumentException();
        return row*n+col;
    }

    private int FindRootInternal(int index) {
        while(vec[index] != index)
            index = vec[index];
        return index;
    }

    private void UnionInternal(int lhs, int rhs) {
        int lhsRoot = FindRootInternal(lhs);
        int rhsRoot = FindRootInternal(rhs);

        if(lhsRoot > rhsRoot) {
            vec[rhsRoot] = lhsRoot;
        }
        else {
            vec[lhsRoot] = rhsRoot;
        }
    }

    private boolean isConnect(int lhs, int rhs) {
        return FindRootInternal(lhs) == FindRootInternal(rhs);
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();
        this.n = n;
        vec = new int[n*n+2];
        for(int i = 0; i < n*n; ++i)
            vec[i] = -1;
        topNodeIndex = n*n+1;
        bottomNodeIndex = n*n;
        vec[topNodeIndex] = topNodeIndex;
        vec[bottomNodeIndex] = bottomNodeIndex;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        row--; col--;
        int index = getIndexInternal(row, col);
        vec[index] = index;
        if(isValidInternal(row-1, col) && isOpenInternal(row-1, col)) {
            int up = getIndexInternal(row-1, col);
            UnionInternal(index, up);
        }
        if(isValidInternal(row+1, col) && isOpenInternal(row+1, col)) {
            int down = getIndexInternal(row+1, col);
            UnionInternal(index, down);
        }
        if(isValidInternal(row, col-1) && isOpenInternal(row, col-1)) {
            int left = getIndexInternal(row, col-1);
            UnionInternal(index, left);
        }
        if(isValidInternal(row, col+1) && isOpenInternal(row, col+1)) {
            int right = getIndexInternal(row, col+1);
            UnionInternal(index, right);
        }
        if(index < n) UnionInternal(index, topNodeIndex);
        if(index >= n*(n-1)) UnionInternal(index, bottomNodeIndex);
        openCnt++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row--; col--;
        return isOpenInternal(row, col);
    }

    private boolean isOpenInternal(int row, int col) {
        int index = getIndexInternal(row, col);
        return vec[index] != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row--; col--;
        return isFullInternal(row, col);
    }

    private boolean isFullInternal(int row, int col) {
        if(!isOpenInternal(row, col)) return false;
        int index = getIndexInternal(row, col);
        return isConnect(index, topNodeIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCnt;
    }

    // does the system percolate?
    public boolean percolates() {
        return isConnect(topNodeIndex, bottomNodeIndex);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
