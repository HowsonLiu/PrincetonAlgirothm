import java.util.Iterator;

public class Board {
    private final int[][] board;
    private final int m;
    private final int n;
    private int i0;
    private int j0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if(tiles == null) throw new IllegalArgumentException();
        m = tiles.length;
        n = tiles[0].length;
        board = new int[m][n];
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                board[i][j] = tiles[i][j];
                if(board[i][j] == 0) {
                    i0 = i;
                    j0 = j;
                }
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(Integer.toString(m)).append("\n");
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                str.append("\t").append(Integer.toString(board[i][j]));
            }
            str.append("\n");
        }
        return str.toString();
    }

    // board dimension n
    public int dimension() {
        return m;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) {
                if(board[i][j] != 0 && i*m+j+1 != board[i][j])
                    ++res;
            }
        }
        return res;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < m; ++j) {
                int cur = board[i][j];
                if(cur == 0) continue;
                int ti = cur / m;
                int tj = (cur % m)-1;
                res += Math.abs(ti-i)+Math.abs(tj-j);
            }
        }
        return res;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        Board rhs = (Board) y;
        if(rhs.m != m || rhs.n != n)
            return false;
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j){
                if(rhs.board[i][j] != board[i][j])
                    return false;
            }
        }
        return true;
    }

    private void swap(int i, int j, int k, int l) {
        int tmp = board[i][j];
        board[i][j] = board[k][l];
        board[k][l] = tmp;
        if(board[i][j] == 0) {
            i0 = i; j0 = j;
        }
        if(board[k][l] == 0) {
            i0 = k; j0 = l;
        }
    }

    private class BoardIterator implements Iterator<Board> {
        private boolean left, up, right, down;
        public BoardIterator() {
            if(i0 > 0) { up = true;};
            if(j0 > 0) { left = true;};
            if(i0 < m-1) { down = true;};
            if(j0 < n-1) { right = true;};
        }
        public boolean hasNext() {return up || left || down || right;}
        public Board next() {
            Board res = new Board(board);
            int i0 = res.i0;
            int j0 = res.j0;
            if(up) {
                res.swap(i0, j0, i0-1, j0);
                up = false;
            }
            else if(left) {
                res.swap(i0, j0, i0, j0-1);
                left = false;
            }
            else if(down) {
                res.swap(i0, j0, i0+1, j0);
                down = false;
            }
            else if(right) {
                res.swap(i0, j0, i0, j0+1);
                right = false;
            }
            return res;
        };
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new BoardIterator();
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board res = new Board(board);
        int i = 0, j = 0;
        int ti = m-1, tj = n-1;
//        int i = StdRandom.uniform(m);
//        int j = StdRandom.uniform(n);
//        int ti = StdRandom.uniform(m);
//        int tj = StdRandom.uniform(n);
//        if(i == ti && j == tj) return twin();
//        if(i == res.i0 && j == res.i0) return twin();
//        if(ti == res.i0 && res.j0 == tj) return twin();
        if(i == res.i0 && j == res.j0) ++i;
        if(ti == res.i0 && tj == res.j0) --ti;
        res.swap(i,j,ti,tj);
        return res;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}