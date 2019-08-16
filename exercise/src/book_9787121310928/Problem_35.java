package book_9787121310928;

import book_9787121310928.utility.ComplexListNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 复杂链表的复制：
 * 链表节点除了有value值和next节点，另外还有sibling节点，
 * 这个附加的节点可以指向链表中的任意节点或者null
 *
 * 复制普通链表比较简单，这题主要区别是多了sibling节点，
 * 这个sibling节点可能指向null或自身，这比较简单。
 *
 * 问题在于这个节点可能指向前面的某个节点，也可能指向后面的：
 * 如果指向前面，可以用Map<oldNode, newNode>来解决，
 * 此Map用于存储已经遍历过的节点，当sibling指向前面的节点时，
 * 就可以从中取出复制的新节点，并将引用赋给sibling。
 * 当Map中不存在key为oldNode的元素时，说明指向后面。
 * 如果指向后面，可以用Map<oldNode, Queue<newNodes>>来解决，其中
 * key表示还没有遍历到的节点，同时链表前方有若干节点将此节点作为sibling，
 * value就是这些前面的节点。只需在遍历到这个key时，修改Queue中节点的sibling即可
 *
 * 当然上述方法比较复杂，完全可以遍历两遍链表。
 * 第一遍和复制普通的单向链表相同，第二遍设置各个节点的sibling
 *
 * 书上提供了一种比较特别的方法（其实还是要遍历三遍），大概意思是，
 * 第一次遍历时，在原链表每个节点后面插入一个复制的新节点，
 * 第二次遍历时，从新节点借助对应的原节点找到相应的sibling节点，
 * 第三次遍历是，将两个链表拆分为原链表和新链表。
 * 书上说这种方法不需要借用辅助的空间，时间复杂度是O(3n)
 *
 * 相对而言，个人提供的方法只遍历一次，但需要维护两个Map，
 * 其中Map<oldNode, newNode>的大小，随着遍历过的节点数逐步递增，
 * 而Map<oldNode, Queue<newNodes>>的大小取决于某一时刻，
 * 最多有多少个节点指向还未遍历过的节点。（这个Map的大小有增有减）
 * 空间复杂度上，如果所有节点都将链表最后一个节点作为sibling时最差，
 * 时间复杂度则取决于Map的大小以及get、contains等方法的复杂度，
 * 这里就不分析了...
 *
 * @author Apollo4634
 * @create 2019/08/16
 */

public class Problem_35 {

    static class Solution {
        ComplexListNode copyList(ComplexListNode listHead) {
            Map<ComplexListNode, ComplexListNode> visitedMap = new HashMap<>();
            Map<ComplexListNode, Queue<ComplexListNode>> queueMap = new HashMap<>();

            ComplexListNode sentinal = new ComplexListNode(-1);
            ComplexListNode newNode = sentinal;
            ComplexListNode oldNode = listHead;
            while (oldNode != null) {
                newNode.next = new ComplexListNode(oldNode.id, oldNode.value);
                visitedMap.put(oldNode, newNode.next);
                if (oldNode.sibling == null) {
                    newNode.next.sibling = null;
                } else if (oldNode.sibling == oldNode) {
                    newNode.next.sibling = newNode.next;
                } else if (visitedMap.containsKey(oldNode.sibling)) {
                    newNode.next.sibling = visitedMap.get(oldNode.sibling);
                } else {
                    if (queueMap.containsKey(oldNode.sibling)) {
                        queueMap.get(oldNode.sibling).add(newNode.next);
                    } else {
                        Queue<ComplexListNode> queue = new LinkedList<>();
                        queue.add(newNode.next);
                        queueMap.put(oldNode.sibling, queue);
                    }
                }

                if (queueMap.containsKey(oldNode)) {
                    for (ComplexListNode node : queueMap.get(oldNode)) {
                        node.sibling = newNode.next;
                    }
                    queueMap.remove(oldNode);
                }

                oldNode = oldNode.next;
                newNode = newNode.next;
            }
            return sentinal.next;
        }
    }


    public static void main(String[] args) {
        ComplexListNode node5 = new ComplexListNode(5, 5, null, null);
        ComplexListNode node4 = new ComplexListNode(4, 4, node5, null);
        ComplexListNode node3 = new ComplexListNode(3, 3, node4, null);
        ComplexListNode node2 = new ComplexListNode(2, 2, node3, node5);
        ComplexListNode node1 = new ComplexListNode(1, 1, node2, node3);
        node4.sibling = node2;
        new Solution().copyList(node1).printList();
    }
}
