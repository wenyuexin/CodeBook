package book_9787121310928;

import book_9787121310928.utility.ListNode;

import java.util.Objects;

/**
 * 删除有序链表中重复的节点
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_18_2 {

    static class Solution {
        ListNode deleteDuplicateNodes(ListNode listHead) {
            ListNode node = listHead;
            while (node != null && node.next != null) {
                if (node.value == node.next.value) {
                    node.next = node.next.next;
                }
                node = node.next;
            }
            return listHead;
        }
    }


    public static void main(String[] args) {
        ListNode node6 = new ListNode(6, null);
        ListNode node5 = new ListNode(4, node6);
        ListNode node4 = new ListNode(4, node5);
        ListNode node3 = new ListNode(2, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode head = new ListNode(1, node2);
        ListNode ret =  new Solution().deleteDuplicateNodes(head);
        ret.printList();
    }
}
