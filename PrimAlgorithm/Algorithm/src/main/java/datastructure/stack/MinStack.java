package datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MinStack {
    /**
     * initialize your data structure here.
     */
    List<Long> array;

    //assist stack records min stack
    Stack<Long> assistStack;

    private long min;

    public MinStack() {
        array = new ArrayList<>();
        assistStack = new Stack<>();
    }

    //1 2 3 -1 -2 -4
    public void push(long x) {
        array.add(x);
        //
        if (assistStack.isEmpty()) {
            assistStack.push(0L);
            min = x;
        } else {
            System.out.println("x = [" + x + "]" + ",min = [" + min + "]" + "data = [" + (x - min) + "]");
            assistStack.push(x - min);//如果x-min>0 则说明 最小的是min 如果<0
            if (x < min) {
                min = x;
            }
        }
    }

    public Long pop() {
        if (array.isEmpty()) return -1l;
        Long remove = array.remove(array.size() - 1);
        Long pop = assistStack.pop();
        if (pop < 0) {
            min = min - pop;
        }
        System.out.println("min:" + min + " pop:" + pop);
        return remove;
    }

    public Long top() {
        if (array.isEmpty()) return -1l;
        return array.get(array.size() - 1);
    }

    public Long getMin() {
        return min;
    }
}
