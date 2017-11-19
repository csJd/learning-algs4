/* EP167 hint : Use an array representation (with resizing). To remove an item,
 * swap one at a random position (indexed 0 throughN-1 ) with the one at the last position (index N-1 ).
 * Then delete and return the last object, as in ResizingArrayStack.
 */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rq;
    private int n; // current number of elements
    private int MAX_N; // the size of rq[]

    public RandomizedQueue() {
        MAX_N = 1;
        rq = (Item[]) new Object[MAX_N];
        n = 0;
    }                // construct an empty randomized queue

    public static void main(String[] args) {

    }  // unit testing (optional)

    private void resizeRQ(int siz) {
        Item[] nrq = (Item[]) new Object[siz];
        for (int i = 0; i < MAX_N; ++i) {
            nrq[i] = rq[i];
        }
        MAX_N = siz;
        rq = nrq;
    }

    private void swap(int i, int j) {
        Item tmp = rq[i];
        rq[i] = rq[j];
        rq[j] = tmp;
    }

    public boolean isEmpty() {
        return n == 0;
    }                // is the randomized queue empty?

    public int size() {
        return n;
    }                       // return the number of items on the randomized queue

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (n >= MAX_N) {
            resizeRQ(MAX_N * 2);
        }
        rq[n++] = item;
    }        // add the item

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int ind = StdRandom.uniform(n);
        swap(ind, n - 1);
        Item item = rq[--n];
        rq[n] = null;
        return item;
    }                   // remove and return a random item

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int ind = StdRandom.uniform(n);
        return rq[ind];
    }                    // return a random item (but do not remove it)

    @Override
    public Iterator<Item> iterator() {
        Item[] srq = rq.clone();
        StdRandom.shuffle(srq);
        return new Iterator<Item>() {
            int cur = 0;

            @Override
            public boolean hasNext() {
                return cur < n;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return srq[cur++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }        // return an independent iterator over items in random order
}
