package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

/**
 * 二叉树的子结构
 * 判断二叉树B是不是二叉树A的子结构
 * （假定节点没有指向父节点的成员变量，只存有value值和左右子节点）
 *
 * 这里使用的是两层中序遍历。
 * 第一层是遍历二叉树A的各个节点a，
 * 判断以节点a为根的子树Aa是否包含二叉树B，若包含则直接返回true。
 * 第二层是以节点a为根判断是否包含树B时使用，
 * 当如果不包含，则直接返回false。
 *
 * 需要注意的是
 * 对于第二层遍历，需要以树B为基准，当递归发现树B的某个节点为null时，
 * 说明至少在这个分支上，二叉树Aa和二叉树B是相同的。
 *
 * @author Apollo4634
 * @create 2019/08/14
 */

public class Problem_26 {

    static class Solution {
        boolean contains(BinaryTreeNode treeRoot, BinaryTreeNode subTreeRoot) {
            return visit(treeRoot, subTreeRoot);
        }

        boolean visit(BinaryTreeNode treeRoot, BinaryTreeNode subTreeRoot) {
            if (treeRoot == null) return false;
            if (compare(treeRoot, subTreeRoot)) return true;
            if (compare(treeRoot.left, subTreeRoot)) return true;
            return compare(treeRoot.right, subTreeRoot);
        }

        boolean compare(BinaryTreeNode treeRoot, BinaryTreeNode subTreeRoot) {
            if (subTreeRoot == null) return true;
            if (treeRoot == null) return false;
            if (subTreeRoot.value != treeRoot.value) return false;
            if (!compare(treeRoot.left, subTreeRoot.left)) return false;
            return compare(treeRoot.right, subTreeRoot.right);
        }
    }


    public static void main(String[] args) {
        BinaryTreeNode n11 = new BinaryTreeNode(11, 4);
        BinaryTreeNode n12 = new BinaryTreeNode(12, 7);
        BinaryTreeNode n13 = new BinaryTreeNode(13, 9);
        BinaryTreeNode n14 = new BinaryTreeNode(14, 2, n11, n12);
        BinaryTreeNode n15 = new BinaryTreeNode(15, 8, n13, n14);
        BinaryTreeNode n16 = new BinaryTreeNode(16, 7);
        BinaryTreeNode tree1 = new BinaryTreeNode(17, 8, n15, n16);

        BinaryTreeNode n21 = new BinaryTreeNode(21, 9);
        BinaryTreeNode n22 = new BinaryTreeNode(22, 2);
        BinaryTreeNode tree2 = new BinaryTreeNode(23, 8, n21, n22);

        System.out.println(new Solution().contains(tree1, tree2));
    }
}
