package book_9787121310928;

import book_9787121310928.utility.ListNode;

import java.util.Objects;

/**
 * 删除链表的节点
 * 给定单向链表的头和一个节点，在O(1)时间内删除该节点
 *
 * 从这个O(1)以及题目下面说不能用常规方法做，大概就知道这个题的意思了。
 * 主要是java不能像c++那样用指针，如果要删除的节点是最后一个点，
 * 那就必须要从头遍历，然后把前一个节点的next设为null
 *
 * @author Apollo4634
 * @create 2019/08/12
 */

public class Problem_18_1 {

    static class Solution {
        ListNode deleteNode(ListNode listHead, ListNode nodeToBeDelete) {
            Objects.requireNonNull(listHead);
            Objects.requireNonNull(nodeToBeDelete);
            if (listHead == nodeToBeDelete)
                return listHead.next;
            if (nodeToBeDelete.next != null) {
                ListNode nextNode = nodeToBeDelete.next;
                nodeToBeDelete.value = nextNode.value;
                nodeToBeDelete.next = nextNode.next;
            } else {
                ListNode node = listHead;
                while (node.next != nodeToBeDelete) {
                    node = node.next;
                }
                node.next = null;
            }
            return listHead;
        }
    }


    public static void main(String[] args) {
        ListNode node6 = new ListNode(5, null);
        ListNode node5 = new ListNode(5, node6);
        ListNode node4 = new ListNode(4, null);
        ListNode node3 = new ListNode(3, node4);
        ListNode node2 = new ListNode(2, node3);
        ListNode head = new ListNode(1, node2);
        ListNode ret = new Solution().deleteNode(head, head);
    }
}
