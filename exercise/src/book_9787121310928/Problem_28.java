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
                Deque<BinaryTreeNode> tempDeque = currentDeque;
                currentDeque = nextLayerDeque;
                nextLayerDeque = tempDeque;
            }
            return true;
        }
    }


    public static void main(String[] args) {
        /*          15
         *    13         14
         *  09   10    11   12
         * 1 2  3 4   5 6  7 8
         */
        BinaryTreeNode n01 = new BinaryTreeNode(1, 4);
        BinaryTreeNode n02 = new BinaryTreeNode(2, 5);
        BinaryTreeNode n03 = new BinaryTreeNode(3, 6);
        BinaryTreeNode n04 = new BinaryTreeNode(4, 7);
        BinaryTreeNode n05 = new BinaryTreeNode(5, 7);
        BinaryTreeNode n06 = new BinaryTreeNode(6, 6);
        BinaryTreeNode n07 = new BinaryTreeNode(7, 5);
        BinaryTreeNode n08 = new BinaryTreeNode(8, 4);
        BinaryTreeNode n09 = new BinaryTreeNode(9, 3, n01, n02);
        BinaryTreeNode n10 = new BinaryTreeNode(10, 33, n03, n04);
        BinaryTreeNode n11 = new BinaryTreeNode(11, 33, n05, n06);
        BinaryTreeNode n12 = new BinaryTreeNode(12, 3, n07, n08);
        BinaryTreeNode n13 = new BinaryTreeNode(13, 2, n09, n10);
        BinaryTreeNode n14 = new BinaryTreeNode(14, 2, n11, n12);
        BinaryTreeNode n15 = new BinaryTreeNode(15, 28, n13, n14);
        System.out.println(new Solution().isSymmetric(n15));
    }
}
