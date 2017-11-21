import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int n;

    public Deque() {                           // construct an empty deque
        first = last = null;
        n = 0;
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

    public boolean isEmpty() {                 // is the deque empty?
        return n == 0;
    }

    public int size() {                        // return the number of items on the deque
        return n;
    }

    public void addFirst(Item item) {          // add the item to the front
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.next = first;
        node.prev = null;

        if (isEmpty()) { // Be careful!
            last = node;
        } else {
            first.prev = node;
        }
        first = node;
        ++n;
    }

    public void addLast(Item item) {          // add the item to the end
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node node = new Node();
        node.item = item;
        node.prev = last;
        node.next = null;

        if (isEmpty()) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        ++n;
    }

    public Item removeFirst() {               // remove and return the item from the front
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        --n;
        if (isEmpty()) {
            last = null;
        } else {
            first.prev = null;
        }
        return item;
    }

    public Item removeLast() {                // remove and return the item from the end
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.prev;
        --n;
        if (isEmpty()) {
            first = null;
        } else {
            last.next = null;
        }
        return item;
    }

    @Override
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new Iterator<Item>() {
            Node cur = first;

            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = cur.item;
                cur = cur.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    private class Node {
        Item item;
        Node next;
        Node prev;
    }
}
