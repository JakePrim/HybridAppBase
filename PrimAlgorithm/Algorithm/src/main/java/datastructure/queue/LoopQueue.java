package datastructure.queue;

/**
 * 循环队列
 *
 * @param <E>
 */
public class LoopQueue<E> implements Queue<E> {
    private E[] array;

    private int size;

    //标记队头
    private int front;
    //标记队尾
    private int tail;

    public LoopQueue(int capacity) {
        array = (E[]) new Object[capacity + 1];//容器的大小要多申请一个空间 因为循环队列需要有一个额外的空间占用 否则无法判断队列是否满了
        size = 0;
        front = 0;
        tail = 0;
    }

    public LoopQueue() {
        this(10);
    }

    public int getCapacity() {
        return array.length - 1;
    }

    @Override
    public void enqueue(E e) {
        //首先判断队列容器是否满了？如果tail的下一个等于front则表示队列已经满了
        if ((tail + 1) % array.length == front) {
            //进行扩容操作
            resize(getCapacity() * 2);
        }
        array[tail] = e;
        tail = (tail + 1) % array.length;
        size++;
    }

    /**
     * 对数组进行扩容和缩容
     *
     * @param capacity 大小
     */
    private void resize(int capacity) {
        E[] newData = (E[]) new Object[capacity + 1];//同样我们需要预留一个空间来判断队列是否满了

        for (int i = 0; i < size; i++) {
            newData[i] = array[(i + front) % array.length];
        }
        //优化循环 每次循环
//        for (int i = front, j = 0; i != tail; i = (i + 1) % array.length, j++) {
//            newData[j] = array[i];
//        }
        array = newData;
        front = 0;
        tail = size;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("LoopQueue is Empty!");
        }
        E res = array[front];
        array[front] = null;
        front = (front + 1) % array.length;
        size--;
        if (size == getCapacity() / 4 && getCapacity() / 2 != 0) {
            //缩容
            resize(getCapacity() / 2);
        }
        return res;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public int size() {
        return array.length - 1;//注意要减掉占用的一个空间的大小
    }

    @Override
    public E getTopQueue() {
        return array[front];//查看队头数据
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("LoopQueue:size:%d,capacity:%d\n", size, getCapacity()));
        builder.append("front:");
        for (int i = front; i != tail; i = (i + 1) % array.length) {
            builder.append(array[i]);
            if ((i + 1) % array.length != tail)
                builder.append(", ");
        }
//        for (int i = 0; i < array.length; i++) {
//            builder.append(array[i]);
//            if (i<array.length-1)
//                builder.append(", ");
//        }
        builder.append(" tail");
        return builder.toString();
    }

    public static void main(String[] args) {
        LoopQueue<Integer> queue = new LoopQueue<Integer>(5);
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
            System.out.println(queue);

            if (i % 3 == 2) {
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
