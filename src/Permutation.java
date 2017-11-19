import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException();
        }
        int k = Integer.valueOf(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        Iterator<String> it = rq.iterator();
        while (k != 0) {
            StdOut.println(it.next());
            k--;
        }
    }
}
