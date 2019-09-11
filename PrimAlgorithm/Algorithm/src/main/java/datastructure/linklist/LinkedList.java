package datastructure.linklist;

/**
 * 链表
 * 添加头部和删除头部时间复杂度为O(1)
 * @param <E>
 */
public class LinkedList<E> {

    //节点
    private static class Node<E> {

        public E e;

        public Node<E> next;

        public Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }

        public Node(Node<E> next) {
            this(null, next);
        }

        public Node(E e) {
            this(e, null);
        }
    }

    //标记链表的头部
    private Node<E> head;

    //标记链表的尾部 可以降低尾部的时间复杂度


    private int size;

    public LinkedList() {
        this.head = new Node<>(null, null);
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 添加头节点
     * O(1)
     * @param e
     */
    public void addFirst(E e) {
        add(e, 0);
    }

    public void add(E e, int index) {
        if (index < 0 || index > size)
            throw new IllegalArgumentException("index > 0 && index < size");
        Node<E> perv = head;
        for (int i = 0; i < index; i++) {
            perv = perv.next;
        }
        perv.next = new Node<E>(e, perv.next);
        size++;
    }

    /**
     * 添加尾节点
     * O(n)
     * @param e
     */
    public void addLast(E e) {
        add(e, size);
    }

    //获取某个节点
    public E get(int index) {
        if (index < 0 && index >= size) {
            throw new IllegalArgumentException("Get Error,Illegal index");
        }
        Node<E> node = head.next;//由于head是虚拟节点 获取下一个节点为头节点
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    public void set(int index, E e) {
        if (index < 0 && index >= size) {
            throw new IllegalArgumentException("Set Error,Illegal index");
        }
        Node<E> node = head.next;//由于head是虚拟节点 获取下一个节点为头节点
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.e = e;
    }

    public boolean contains(E e) {
        Node<E> node = head.next;
        while (node != null) {
            if (node.e.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 && index >= size) {
            throw new IllegalArgumentException("Remove Error,Illegal index");
        }
        Node<E> prev = head;//从0开始查找
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }
        Node<E> dNode = prev.next;
        prev.next = dNode.next;
        dNode.next = null;
        size--;
        return dNode.e;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        Node<E> node = head.next;
        while (node != null) {
            res.append(node.e + " -> ");
            node = node.next;
        }

        //等价循环
//        for (Node<E> cur = head.next;cur != null;cur = cur.next){
//            res.append(cur+" -> ");
//        }

        res.append("null");
        return res.toString();
    }

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }
        linkedList.add(266, 2);
        System.out.println(linkedList);
        linkedList.set(2,100);
        System.out.println(linkedList);
        linkedList.remove(2);
        System.out.println(linkedList);
        linkedList.removeFirst();
        System.out.println(linkedList);
        linkedList.removeLast();
        System.out.println(linkedList);
    }
}
