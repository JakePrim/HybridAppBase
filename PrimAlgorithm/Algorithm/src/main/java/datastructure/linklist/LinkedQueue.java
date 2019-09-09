package datastructure.linklist;

import datastructure.queue.Queue;

public class LinkedQueue<E> implements Queue<E> {

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

    private Node<E> head, tail;

    private int size = 0;

    public LinkedQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void enqueue(E e) {
        if (tail == null) {
            tail = new Node<>(e, null);
            head = tail;
        } else {
            tail.next = new Node<>(e, null);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is Empty");
        }
        Node<E> eNode = head;
        head = head.next;
        eNode.next = null;
        if (head == null) {
            tail = null;
        }
        size--;
        return eNode.e;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E getTopQueue() {
        return null;
    }
}
