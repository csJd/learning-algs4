import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {

    private final double EPS = 1e-8;
    private List<LineSegment> list;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }
        points = points.clone();
        Arrays.sort(points); // sort by coordinates
        int num = points.length;
        for (int i = 1; i < num; ++i) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        list = new LinkedList<>();
        for (int i = 0; i < num - 3; ++i) {
            Point[] ps = points.clone();
            Arrays.sort(ps, i + 1, num, ps[i].slopeOrder()); // sort ps[i+1, num) by slope to ps[i]
            Arrays.sort(ps, 0, i, ps[i].slopeOrder());
            int last = i + 1;
            for (int j = i + 1; j <= num; ++j) { // =num for last range
                if (j < num && doubleEquals(ps[i].slopeTo(ps[j]), ps[i].slopeTo(ps[last]))) {
                    continue; // notice: j < num is necessary
                }
                if (j - last > 2) {
                    boolean flag = true;
//                    for (int k = 0; k < i && flag; ++k) {
//                        if (doubleEquals(ps[k].slopeTo(ps[i]), ps[i].slopeTo(ps[last]))) {
//                            flag = false;
//                        }
//                    }
                    int le = 0, ri = i - 1; // bin search to get 100 score!
                    double slope = ps[i].slopeTo(ps[last]);
                    while (flag && le <= ri) {
                        int mid = (le + ri) >> 1;
                        double midSlope = ps[mid].slopeTo(ps[i]);
                        if (doubleEquals(midSlope, slope)) {
                            flag = false;
                        } else if (midSlope < slope) {
                            le = mid + 1;
                        } else {
                            ri = mid - 1;
                        }
                    }
                    if (flag) {
                        list.add(new LineSegment(ps[i], ps[j - 1]));
                    }
                }
                last = j;
            }
        }

    }    // finds all line segments containing 4 or more points

    public static void main(String[] args) {

        // read the n points from a file
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

    private boolean doubleEquals(double a, double b) {
        return a == b || ((a > b - EPS) && (a < b + EPS));
    }

    public int numberOfSegments() {
        return list.size();
    }    // the number of line segments

    public LineSegment[] segments() {
        return list.toArray(new LineSegment[list.size()]);
    }   // the line segments
}