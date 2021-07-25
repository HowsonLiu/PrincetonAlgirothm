import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        for(int i = 0; i < points.length-3; ++i) {
            Arrays.sort(points);
            Arrays.sort(points, points[i].slopeOrder());
            // 0 must be self
            for(int p = 0, lo = 1, hi = 2; hi < points.length; ++hi) {
                while(hi < points.length && Double.compare(points[p].slopeTo(points[lo]), points[p].slopeTo(points[hi])) == 0)
                    ++hi;
                // compareTo makes sure that the line is start from the minimum point, so it is unique, fuck, awesome!
                if(hi - lo >= 3 && points[p].compareTo(points[lo]) < 0)
                    segs.add(new LineSegment(points[p], points[hi-1]));
                lo = hi;
            }
        }
    }

    public int numberOfSegments() {
        return segs.size();
    }
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[segs.size()];
        segs.toArray(res);
        return res;
    }

    public static void main(String[] args) {
        // read the n points from a file
        args = new String[1];
        args[0] = "testcase\\input6.txt";
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}