package book_9787121310928;

import book_9787121310928.utility.ListNode;

/**
 * 打印单向链表倒数第k个节点
 *
 * 主要区别是只遍历一遍还是两遍
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_22 {

    static class Solution {
        ListNode findNode(ListNode listHead, int k) {
            if (listHead == null || k < 1) return null;
            int count = 0;
            ListNode kNode = listHead;
            for (ListNode node = listHead; node != null; node = node.next) {
                if (count < k)  count += 1;
                else            kNode = kNode.next;
            }
            return count == k ? kNode : null;
        }
    }


    public static void main(String[] args) {
        ListNode node7 = new ListNode(7, null);
        ListNode node6 = new ListNode(6, node7);
        ListNode node5 = new ListNode(5, node6);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode head = new ListNode(1, node2);
        ListNode ret = new Solution().findNode(head, 3);
        System.out.println(ret);
    }
}
