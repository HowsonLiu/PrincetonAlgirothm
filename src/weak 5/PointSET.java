import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> m_set;

    // construct an empty set of points
    public PointSET() {
        m_set = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return m_set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return m_set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        m_set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return m_set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : m_set)
            p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> res = new ArrayList<>();
        for (Point2D mp : m_set) {
            if (rect.contains(mp)) res.add(mp);
        }
        return res;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D res = null;
        for (Point2D mp : m_set) {
            if (res == null) res = mp;
            if (mp.distanceTo(p) < res.distanceTo(p)) res = mp;
        }
        return res;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}