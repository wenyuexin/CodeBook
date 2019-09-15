package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉搜索树的第k大节点：
 * 给定一颗二叉搜索树，请找出其中第k大的节点。
 * 例如，在下图中安节点数值大小顺序，第三大节点的值是4.
 *    5
 *  3   7
 * 2 4 6 8
 *
 * @author Apollo4634
 * @create 2019/09/15
 */

public class Problem_54 {
    public static class Solution {
        private BinaryTreeNode kNode;

        BinaryTreeNode KthNode(BinaryTreeNode root, int k) {
            kNode = null;
            if (k > 0) track(root, k);
            return kNode;
        }

        private int track(BinaryTreeNode root, int k) {
            if (root == null) return 0;
            int size = track(root.left, k);
            if (++size == k) {
                kNode = root; return size;
            }
            if (size >= k) return size;
            size += track(root.right, k-size);
            return size;
        }
    }


    public static void main(String[] args) {
        BinaryTreeNode node2 = new BinaryTreeNode(2);
        BinaryTreeNode node4 = new BinaryTreeNode(4);
        BinaryTreeNode node6 = new BinaryTreeNode(6);
        BinaryTreeNode node8 = new BinaryTreeNode(8);
        BinaryTreeNode node3 = new BinaryTreeNode(3, 0, node2, node4);
        BinaryTreeNode node7 = new BinaryTreeNode(7, 0, node6, node8);
        BinaryTreeNode node5 = new BinaryTreeNode(5, 0, node3, node7);
        System.out.println(new Solution().KthNode(node5, 0));
        System.out.println(new Solution().KthNode(node5, 1));
        System.out.println(new Solution().KthNode(node5, 3));
        System.out.println(new Solution().KthNode(node5, 7));
        System.out.println(new Solution().KthNode(node5, 8));
    }
}
