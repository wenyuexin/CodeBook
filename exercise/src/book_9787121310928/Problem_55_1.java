package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉树的深度：
 * 二叉树的深度：
 * 输入一颗二叉树的根节点，求该树的深度。
 * 从根节点到叶节点依次经过的结点形成一条路径，最长路径的长度为树的深度
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_55_1 {
    public static class Solution {
        private int maxDepth;

        int TreeDepth(BinaryTreeNode root) {
            if (root == null) return 0;
            maxDepth = 0;
            dfs(root, 1);
            return maxDepth;
        }

        private void dfs(BinaryTreeNode root, int idx) {
            if (root == null) {
                if (idx-1 > maxDepth) maxDepth = idx-1;
                return;
            }
            dfs(root.left, idx+1);
            dfs(root.right, idx+1);
        }
    }


    public static void main(String[] args) {
        BinaryTreeNode node7 = new BinaryTreeNode(7);
        BinaryTreeNode node6 = new BinaryTreeNode(6);
        BinaryTreeNode node5 = new BinaryTreeNode(5, 0, node7, null);
        BinaryTreeNode node4 = new BinaryTreeNode(4);
        BinaryTreeNode node3 = new BinaryTreeNode(3, 0, null, node6);
        BinaryTreeNode node2 = new BinaryTreeNode(2, 0, node4, node5);
        BinaryTreeNode node1 = new BinaryTreeNode(1, 0, node2, node3);
        int ret = new Solution().TreeDepth(node1);
        System.out.println(ret);
    }
}
