package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 对称的二叉树：判断二叉树是否对称
 *
 * 由于{@link book_9787121310928.Problem_27}的影响，
 * 看到这题第一反应是先获取镜像，再判断两颗树是否相等
 * 所以这里不打算用这种方法。。。
 *
 * 这里使用的是双端队列加BFS的方法，队列用于保存某一层的所有节点。
 * 如果某一层的节点是对称的，那么每次从队列的头和尾各取出一个节点，
 * 这两个节点的value就应该是相等的。
 * 然后再把这两个节点的子节点按特定顺序加入到下一层的双端队列。
 * 以此类推，逐行判断，直到遍历完二叉树的所有层。
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_28 {

    static class Solution {
        boolean isSymmetric(BinaryTreeNode treeRoot) {
            if (treeRoot == null) return true;
            Deque<BinaryTreeNode> currentDeque = new LinkedList<>();
            Deque<BinaryTreeNode> nextLayerDeque = new LinkedList<>();
            currentDeque.add(treeRoot.left);
            currentDeque.add(treeRoot.right);
            while (!currentDeque.isEmpty()) {
                while (!currentDeque.isEmpty()) {
                    BinaryTreeNode firstNode = currentDeque.pollFirst();
                    BinaryTreeNode lastNode = currentDeque.pollLast();
                    if (firstNode == null && lastNode == null) continue;
                    if (firstNode == null ^ lastNode == null) return false;
                    if (firstNode.value != lastNode.value) return false;
                    nextLayerDeque.addFirst(firstNode.right);
                    nextLayerDeque.addFirst(firstNode.left);
                    nextLayerDeque.addLast(lastNode.left);
                    nextLayerDeque.addLast(lastNode.right);
                }
                currentDeque = nextLayerDeque;
            }
            return true;
        }
    }


    public static void main(String[] args) {
        /*
         *    7
         *  5   6
         * 1 2 3 4
         */
        BinaryTreeNode n1 = null;
        BinaryTreeNode n2 = new BinaryTreeNode(2, 3);
        BinaryTreeNode n3 = new BinaryTreeNode(3, 3);
        BinaryTreeNode n4 = null;
        BinaryTreeNode n5 = new BinaryTreeNode(5, 2, n1, n2);
        BinaryTreeNode n6 = new BinaryTreeNode(6, 2, n3, n4);
        BinaryTreeNode tree = new BinaryTreeNode(7, 28, n5, n6);
        System.out.println(new Solution().isSymmetric(tree));
    }
}
