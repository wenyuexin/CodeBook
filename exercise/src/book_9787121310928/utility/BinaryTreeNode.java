package book_9787121310928.utility;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class BinaryTreeNode {
    public int id;
    public double value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;
    public BinaryTreeNode parent;

    public BinaryTreeNode(int id) {
        this(id, 0, null, null, null);
    }

    public BinaryTreeNode(double value) {
        this(0, value, null, null, null);
    }

    public BinaryTreeNode(int id, double value) {
        this(id, value, null, null, null);
    }

    public BinaryTreeNode(int id, double value, BinaryTreeNode left, BinaryTreeNode right) {
        this(id, value, left, right, null);
    }

    public BinaryTreeNode(int id, double value, BinaryTreeNode left, BinaryTreeNode right, BinaryTreeNode parent) {
        this.id = id;
        this.value = value;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }


    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
