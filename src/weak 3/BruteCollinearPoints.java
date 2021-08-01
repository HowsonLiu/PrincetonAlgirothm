import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segs = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if(points == null)
            throw new IllegalArgumentException();

        int n = points.length;
        for(int p = 0; p < n; ++p) {
            for(int q = 0; q < n; ++q) {
                for(int r = 0; r < n; ++r) {
                    for(int s = 0; s < n; ++s) {
                        if(p == q || p == r || p == s || q == r || q == s || r == s) continue;
                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);
                        if(pq == pr && pr == ps) {
                             LineSegment seg = combine(points[p], points[q], points[r], points[s]);
                             boolean isExist = false;
                             for(int i = 0; i < segs.size(); ++i) {
                                 if(seg.toString().equals(segs.get(i).toString())) {
                                     isExist = true;
                                     break;
                                 }
                             }
                             if(!isExist) segs.add(seg);
                        }
                    }
                }
            }
        }
    }

    private static LineSegment combine(Point p, Point q, Point r, Point s) {
        Point arr[] = new Point[4];
        arr[0] = p;
        arr[1] = q;
        arr[2] = r;
        arr[3] = s;
        Arrays.sort(arr);
        return new LineSegment(arr[0], arr[3]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segs.size();
    }

    // the line segments
    public LineSegment[] segments(){
        LineSegment[] arr = new LineSegment[segs.size()];
        segs.toArray(arr);
        return arr;
    }

    public static void main(String[] args) {
        // read the n points from a file
        args = new String[1];
        args[0] = "testcase\\input8.txt";
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}