package org.rtsang.cpu;

import java.util.Stack;
public class CallStack {
    private Stack<Integer> callStack;
    public CallStack()
    {
        callStack = new Stack<Integer>();
    }
    public void push(int number)
    {
        callStack.push(number);
    }
    public int pop()
    {
        return callStack.pop();
    }
}
