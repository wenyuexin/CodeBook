package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.Objects;

/**
 * 重建二叉树：
 * 根据二叉树的前序遍历和中序遍历结果构建二叉树
 *
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_07 {

    static class Solution {
        private int index = 0;

        BinaryTreeNode buildTree(int[] inorder, int[] preorder) {
            Objects.requireNonNull(inorder);
            Objects.requireNonNull(preorder);
            return build(inorder, preorder, 0, inorder.length-1);
        }

        private BinaryTreeNode build(int[] inorder, int[] preorder, int from, int to) {
            if (from > to) return null;
            int value = preorder[index++];
            BinaryTreeNode root = new BinaryTreeNode(value);

            int pos = -1;
            for (int i = from; i <= to; i++) {
                if (inorder[i] == value) pos = i;
            }
            if (pos < 0) {
                throw new RuntimeException("Invalid inorder or preorder");
            }

            root.left = build(inorder, preorder, from, pos-1);
            root.right = build(inorder, preorder, pos+1, to);
            return root;
        }
    }


    public static void main(String[] args) {
        int[] preorder = new int[] { 1,2,4,7,3,5,6,8 };
        int[] inorder = new int[] { 4,7,2,1,5,3,8,6 };
        BinaryTreeNode treeNode = new Solution().buildTree(inorder, preorder);
    }
}
