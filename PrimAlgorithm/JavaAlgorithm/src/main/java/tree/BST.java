package tree;

/**
 * 手写二叉树结构
 */
public class BST<T extends Comparable> {

    /**
     * 树的节点类
     */
    private static class Node<T> {
        public Node<T> left;//左节点
        public Node<T> right;//右节点
        public T value;//当前节点的值

        public Node(Node<T> left, Node<T> right, T value) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
    }

    private Node<T> node;

    private Node<T> leftNode;

    private Node<T> rightNode;

    private int size;

    /**
     * 添加树的节点
     *
     * @param e
     * @return
     */
    public void add(T e) {
        //1 判断当前的[node]是否为null
        if (node == null) {
            node = new Node<T>(null, null, e);
            size++;
            return;
        }

        //2 如果当前的node不为null 则判断是否大于小于或等于当前的node
        if (e.compareTo(node.value) > 0) {//如果[e]大于node的节点值，则遍历node的右子树


        } else if (e.compareTo(node.value) < 0) {//如果[e]小于node的节点值，则遍历node的左子树

        } else {

        }
        size++;

    }

}
