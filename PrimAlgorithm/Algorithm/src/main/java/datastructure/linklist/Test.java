package datastructure.linklist;

import datastructure.queue.ArrayQueue;
import datastructure.queue.LoopQueue;
import datastructure.queue.Queue;
import datastructure.stack.ArrayStack;
import datastructure.stack.Stack;

import java.util.Random;

public class Test {

    // 测试使用q运行opCount个enqueueu和dequeue操作所需要的时间，单位：秒
    private static double testQueue(Stack<Integer> q, int opCount) {

        long startTime = System.nanoTime();

        Random random = new Random();
        for (int i = 0; i < opCount; i++)
            q.push(random.nextInt(Integer.MAX_VALUE));
        for (int i = 0; i < opCount; i++)
            q.pop();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        int opCount = 100000;

        LinkedStack<Integer> linkedStack = new LinkedStack<>();//0.008 链表栈比数组栈性能要高一些，因为数组栈要就行扩容，而链表要不停的new操作，都
        //存在性能问题 在复杂度上没有巨大的差异

        ArrayStack<Integer> arrayStack = new ArrayStack<>();//0.020



        double time2 = testQueue(linkedStack, opCount);

        double time1 = testQueue(arrayStack, opCount);

        System.out.println("ArrayStack, time: " + time1 + " s");
        System.out.println("LinkedStack, time: " + time2 + " s");
    }
}
