package Practical_17;

import java.util.LinkedList;
import java.util.Queue;

public class tryBST {
    tNode root;

    public tryBST() {
        root = null;
    }

    public void insert(int key) {
        root = insert_rec(root, key);
    }

    private tNode insert_rec(tNode node, int key) {
        if (node == null) {
            return new tNode(key);
        }

        if (key < node.key) {
            node.left = insert_rec(node.left, key);
        }

        else if (key > node.key) {
            node.right = insert_rec(node.right, key);
        }

        return node;
    }

    public void delete(int key) {
        root = delete_rec(root, key);
    }

    private tNode delete_rec(tNode node, int key) {
        if (node == null) {
            return null;
        }

        if (key < node.key) {
            node.left = delete_rec(node.left, key);
        } else if (key > node.key) {
            node.right = delete_rec(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            tNode successor = min_node(node.right);
            node.key = successor.key;
            node.right = delete_rec(node.right, successor.key);
        }

        return node;
    }

    private tNode min_node(tNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    public boolean isBST() {
        return is_BST_rec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean is_BST_rec(tNode node, int min, int max) {
        if (node == null) {
            return true;
        }

        if (node.key <= min || node.key >= max) {
            return false;
        }

        return is_BST_rec(node.left, min, node.key) && is_BST_rec(node.right, node.key, max);
    }

    public int size() {
        return size_rec(root);
    }

    private int size_rec(tNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size_rec(node.left) + size_rec(node.right);
    }

    public int height() {
        return height_rec(root);
    }

    private int height_rec(tNode node) {
        if (node == null) {
            return -1;
        }

        return 1 + Math.max(height_rec(node.left), height_rec(node.right));
    }

    public void in_order() {
        in_order_rec(root);

        System.out.println();
    }

    private void in_order_rec(tNode node) {
        if (node == null) {
            return;
        }

        in_order_rec(node.left);

        System.out.print(node.key + " ");

        in_order_rec(node.right);
    }

    /**
     * Populate BST with numbers 1..2 ^ n - 1 in breadth-first (middle) order
     * so resulting tree is a perfect balanced BST.
     *
     * Strategy: find middle of range, insert it, then recursively
     * do the same for left half and right half.
     * Processing middles level-by-level is equivalent to a queue-based BFS
     * approach.
     */
    public void populate_balanced(int n) {
        int max = (1 << n) - 1; // 2 ^ n - 1

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[] { 1, max });

        while (!queue.isEmpty()) {
            int[] range = queue.poll();

            int low = range[0];
            int high = range[1];

            if (low > high) {
                continue;
            }

            int mid = low + (high - low) / 2;

            insert(mid);

            queue.add(new int[] { low, mid - 1 });
            queue.add(new int[] { mid + 1, high });
        }
    }

    public void remove_evens() {
        remove_evens_rec(root);
    }

    private void remove_evens_rec(tNode node) {
        if (node == null) {
            return;
        }

        remove_evens_rec(node.left);
        remove_evens_rec(node.right);

        if (node.key % 2 == 0) {
            delete(node.key);
        }
    }
}
