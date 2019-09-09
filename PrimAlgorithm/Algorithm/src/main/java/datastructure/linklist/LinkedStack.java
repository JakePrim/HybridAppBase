package datastructure.linklist;

import datastructure.stack.Stack;

public class LinkedStack<E> implements Stack<E> {

    private LinkedList<E> linkedList;

    public LinkedStack() {
        linkedList = new LinkedList<>();
    }

    @Override
    public void push(E e) {
        linkedList.addFirst(e);
    }

    @Override
    public E pop() {
        return linkedList.removeFirst();
    }

    @Override
    public E peek() {
        return linkedList.getFirst();
    }

    @Override
    public int size() {
        return linkedList.size();
    }

    @Override
    public boolean isEmpty() {
        return linkedList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Stack:top");
        builder.append(linkedList);
        return builder.toString();
    }

    public static void main(String[] args) {
        LinkedStack<Integer> linkedStack = new LinkedStack<>();
        for (int i = 0; i < 5; i++) {
            linkedStack.push(i);
            System.out.println(linkedStack);
        }
        linkedStack.pop();
        System.out.println(linkedStack);
    }
}
