package tree.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class BinaryTreeNode {
    public int id;
    public double value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    public BinaryTreeNode(int id) {
        this(id, null, null, 0);
    }

    public BinaryTreeNode(int id, BinaryTreeNode left, BinaryTreeNode right) {
        this(id, left, right, 0);
    }

    public BinaryTreeNode(int id, BinaryTreeNode left, BinaryTreeNode right, double value) {
        this.id = id;
        this.left = left;
        this.right = right;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("[%d, %f]", id, value);
    }
}
