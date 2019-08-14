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
            ListNode next = node.next;
            for (prev = node, node = next; node != null; prev = node, node = next) {
                next = node.next;
            }
            return null;
        }
    }
}
