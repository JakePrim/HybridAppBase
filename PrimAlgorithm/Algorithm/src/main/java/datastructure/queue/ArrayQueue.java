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

    /**
     * 每次移除的是数组的第一个，会导致所有数据的移动 性能低效
     */
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
        StringBuilder res = new StringBuilder();
        res.append("Queue: \n");
        res.append("top: ");
        for (int i = 0; i < size(); i++) {
            res.append(array.get(i));
            if (i < size() - 1) {
                res.append(", ");
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
//        ArrayQueue<Integer> arrayQueue = new ArrayQueue<Integer>();
//        for (int i = 0; i < 6; i++) {
//            arrayQueue.enqueue(i);
//        }
//        System.out.println(arrayQueue);
//        arrayQueue.dequeue();
//        System.out.println(arrayQueue);
//        arrayQueue.enqueue(7);
//        System.out.println(arrayQueue);
//        arrayQueue.dequeue();
//        System.out.println(arrayQueue);
        System.out.println("求余："+1%2);
    }
}
