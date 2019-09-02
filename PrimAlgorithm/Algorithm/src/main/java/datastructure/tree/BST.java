package datastructure.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树数据结构
 *
 * @param <T>
 */
public class BST<T extends Comparable<T>> {
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

    public BST() {
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

    //前序遍历递归实现
    private void preOrder(Node<T> node) {
        if (node == null)
            return;
        System.out.println("node = [" + node.value + "]");
        preOrder(node.left);//遍历左子树 不为空就会一直遍历
        preOrder(node.right);//遍历右子树 不为空就会一直遍历
    }

    //前序遍历非递归实现 通过栈结构深度优先遍历
    public void preOrderNR() {
        if (root != null) {
            //新建一个栈
            Stack<Node<T>> stack = new Stack<>();
            stack.push(root);
            //遍历栈 直到栈元素为空为止
            while (!stack.empty()) {
                //取出栈顶元素 然后判断该元素是否有左子树或者右子树
                Node<T> cur = stack.pop();
                System.out.println(cur.value);
                //先压入右子树
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                //再压入左子树
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
    }

    //广度优先遍历又叫做层序遍历
    public void lastOrder() {
        Queue<Node<T>> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node<T> cur = q.remove();
            System.out.println(cur.value);
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }
    }

    //中序遍历 二分搜索树排序的结果 是顺序排列的
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node<T> node) {
        if (node == null)
            return;
        inOrder(node.left);
        System.out.println("node = [" + node.value + "]");
        inOrder(node.right);
    }

    /**
     * 3
     * <p>
     * <p>
     * <p>
     * <p>
     * 5
     */
    public void inOrderNR() {

    }

    //后序遍历
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node<T> node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        //遍历完左右节点才会进行操作
        System.out.println("node = [" + node.value + "]");
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    //生成以root 为跟节点，深入为depth的描述二叉树的字符串
    private void generateBSTString(Node<T> root, int depth, StringBuilder res) {
        if (root == null) {
            res.append(generateDepthString(depth) + "null\n");
            return;
        }
        res.append(generateDepthString(depth) + root.value + "\n");
        generateBSTString(root.left, depth + 1, res);
        generateBSTString(root.right, depth + 1, res);
    }

    private String generateDepthString(int depth) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }

    //查找最小节点 一直查找左子树
    public T minimum() {
        if (size == 0)
            throw new IllegalArgumentException("BST is empty");
        return minimum(root).value;
    }

    private Node<T> minimum(Node<T> node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    //查找二分搜索树最大节点 一直查找右子树
    public T maximum() {
        if (size == 0) {
            throw new IllegalArgumentException("BST is empty");
        }
        return maximum(root).value;
    }

    private Node<T> maximum(Node<T> node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    //删除二分搜索树最小节点
    public T removeMin() {
        //找到最小值
        T minimum = minimum();
        //删除最小值
        root = removeMin(root);
        return minimum;
    }

    private Node<T> removeMin(Node<T> node) {
        //先写递归的终止条件
        //如果递归的节点的左子树为null那么就查找到最小的节点
        if (node.left == null) {
            //保存右子树
            Node<T> rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        //然后将最小的节点进行赋值
        node.left = removeMin(node.left);
        return node;
    }

    //删除二分搜索树中最大的节点
    public T removeMax() {
        T max = maximum();
        root = removeMax(root);
        return max;
    }

    private Node<T> removeMax(Node<T> node) {
        if (node.right == null) {
            Node<T> leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    //移除任意一个元素
    public void remove(T e) {
        root = remove(root, e);
    }

    private Node<T> remove(Node<T> node, T e) {
        if (node == null)
            return null;
        //查找节点
        //如果查找的节点大于当前的节点值 则查找当前节点的右子树
        if (e.compareTo(node.value) > 0) {
            node.right = remove(node.right, e);
            return node;
        } else if (e.compareTo(node.value) < 0) {//小于 查找左子树
            node.left = remove(node.left, e);
            return node;
        } else {//e == root.node
            //要删除的节点只有右子树
            if (node.left == null) {
                return moveRight((Node<T>) node);
            }

            //要删除的节点只有左子树
            if (node.right == null) {
                return moveLeft((Node<T>) node);
            }

            //左右子树都存在的情况

            //后继方式 找到该节点右子树最小的节点
//            Node<T> s = minimum(node.right);
//
//            //将s节点从删除节点的右子树中移除,然后将s节点指向右子树指向删除节点的右子树
//            s.right = removeMin(node.right);//removeMin中已经将size-1了
//            //s节点的左子树=删除节点的左子树
//            s.left = node.left;

//            //前驱方式 找到该节点左子树最大的节点
            Node<T> p = maximum(node.left);

            //先将p节点移除在做其他操作
            p.left = removeMax(node.left);

            p.right = node.right;


            //将删除节点置空
            node.right = null;
            node.left = null;
            return p;
        }
    }

    private Node<T> moveLeft(Node<T> node) {
        Node<T> leftNode = node.left;
        node.left = null;
        size--;
        return leftNode;
    }

    private Node<T> moveRight(Node<T> node) {
        Node<T> rightNode = node.right;
        node.right = null;
        size--;
        return rightNode;
    }
}
