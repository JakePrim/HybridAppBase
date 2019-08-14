package datastructure.tree;

/**
 * 二分搜索树数据结构
 *
 * @param <T>
 */
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

    /**
     * 添加新的元素
     *
     * @param value 元素
     */
    public void add(T value) {
//        if (root == null) {
//            root = new Node<>(value);
//            size++;
//        } else {
//            add(root, value);
//        }
        root = add(root, value);
    }

    private Node<T> add(Node<T> node, T value) {
        //先写递归的终止条件
//        if (value.equals(node.value))
//            return;
        //优化

//        else if (value.compareTo(node.value) < 0 && node.left == null) {
//            node.left = new Node<>(value);
//            size++;
//            return;
//        } else if (value.compareTo(node.value) > 0 && node.right == null) {
//            node.right = new Node<>(value);
//            size++;
//            return;
//        }
//        if (value.compareTo(node.value) < 0) {//添加的元素小于节点 则添加到左子树
//            add(node.left, value);
//        } else {//添加的元素大于节点 则添加到右子树
//            add(node.right, value);
//        }
        if (node == null) {
            size++;
            return new Node<>(value);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = add(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = add(node.right, value);
        }

        return node;
    }

    //是否包含元素e
    public boolean contains(T value) {
        return contains(root, value);
    }

    //以node为根的二分搜索树中是否包含元素e
    private boolean contains(Node<T> node, T e) {
        if (node == null) {
            return false;
        }
        if (e.compareTo(node.value) == 0) {
            return true;
        } else if (e.compareTo(node.value) < 0) {
            return contains(node.left, e);
        } else {
            return contains(node.right, e);
        }
    }

    //前序遍历
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node<T> node) {
        if (node == null)
            return;
        System.out.println("node = [" + node.value + "]");
        preOrder(node.left);
        preOrder(node.right);
    }
}
