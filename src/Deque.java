import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {

    private Node first = null;
    private Node last = null;
    private int n = 0;

    private class Node {
        T item;
        Node next;
        Node prev;
    }

    public Deque() {                           // construct an empty deque
        first = last = null;
    }

    public boolean isEmpty() {                 // is the deque empty?
        return first == null;
    }

    public int size() {                        // return the number of items on the deque
        return n;
    }

    public void addFirst(T item) {          // add the item to the front
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.next = first;
        node.prev = null;
        first = node;
        ++n;
    }

    public void addLast(T item) {          // add the item to the end
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.prev = last;
        node.next = null;
        last.next = node;

        last = node;
        ++n;
    }

    public T removeFirst() {               // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        T item = first.item;
        first = first.next;
        --n;
        if (isEmpty()) {
            last = null;
        }
        return item;
    }

    public T removeLast() {                // remove and return the item from the end
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        T item = last.item;
        last = last.prev;
        --n;
        if (n == 0) {
            first = null;
        }
        return item;
    }

    @Override
    public Iterator<T> iterator() {         // return an iterator over items in order from front to end
        return new Iterator<T>() {
            Node cur = first;

            @Override
            public boolean hasNext() {
                return cur.next != null;
            }

            @Override
            public T next() {
                T item = cur.item;
                cur = cur.next;
                return item;
            }
        };
    }

    public static void main(String[] args) {   // unit testing (optional)
        Deque<Integer> dq = new Deque<Integer>();
        for (int i = 0; i < 10; ++i) {
            dq.addFirst(i);
        }
        for (int i : dq) {
            System.out.println(i);
        }
    }
}
