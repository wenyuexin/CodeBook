package book_9787121310928;

import book_9787121310928.utility.ListNode;

/**
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_24 {

    static class Solution {
        ListNode reverseList(ListNode listHead) {
            if (listHead == null || listHead.next == null) return listHead;
            ListNode prev = null;
            ListNode node = listHead;
            ListNode next;
            for (; node != null; prev = node, node = next) {
                next = node.next;
                node.next = prev;
            }

            return prev;
        }
    }


    public static void main(String[] args) {
        //new Solution().reverseList(null);
        //new Solution().reverseList(new ListNode(0)).printList();

        ListNode node7 = new ListNode(247, null);
        ListNode node6 = new ListNode(246, node7);
        ListNode node5 = new ListNode(245, node6);
        ListNode node4 = new ListNode(244, node5);
        ListNode node3 = new ListNode(243, node4);
        ListNode node2 = new ListNode(242, node3);
        ListNode head = new ListNode(241, node2);
        ListNode ret = new Solution().reverseList(head);
        ret.printList();
    }
}
