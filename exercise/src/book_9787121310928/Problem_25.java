package book_9787121310928;

import book_9787121310928.utility.ListNode;

/**
 * 合并两个有序的单向链表，使得返回的链表仍然有序
 *
 * 参考leetcode 第21题 Merge Two Sorted Lists
 * 建议尝试第23题 Merge k Sorted Lists
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_25 {

    static class Solution {
        ListNode merge(ListNode listHead1, ListNode listHead2) {
            if (listHead1 == null) return listHead2;
            if (listHead2 == null) return listHead1;
            ListNode node1 = listHead1;
            ListNode node2 = listHead2;
            ListNode sentinel = new ListNode(0);

            ListNode prev = sentinel;
            ListNode node;
            while (node1 != null || node2 != null) {
                if (node1 == null) {
                    node = new ListNode(node2.value);
                    node2 = node2.next;
                } else if (node2 == null) {
                    node = new ListNode(node1.value);
                    node1 = node1.next;
                } else {
                    if (node1.value > node2.value) {
                        node = new ListNode(node2.value);
                        node2 = node2.next;
                    } else {
                        node = new ListNode(node1.value);
                        node1 = node1.next;
                    }
                }
                prev.next = node;
                prev = node;
            }
            return sentinel.next;
        }
    }


    public static void main(String[] args) {
        ListNode node17 = new ListNode(13, null);
        ListNode node16 = new ListNode(11, node17);
        ListNode node15 = new ListNode(9, node16);
        ListNode node14 = new ListNode(7, node15);
        ListNode node13 = new ListNode(5, node14);
        ListNode node12 = new ListNode(4, node13);
        ListNode head1 = new ListNode(1, node12);

        ListNode node26 = new ListNode(12, null);
        ListNode node25 = new ListNode(10, node26);
        ListNode node24 = new ListNode(8, node25);
        ListNode node23 = new ListNode(4, node24);
        ListNode node22 = new ListNode(4, node23);
        ListNode head2 = new ListNode(2, node22);

        ListNode ret = new Solution().merge(head1, head2);
        ListNode.printList(ret);
    }
}
