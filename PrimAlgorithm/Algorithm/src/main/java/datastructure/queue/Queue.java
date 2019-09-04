package datastructure.queue;

public interface Queue<E> {
    void enqueue(E e);

    E dequeue();

    boolean isEmpty();

    int size();

    E getTopQueue();
}
