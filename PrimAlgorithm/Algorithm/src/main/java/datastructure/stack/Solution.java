package datastructure.stack;


import java.util.Stack;

public class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //将左括号添加到栈中 ()[]{}
            if (c == '(' || c == '[' || c == '{')
                stack.push(c);
            else {
                //如果栈中没有都没有返回false
                if (stack.isEmpty()) return false;
                Character top = stack.pop();
                if (c == ')' && top != '(') {
                    return false;
                }
                if (c == ']' && top != '[') {
                    return false;
                }
                if (c == '}' && top != '{') {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}
