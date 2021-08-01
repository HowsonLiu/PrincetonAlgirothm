import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node{
        Item item;
        Node next;
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty deque
    public Deque() {
        head = tail = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {return size == 0;}

    // return the number of items on the deque
    public int size() {return size;}

    // add the item to the front
    public void addFirst(Item item) {
        if(null == item)
            throw new IllegalArgumentException();

        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        if(isEmpty()) tail = head;
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(null == item)
            throw new IllegalArgumentException();

        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.next = null;
        if(isEmpty()) head = tail;
        if(null != oldTail) oldTail.next = tail;
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(isEmpty())
            throw new NoSuchElementException();

        Item res = head.item;
        head = head.next;
        --size;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(isEmpty())
            throw new NoSuchElementException();

        Item res = tail.item;
        if(size() == 1) {
            head = tail = null;
        }
        else {
            Node newTail = head;
            while(newTail.next != tail) newTail = newTail.next;
            tail = newTail;
            tail.next = null;
        }
        --size;
        return res;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node cur = head;
        public boolean hasNext() {return null != cur;}
        public void remove() { throw new UnsupportedOperationException(); };
        public Item next() {
            Item res = cur.item;
            cur = cur.next;
            return res;
        };
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> ds = new Deque<String>();
        while(!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if(str.equals("-l")) {
                StdOut.println("remove last: " + ds.removeLast());
            }
            else if(str.equals("-f")) {
                StdOut.println("remove first: " + ds.removeFirst());
            }
            else if(str.startsWith("+l")) {
                ds.addLast(str);
                StdOut.println("add last: " + str);
            }
            else if(str.startsWith("+f")) {
                ds.addFirst(str);
                StdOut.println("add first: " + str);
            }
            StdOut.println("Deque: ");
            Iterator<String> it = ds.iterator();
            while(it.hasNext()) {
                StdOut.print(it.next() + " ");
            }
            StdOut.println("");
        }
    }

}