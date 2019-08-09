package book_9787121310928;

import book_9787121310928.utility.ListNode;

import java.util.LinkedList;

/**
 * 从尾到头打印链表（链表的遍历）
 *
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_06 {

    static class Solution {
        void printList(ListNode head) {
            LinkedList<Integer> list = new LinkedList<>();
            ListNode node = head;
            while (node != null) {
                list.add(node.key);
                node = node.next;
            }
            while (!list.isEmpty()) {
                Integer key = list.pollLast();
                System.out.println(key);
            }
        }
    }


    public static void main(String[] args) {
        ListNode node4 = new ListNode(4, null);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode head = new ListNode(1, node2);
        new Solution().printList(head);
    }
}
