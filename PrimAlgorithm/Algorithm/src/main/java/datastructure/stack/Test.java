package datastructure.stack;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
//        ArrayStack<Integer> stack = new ArrayStack<>();
//        stack.push(1);
//        stack.push(2);
//        stack.push(3);
//        stack.push(4);
//        stack.push(5);
//        System.out.println(stack);
//        stack.pop();
//        System.out.println(stack);
//
//        String res = "()[]{}";
//        Solution solution = new Solution();
//        System.out.println(solution.isValid(res));

        //["MinStack","push","push","push","top","pop","getMin","pop","getMin","pop","push","top","getMin","push","top","getMin","pop","getMin"]
        //[[],[2147483646],[2147483646],[2147483647],[],[],[],[],[],[],[2147483647],[],[],[-2147483648],[],[],[],[]]

        MinStack minStack = new MinStack();
        minStack.push(2147483646);
        minStack.push(2147483647);
        minStack.push(-2147483648);
        System.out.println("top:" + minStack.top());
        System.out.println("min:" + minStack.getMin());
        minStack.pop();
        System.out.println("top:" + minStack.top());
        System.out.println("min:" + minStack.getMin());
    }
}
