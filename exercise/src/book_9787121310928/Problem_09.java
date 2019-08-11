package book_9787121310928;

import java.util.List;
import java.util.Stack;

/**
 * 用两个栈实现一个队列，队列需要包含appendTail和deleteHead两个方法
 *
 * 栈只能取出栈顶元素，因此如果要想队列一样得到最先入栈的元素，
 * 就需要用另一个栈模拟入队，另一个栈模拟出队，
 * 而模拟出队的栈显然要和另一个栈的元素存放顺序相反。
 * 同时为了协调两个栈，还需要设置计数器，用于确定已经出队但仍在inStack中的元素个数
 *
 * @author Apollo4634
 * @create 2019/08/11
 */

public class Problem_09 {
    static class MyQueue<E> {
        int outNum;
        Stack<E> inStack;
        Stack<E> outStack;

        public void enqueue(E e) {
            inStack.add(e);
        }

        public E dequeue() {
            if (outStack.empty()) {
                int n = inStack.size() - outNum;
                while (n > 0) {
                    outStack.add(inStack.pop());
                    n -= 1;
                }
                outNum = 0;
            }
            outNum += 1;
            return outStack.pop();
        }
    }
}
