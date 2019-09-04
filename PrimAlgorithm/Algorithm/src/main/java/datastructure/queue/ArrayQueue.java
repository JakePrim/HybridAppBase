package datastructure.queue;

import datastructure.array.Array;

/**
 * 动态队列结构
 *
 * @param <E>
 */
public class ArrayQueue<E> implements Queue<E> {
    private Array<E> array;

    public ArrayQueue(int capacity) {
        array = new Array<>(capacity);
    }

    public ArrayQueue() {
        this(10);
    }

    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) return null;
        return array.removeFirst();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public int size() {
        return array.getSize();
    }

    @Override
    public E getTopQueue() {
        return array.get(0);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
