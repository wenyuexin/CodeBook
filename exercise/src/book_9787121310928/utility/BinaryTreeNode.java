package book_9787121310928.utility;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    public BinaryTreeNode parent;

    public BinaryTreeNode(int value) {
        this(value, null, null, null);
    }

    public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right) {
        this(value, left, right, null);
    }

    public BinaryTreeNode(int value, BinaryTreeNode left, BinaryTreeNode right, BinaryTreeNode parent) {
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }
}
