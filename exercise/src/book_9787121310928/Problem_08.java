package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉树的下一个节点
 *
 * 如果存在右节点，则找右子树的第一个节点（按中序遍历的顺序）
 * 如果不存在右子树，则往上找
 *   发现自己是父节点的左节点 直接返回父节点
 *   发现自己是父节点的右节点 则继续往上找
 * 如果循环到根节点时仍然没有找到，则说明已经是最后一个节点，此时返回空
 * @author Apollo4634
 * @create 2019/08/09
 */

public class Problem_08 {
    static class Solution {
        BinaryTreeNode findNext(BinaryTreeNode treeRoot, BinaryTreeNode node) {
            if (node.right != null) {
                return searchTheFirstNode(node.right);
            } else {
                BinaryTreeNode parent;
                BinaryTreeNode tmp = node;
                while (tmp.parent != null) {
                    parent = node.parent;
                    if (tmp == parent.left) return parent;
                    tmp = parent;
                }
            }
            return null;
        }

        BinaryTreeNode searchTheFirstNode(BinaryTreeNode root) {
            if (root == null) return null;
            BinaryTreeNode node = searchTheFirstNode(root.left);
            if (node != null) return node;
            return root;
        }
    }
}
