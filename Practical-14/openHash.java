import java.util.Objects;

public class openHash {
    private static final byte EMPTY = 0;
    private static final byte OCCUPIED = 1;
    private static final byte DELETED = 2;

    private final int m;

    private final String[] keys;
    private final String[] values;

    private final byte[] states;

    private int size = 0;
    private final long seed;

    public openHash(int m) {
        if (m <= 0) {
            throw new IllegalArgumentException("m must be positive");
        }

        this.m = m;

        this.keys = new String[m];
        this.values = new String[m];

        this.states = new byte[m];

        this.seed = mix_64(System.nanoTime() ^ (long) System.identityHashCode(this));
    }

    // scatter method
    public int hash(String key) {
        return index_0(key) + 1;
    }

    // insert method
    public void insert(String key, String value) {
        Objects.requireNonNull(key, "key");

        if (isFull()) {
            throw new IllegalStateException("table is full");
        }

        int base = index_0(key);
        int first_deleted = -1;

        for (int step = 0; step < m; step++) {
            int index = (base + step) % m;
            byte state = states[index];

            if (state == EMPTY) {
                int put = (first_deleted != -1) ? first_deleted : index;

                keys[put] = key;
                values[put] = value;

                states[put] = OCCUPIED;

                size++;

                return;
            }

            if (state == DELETED) {
                if (first_deleted == -1) {
                    first_deleted = index;
                }

                continue;
            }

            if (key.equals(keys[index])) {
                values[index] = value;

                return;
            }
        }

        throw new IllegalStateException("no slot found");
    }

    // lookup method
    public String lookup(String key) {
        int index = find_index(key);
        return index == -1 ? null : values[index];
    }

    // remove method
    public String remove(String key) {
        int index = find_index(key);

        if (index == -1) {
            return null;
        }

        String old = values[index];

        keys[index] = null;
        values[index] = null;

        states[index] = DELETED;

        size--;

        return old;
    }

    public boolean isInTable(String key) {
        return find_index(key) != -1;
    }

    public boolean isFull() {
        return size >= m;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // internal helpers
    private int find_index(String key) {
        Objects.requireNonNull(key, "key");

        int base = index_0(key);

        for (int step = 0; step < m; step++) {
            int index = (base + step) % m;
            byte state = states[index];

            if (state == EMPTY) {
                return -1;
            }

            if (state == OCCUPIED && key.equals(keys[index])) {
                return index;
            }
        }

        return -1;
    }

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
