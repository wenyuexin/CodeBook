package book_9787121310928;

import book_9787121310928.utility.BinaryTreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * 找出二叉树中和为设定值的所有路径：
 * 将从根节点到叶子节点路径上的所有节点的值视为路径的值，
 * 找出所有路径值等于设定值的路径。
 * （此题禁止使用BinaryTreeNode中的父节点）
 *
 * 还是常规做法，用DFS遍历一遍树，同时设置一个链表保存搜索路径，
 * 当找到符合的路径时就把当前的路径保存下来
 *
 * @author Apollo4634
 * @create 2019/08/16
 */

public class Problem_34 {

    static class Solution {
        private double target;
        private List<List<BinaryTreeNode>> paths;

        List<List<BinaryTreeNode>> findPaths(BinaryTreeNode treeRoot, double target) {
            this.target = target;
            paths = new LinkedList<>();
            Stack<BinaryTreeNode> path = new Stack<>();
            travel(treeRoot, 0, path);
            return paths;
        }

        private void travel(BinaryTreeNode root, double sum, Stack<BinaryTreeNode> path) {
            if (root == null) return;
            path.add(root);
            sum += root.value;
            if (root.left == null && root.right == null) {
                if (sum == target) paths.add(new LinkedList<>(path));
            } else {
                travel(root.left, sum, path);
                travel(root.right, sum, path);
            }
            path.pop();
        }
    }


    public static void main(String[] args) {
        /*
         *    7
         *  5   6
         * 1 2 3 4
         */
        BinaryTreeNode n1 = new BinaryTreeNode(1, 4);
        BinaryTreeNode n2 = new BinaryTreeNode(2, 7);
        BinaryTreeNode n3 = new BinaryTreeNode(3, 0);
        BinaryTreeNode n4 = null;
        BinaryTreeNode n5 = new BinaryTreeNode(5, 5, n1, n2);
        BinaryTreeNode n6 = new BinaryTreeNode(6, 12, n3, n4);
        BinaryTreeNode tree = new BinaryTreeNode(7, 10, n5, n6);
        List<List<BinaryTreeNode>> paths = new Solution().findPaths(tree, 22);
        for (List<BinaryTreeNode> path : paths)
            System.out.println(path);
    }
}
