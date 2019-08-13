package datastructure.tree;

public class BTS<T extends Comparable<T>> {
    public static class Node<T> {
        public T value;
        public Node<T> left;
        public Node<T> right;

        public Node(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node<T> root;

    private int size;

    public BTS() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void add(T value) {
        if (root == null) {
            root = new Node<>(value);
            size++;
            return;
        }



    }
}
