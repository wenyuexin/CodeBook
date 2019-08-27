package tree.util;

/**
 * @author Apollo4634
 * @create 2019/08/27
 */


public class RedBlackTreeNode implements TreeNode {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public int id;
    public double value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    int N;
    boolean color;


    public RedBlackTreeNode(int id) {
        this(id, null, null, 0);
    }

    public RedBlackTreeNode(int id, BinaryTreeNode left, BinaryTreeNode right) {
        this(id, left, right, 0);
    }

    public RedBlackTreeNode(int id, BinaryTreeNode left, BinaryTreeNode right, double value) {
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
