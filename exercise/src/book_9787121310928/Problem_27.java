package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉树的镜像
 * （每个节点存有id值和左右子节点的引用）
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_27 {

    static class Solution {
        void mirror(BinaryTreeNode treeRoot) {
            transform(treeRoot);
        }

        void transform(BinaryTreeNode root) {
            if (root == null) return;
            transform(root.left);
            transform(root.right);
            BinaryTreeNode tmp = root.right;
            root.right = root.left;
            root.left = tmp;
        }
    }


    public static void main(String[] args) {
        /*
         *    7
         *  5   6
         * 1 2 3 4
         */
        BinaryTreeNode n1 = new BinaryTreeNode(1);
        BinaryTreeNode n2 = new BinaryTreeNode(2);
        BinaryTreeNode n3 = new BinaryTreeNode(3);
        BinaryTreeNode n4 = new BinaryTreeNode(4);
        BinaryTreeNode n5 = new BinaryTreeNode(5, 0, n1, n2);
        BinaryTreeNode n6 = new BinaryTreeNode(6, 0, n3, n4);
        BinaryTreeNode tree = new BinaryTreeNode(7, 0, n5, n6);
        new Solution().mirror(tree);
    }
}
