package book_9787121310928;

import book_9787121310928.utility.ListNode;

/**
 * 两个链表的第一个公共节点：
 * 输入两个链表，找出它们的第一个公共结点。
 * 注：此处为单向链表
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_52 {

    static class Solution {
        ListNode FindFirstCommonNode(ListNode head1, ListNode head2) {
            if (head1 == null || head2 == null) return null;
            int len1 = getListLength(head1);
            int len2 = getListLength(head2);
            int deltaLen = Math.abs(len1 - len2);
            ListNode node1, node2;
            if (len1 > len2) {
                node1 = head1; node2 = head2;
            } else {
                node1 = head2; node2 = head1;
            }
            for (int i = 0; i < deltaLen; i++) {
                node1 = node1.next;
            }

            while (node1 != node2) {
                node1 = node1.next;
                node2 = node2.next;
            }
            return node1;
        }

        private int getListLength(ListNode head) {
            int len = 0;
            ListNode node = head;
            while (node != null) {
                len += 1;
                node = node.next;
            }
            return len;
        }
    }


    public static void main(String[] args) {
        ListNode node7 = new ListNode(7);
        ListNode node6 = new ListNode(6, node7);
        ListNode node5 = new ListNode(6, node6);
        ListNode node4 = new ListNode(6, node5);
        ListNode node3 = new ListNode(6, node6);
        ListNode node2 = new ListNode(6, node3);
        ListNode node1 = new ListNode(6, node2);
        ListNode commonNode = new Solution().FindFirstCommonNode(node1, node4);
        System.out.println(commonNode.toString());
    }
}
