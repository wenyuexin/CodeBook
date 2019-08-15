package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 从上到下打印二叉树
 * 从根节点开始，按照从上往下、从左到右的顺序逐层打印各个节点
 *
 * 可以参考 {@link book_9787121310928.Problem_28}
 * 这题明显可以用BFS
 *
 * @author Apollo4634
 * @create 2019/08/15
 */

public class Problem_32_1 {

    static class Solution {
        void print(BinaryTreeNode treeRoot) {
            if (treeRoot == null) {
                System.out.println("null"); return;
            }
            Queue<BinaryTreeNode> queue = new LinkedList<>();
            queue.add(treeRoot);
            while (!queue.isEmpty()) {
                BinaryTreeNode node = queue.poll();
                System.out.print(node + " ");
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        /*
         *    7
         *  5   6
         * 1 2 3 4
         */
        BinaryTreeNode n1 = new BinaryTreeNode(1, 32);
        BinaryTreeNode n2 = new BinaryTreeNode(2, 32);
        BinaryTreeNode n3 = new BinaryTreeNode(3, 32);
        BinaryTreeNode n4 = new BinaryTreeNode(4, 32);
        BinaryTreeNode n5 = new BinaryTreeNode(5, 32, n1, n2);
        BinaryTreeNode n6 = new BinaryTreeNode(6, 32, n3, n4);
        BinaryTreeNode tree = new BinaryTreeNode(7, 32, n5, n6);
        new Solution().print(tree);
    }
}
