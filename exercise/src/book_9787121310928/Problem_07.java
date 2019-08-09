package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.Arrays;
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
        BinaryTreeNode buildTree(int[] inorder, int[] preorder) {
            Objects.requireNonNull(inorder);
            Objects.requireNonNull(preorder);
            return build(inorder, 0, preorder, 0, inorder.length);
        }

        BinaryTreeNode build(int[] inorder, int idx, int[] preorder, int from, int to) {
            if (from > to)
                System.out.println("from "+from+"  to "+to);
            BinaryTreeNode root = new BinaryTreeNode(inorder[idx]);
            int pos = Arrays.binarySearch(preorder, from, to, inorder[idx]);
            if (pos >= 0) {
                root.left = build(inorder, pos, preorder, from, pos-1);
                root.right = build(inorder, 0, preorder, from, pos-1);
            }


            return root;
        }
    }
}
