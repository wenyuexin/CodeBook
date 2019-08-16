package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.List;

/**
 * 二叉搜索树与双向链表：
 * 在不创建任何节点的情况下，将二叉搜索树转换为双向链表
 *
 * 中序遍历，每次调用递归函数，都将得到这棵树对应链表的首位两个节点。
 * 依次将左子树链表、根节点、右子树链表组合成新的链表，然后返回即可。
 *
 * @author Apollo4634
 * @create 2019/08/16
 */

public class Problem_36 {

    static class Solution {
        BinaryTreeNode toDoublyLinkedList(BinaryTreeNode treeRoot) {
            if (treeRoot == null) return null;
            return travel(treeRoot)[0];
        }

        BinaryTreeNode[] travel(BinaryTreeNode root) {
            if (root == null) return null;
            BinaryTreeNode[] ret = new BinaryTreeNode[] { root,root };
            if (root.left == null && root.right == null) return ret;

            BinaryTreeNode[] leftList = travel(root.left);
            BinaryTreeNode[] rightList = travel(root.right);

            if (leftList != null) {
                leftList[1].right = root;
                root.left = leftList[1];
                ret[0] = leftList[0];
            }

            if (rightList != null) {
                rightList[0].left = root;
                root.right = rightList[0];
                ret[1] = rightList[1];
            }
            return ret;
        }
    }


    public static void main(String[] args) {
        /*
         *    7
         *  5   6
         * 1 2 3 4
         */
        BinaryTreeNode n1 = new BinaryTreeNode(1, 21);
        BinaryTreeNode n2 = new BinaryTreeNode(2, 22);
        BinaryTreeNode n3 = null;
        BinaryTreeNode n4 = new BinaryTreeNode(4, 24);
        BinaryTreeNode n5 = new BinaryTreeNode(5, 12, n1, n2);
        BinaryTreeNode n6 = new BinaryTreeNode(6, 11, n3, n4);
        BinaryTreeNode tree = new BinaryTreeNode(7, 0, n5, n6);
        BinaryTreeNode ret = new Solution().toDoublyLinkedList(tree);

        while (ret != null) {
            System.out.print(ret+" -> ");
            ret = ret.right;
        }
        System.out.println("null");
    }
}
