package Practical_17;

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
}
