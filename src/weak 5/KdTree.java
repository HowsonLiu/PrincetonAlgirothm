import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;

public class KdTree {

    private class Node {
        private Point2D point;
        private final boolean isVertical;
        private static final boolean VERTICAL = true;
        private static final boolean HORIZONTAL = false;
        private Node left;
        private Node right;

        public Node(Point2D p, boolean ver) {
            this.point = p;
            this.isVertical = ver;
            left = null;
            right = null;
        }

        public int compareTo(Point2D rhs) {
            if (isVertical)
                return Double.compare(rhs.x(), point.x());
            else
                return Double.compare(rhs.y(), point.y());
        }
    }

    private Node root;
    private int size;

    private Node put(Node node, Point2D val, boolean ver) {
        if (node == null) {
            size++;
            return new Node(val, ver);
        }
        if (node.point.equals(val)) return node;
        int cmp = node.compareTo(val);
        if (cmp < 0)
            node.left = put(node.left, val, !ver);
        else
            node.right = put(node.right, val, !ver);
        return node;
    }

    private void put(Point2D val) {
        root = put(root, val, Node.VERTICAL);
    }

    private Node get(Point2D val) {
        Node x = root;
        while (x != null) {
            if (x.point.equals(val)) return x;
            int cmp = x.compareTo(val);
            if (cmp < 0) x = x.left;
            else x = x.right;
        }
        return null;
    }

    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (isEmpty()) return 0;
//        int size = 0;
//
//        // mid order travel
//        Stack<Node> stk = new Stack<>();
//        Node cur = root;
//        while (cur != null || !stk.isEmpty()) {
//            while (cur != null) {
//                stk.push(cur);
//                cur = cur.left;
//            }
//            cur = stk.pop();
//            size++;
//            cur = cur.right;
//        }
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        put(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
    }

    // draw all points to standard draw
    public void draw() {
        // mid order travel
        Stack<Node> stk = new Stack<>();
        Node cur = root;
        while (cur != null || !stk.isEmpty()) {
            while (cur != null) {
                stk.push(cur);
                cur = cur.left;
            }
            cur = stk.pop();
            cur.point.draw();
            cur = cur.right;
        }
    }

    private ArrayList<Point2D> dfs(Node node, RectHV rect) {
        ArrayList<Point2D> res = new ArrayList<>();
        if (node == null) return res;
        if (rect.contains(node.point)) res.add(node.point);
        if (node.isVertical) {
            double x = node.point.x();
            if (x > rect.xmax()) res.addAll(dfs(node.left, rect));
            else if (x < rect.xmin()) res.addAll(dfs(node.right, rect));
            else {
                res.addAll(dfs(node.left, rect));
                res.addAll(dfs(node.right, rect));
            }
        } else {
            double y = node.point.y();
            if (y > rect.ymax()) res.addAll(dfs(node.left, rect));
            else if (y < rect.ymin()) res.addAll(dfs(node.right, rect));
            else {
                res.addAll(dfs(node.left, rect));
                res.addAll(dfs(node.right, rect));
            }
        }
        return res;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        return dfs(root, rect);
    }

    // return champion
    private Node dfs2(Node cur, Point2D p, Node champion) {
        if (cur == null) return champion;                                    // champion still champion
        if (cur.point.distanceTo(p) < champion.point.distanceTo(p)) {        // yes, i can beat the champion, i have to search both way
            Node nextChampion = dfs2(cur.left, p, cur);                      // start from left
            if (nextChampion == cur)                                          // wow, after walk though left way, im still champion
                nextChampion = dfs2(cur.right, p, cur);                      // i have to search right way
            else
                ;                                                            // some one took my champion in left way, so i dont need to search in right way
            return nextChampion;
        } else {                                                              // no, i cant beat the champion, so i can only search in a half way
            int cmp = cur.compareTo(p);
            if (cmp < 0) return dfs2(cur.left, p, champion);
            else return dfs2(cur.right, p, champion);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        return dfs2(root, p, root).point;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}