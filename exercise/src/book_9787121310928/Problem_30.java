package book_9787121310928;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含min函数的栈
 * 定义栈的数据结构，实现在O(1)复杂度内调用min、push、pop函数
 *
 * 栈就是一种FILO的数据结构，不论是用数组还是链表实现，
 * 都只能只在栈顶压入或弹出元素，并且很容易实现O(1)复杂度，
 * 但是要在O(1)的时间内实现min的调用，只靠数组或者链表比较难，
 * 初步的想法是想最大堆、最小堆类似的方式，按特定的规则存放数据。
 * 然而很难保证三个函数都是O(1)复杂度的要求。
 *
 * 算了不废话，简单说就是使用另一个辅助的变量来做，例如两个数组。
 * 一个数组模拟栈，保证入栈和出栈的复杂度为O(1)，
 * 另一个记录，对应元素为栈顶元素时的最小值，保证min的复杂度符合要求
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_30 {

    static class Solution {
        static class MyStack<E extends Comparable<? super E>> {
            List<E> elements;
            List<Integer> minIndex;
            int top;

            MyStack() {
                this.elements = new ArrayList<>();
                this.minIndex = new ArrayList<>();
                this.top = -1;
            }

            E min() {
                if (top < 0) return null;
                else         return elements.get(minIndex.get(top));
            }

            void push(E e) {
                elements.set(++top, e);
                minIndex.set(top, top);
                if (top > 0 && e.compareTo(elements.get(minIndex.get(top-1))) >= 0)
                    minIndex.set(top, minIndex.get(top-1));
            }

            E pop() {
                if (top < 0) return null;
                return elements.get(top--);
            }
        }
    }
}
