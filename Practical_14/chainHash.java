package Practical_14;

import java.util.Objects;

public class chainHash {
    private static class Node {
        final String key;
        String value;

        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int m;

    private final Node[] table;

    private int size = 0;

    private final long seed;

    public chainHash(int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("m must be positive");
        }

        this.m = m;

        this.table = new Node[m];

        this.seed = mix_64(System.nanoTime() ^ (long) System.identityHashCode(this));
    }

    // scatter method
    public int hash(String key) {
        return index_0(key) + 1;
    }

    // insert method
    public void insert(String key, String value) {
        Objects.requireNonNull(key, "key");

        int index = index_0(key);

        Node head = table[index];

        if (head == null) {
            table[index] = new Node(key, value);

            size++;

            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null) {
            if (key.equals(current.key)) {
                current.value = value;
                return;
            }

            previous = current;
            current = current.next;
        }

        previous.next = new Node(key, value);

        size++;
    }

    // lookup method
    public String lookup(String key) {
        Objects.requireNonNull(key, "key");

        int index = index_0(key);

        Node current = table[index];

        while (current != null) {
            if (key.equals(current.key)) {
                return current.value;
            }

            current = current.next;
        }

        return null;
    }

    // remove method
    public String remove(String key) {
        Objects.requireNonNull(key, "key");

        int index = index_0(key);

        Node current = table[index];
        Node previous = null;

        while (current != null) {
            if (key.equals(current.key)) {
                String old = current.value;

                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;

                return old;
            }

            previous = current;
            current = current.next;
        }

        return null;
    }

    public boolean isInTable(String key) {
        return lookup(key) != null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // internal helpers
    private int index_0(String key) {
        Objects.requireNonNull(key, "key");

        long x = ((long) key.hashCode()) ^ seed;
        long mixed = mix_64(x);
        int positive = (int) (mixed & 0x7fffffffL);

        return positive % m;
    }

    private static long mix_64(long z) {
        z = (z ^ (z >>> 33)) * 0xff51afd7ed558ccdL;
        z = (z ^ (z >>> 33)) * 0xc4ceb9fe1a85ec53L;
        return z ^ (z >>> 33);
    }
}
