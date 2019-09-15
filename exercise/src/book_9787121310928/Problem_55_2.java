package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉树的深度：
 * 平衡二叉树：
 * 输入一颗二叉树的根节点，判断该树是不是平衡二叉树
 * 如果某二叉树中任意节点的左右子树深度相差不超过1，那么它就是一棵平衡二叉树。
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_55_2 {
    public static class Solution {
        boolean isBalanced(BinaryTreeNode root) {
            if (root == null) return true;
            return dfs(root) != -1;
        }

        private int dfs(BinaryTreeNode root) {
            if (root == null) return 0;
            int depth1 = dfs(root.left);
            if (depth1 == -1) return -1;
            int depth2 = dfs(root.right);
            if (depth2 == -1) return -1;
            if (depth1 != 0 && depth2 != 0 && Math.abs(depth1-depth2) != 1) return -1;
            return Math.max(depth1, depth2) + 1;
        }

        private boolean isLeafNode(BinaryTreeNode node) {
            return (node == null) || (node.left == null && node.right == null);
        }
    }


    public static void main(String[] args) {
        BinaryTreeNode node8 = new BinaryTreeNode(8, 0);
        BinaryTreeNode node7 = new BinaryTreeNode(7, 0, node8, null);
        BinaryTreeNode node6 = new BinaryTreeNode(6);
        BinaryTreeNode node5 = new BinaryTreeNode(5, 0, node7, null);
        BinaryTreeNode node4 = new BinaryTreeNode(4);
        BinaryTreeNode node3 = new BinaryTreeNode(3, 0, null, node6);
        BinaryTreeNode node2 = new BinaryTreeNode(2, 0, node4, node5);
        BinaryTreeNode node1 = new BinaryTreeNode(1, 0, node2, node3);
        boolean ret = new Solution().isBalanced(node1);
        System.out.println(ret);
    }
}
