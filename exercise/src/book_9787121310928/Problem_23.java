package book_9787121310928;

import book_9787121310928.utility.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 单向链表中环的入口节点
 * 如果节点A可以到达节点B，同时节点B直接指向节点A，那么A就是入口节点
 *
 * 这个和检测是否存在有向环的方法类似，不过这个题简单很多
 * 如果事先知道总共有多少个节点，可以用一个数组保存是否遍历过的状态。
 * 这里并不知道，因此可以用一个set保存已经遍历过的节点，
 * 当发现指向的节点已经遍历过时，就说明链表存在环，而这个节点就是入口点
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_23 {

    static class Solution {
        ListNode findEntrance(ListNode listHead) {
            if (listHead == null) return null;
            Set<ListNode> visited = new HashSet<>();
            for (ListNode node = listHead; node != null; node = node.next) {
                if (visited.contains(node)) return node;
                else                        visited.add(node);
            }
            return null;
        }
    }


    public static void main(String[] args) {
        ListNode node7 = new ListNode(237, null);
        ListNode node6 = new ListNode(236, node7);
        ListNode node5 = new ListNode(235, node6);
        ListNode node4 = new ListNode(234, node5);
        ListNode node3 = new ListNode(233, node4);
        ListNode node2 = new ListNode(232, node3);
        ListNode head = new ListNode(231, node2);
        node6.next = node2;
        ListNode ret = new Solution().findEntrance(head);
        System.out.println(ret);
    }
}
