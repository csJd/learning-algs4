import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {

    private final double EPS = 1e-8;
    private List<LineSegment> list;

    public BruteCollinearPoints(Point[] points) {
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
            for (int j = i + 1; j < num - 2; ++j) {
                double slope = points[i].slopeTo(points[j]);
                for (int k = j + 1; k < num - 1; ++k) {
                    if (!doubleEquals(points[i].slopeTo(points[k]), slope)) {
                        continue;
                    }
                    for (int l = k + 1; l < num; ++l) {
                        if (doubleEquals(points[i].slopeTo(points[l]), slope)) {
                            list.add(new LineSegment(points[i], points[l]));
                        }
                    }
                }
            }
        }
    }   // finds all line segments containing 4 points

    private boolean doubleEquals(double a, double b) {
        return (a == b) || (a > b - EPS) && (a < b + EPS);
    }

    public int numberOfSegments() {
        return list.size();
    }       // the number of line segments

    public LineSegment[] segments() {
        return list.toArray(new LineSegment[list.size()]);
    }   // the line segments
}