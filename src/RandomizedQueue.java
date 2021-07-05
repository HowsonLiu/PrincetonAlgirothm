import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node{
        Item item;
        Node next;
    }

    private Node head;
    private Node tail;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        head = tail = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return 0 == size;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
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

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item res;
        Node prev = null;
        Node curr = head;
        while (null != curr) {
            if (StdRandom.bernoulli(1.0f / size))
                break;
            prev = curr;
            curr = curr.next;
        }
        if (null == curr) {
            res = head.item;
            if(tail == head) tail = null;
            head = head.next;
        }
        else {
            res = curr.item;
            if(null != prev) prev.next = curr.next;
            if(curr == head) head = curr.next;
            if(curr == tail) tail = prev;
        }
        --size;
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node curr = head;
        while(null != curr) {
            if (StdRandom.bernoulli(1.0f / size))
                break;
            curr = curr.next;
        }
        if(null == curr)
            return head.item;
        else
            return curr.item;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Node cur = head;
        public boolean hasNext() {return null != cur;}
        public void remove() { throw new UnsupportedOperationException(); };
        public Item next() {
            Item res = cur.item;
            cur = cur.next;
            return res;
        };
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> ds = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if(str.equals("-")) {
                StdOut.println("remove random: " + ds.dequeue());
            }
            else if(str.equals("sample")) {
                StdOut.println("sample: " + ds.sample());
            }
            else {
                ds.enqueue(str);
            }
            StdOut.println("RandomizedQueue: ");
            Iterator<String> it = ds.iterator();
            while(it.hasNext()) {
                StdOut.print(it.next() + " ");
            }
            StdOut.println("");
        }
    }

}