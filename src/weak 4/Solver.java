import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {
    private Node goalNode;

    private static class Node implements Comparable<Node> {
        public int move;
        public Board board;
        public Node parent;
        public Node(Board b, int move, Node parent) {
            board = b;
            this.move = move;
            this.parent = parent;
        }

        @Override
        public int compareTo(Node o) {
            // use manhattan distance
            int lhs = board.manhattan() + move;
            int rhs = o.board.manhattan() + o.move;
            return Integer.compare(lhs, rhs);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if(initial == null)
            throw new IllegalArgumentException();


        Node root = new Node(initial, 0, null);
        root.move = 0;
        root.parent = null;
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(root);

        Node twinRoot = new Node(initial.twin(), 0, null);
        twinRoot.move = 0;
        twinRoot.parent = null;
        MinPQ<Node> twinPq = new MinPQ<>();
        twinPq.insert(twinRoot);

        while(true) {
            Node twinCur = twinPq.delMin();
            Node cur = pq.delMin();
            if(cur.board.isGoal()) {
                goalNode = cur;
                break;
            }
            if(twinCur.board.isGoal()) {
                break;
            }
            for (Board next : cur.board.neighbors()) {
                if (cur.parent != null && next.equals(cur.parent.board)) continue;
                Node nextNode = new Node(next, cur.move+1, cur);
                pq.insert(nextNode);
            }
            for(Board twinNext : twinCur.board.neighbors()) {
                if (twinCur.parent != null && twinNext.equals(twinCur.parent.board)) continue;
                Node nextNode = new Node(twinNext, twinCur.move+1, twinCur);
                twinPq.insert(nextNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goalNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return goalNode == null ? -1 : goalNode.move;
    }

    private class SolutionIterator implements Iterator<Board> {
        private final Board[] path;
        private int index;
        public SolutionIterator() {
            path = new Board[goalNode.move+1];
            Node cur = goalNode;
            while(cur.parent != null) {
                path[cur.move] = cur.board;
                cur = cur.parent;
            }
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < path.length;
        }

        @Override
        public Board next() {
            return path[index++];
        }
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return new SolutionIterator();
            }
        };
    }

    // test client (see below)
    public static void main(String[] args) {
//         create initial board from file
//        args = new String[1];
//        args[0] = "testcase\\puzzle3x3-unsolvable.txt";
//        args[0] = "testcase\\puzzle04.txt";
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}