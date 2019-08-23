package tree;

import tree.util.BinaryTreeNode;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class BinaryTree {

    public enum Type {
        PREORDER,
        INORDER,
        POSTORDER
    }

    private int N;
    private BinaryTreeNode treeRoot;
    private int[] inorder;
    private int[] preorder;
    private int[] postorder;

    BinaryTree(int[] inorder, int[] preorder) {
        this(inorder, preorder, true);
    }

    BinaryTree(int[] inorder, int[] preOrPost, boolean isPreorder) {
        Objects.requireNonNull(inorder);
        Objects.requireNonNull(preOrPost);
        if (inorder.length != preOrPost.length)
            throw new IllegalArgumentException("The length of the two arrays is different");

        this.inorder = inorder;
        this.N = inorder.length;
        if (isPreorder) {
            this.preorder = preOrPost;
            preorderBuildingIndex = 0;
            this.treeRoot = buildWithPreorder(0, N-1);
        }
        else {
            this.postorder = preOrPost;
            postorderBuildingIndex = N-1;
            this.treeRoot = buildWithPostorder(0, N-1);
        }
    }

    /**
     * 由前序或后序之一以及中序构建二叉树
     */

    // pre-order
    private int preorderBuildingIndex;

    private BinaryTreeNode buildWithPreorder(int from, int to) {
        if (from > to) return null;
        int value = preorder[preorderBuildingIndex++];
        BinaryTreeNode root = new BinaryTreeNode(value);

        int pos = -1;
        for (int i = from; i <= to; i++) {
            if (inorder[i] == value) pos = i;
        }
        if (pos < 0) {
            throw new RuntimeException("Invalid inorder or preorder");
        }

        root.left = buildWithPreorder(from, pos-1);
        root.right = buildWithPreorder(pos+1, to);
        return root;
    }

    // post-order
    private int postorderBuildingIndex;

    private BinaryTreeNode buildWithPostorder(int from, int to) {
        if (from > to) return null;
        int value = postorder[postorderBuildingIndex--];
        BinaryTreeNode root = new BinaryTreeNode(value);

        int pos = -1;
        for (int i = from; i <= to; i++) {
            if (inorder[i] == value) pos = i;
        }
        if (pos < 0) {
            throw new RuntimeException("Invalid inorder or postorder");
        }

        root.right = buildWithPostorder(pos+1, to);
        root.left = buildWithPostorder(from, pos-1);
        return root;
    }


    /**
     * 获取三种顺序的遍历轨迹
     */

    // in-order
    public List<Integer> inorder() {
        return Arrays.stream(inorder).boxed().collect(Collectors.toList());
    }


    // pre-order
    private int preorderTrackingCount;

    public List<Integer> preorder() {
        if (preorder == null) {
            preorder = new int[N];
            preorderTrackingCount = 0;
            preorderTracking(treeRoot);
        }
        return Arrays.stream(preorder).boxed().collect(Collectors.toList());
    }

    private void preorderTracking(BinaryTreeNode node) {
        if (node == null) return;
        preorder[preorderTrackingCount++] = node.id;
        preorderTracking(node.left);
        preorderTracking(node.right);
    }

    // post-order
    private int postorderTrackingCount;

    public List<Integer> postorder() {
        if (postorder == null) {
            postorder = new int[N];
            postorderTrackingCount = 0;
            postorderTracking(treeRoot);
        }
        return Arrays.stream(postorder).boxed().collect(Collectors.toList());
    }

    private void postorderTracking(BinaryTreeNode node) {
        if (node == null) return;
        postorderTracking(node.left);
        postorderTracking(node.right);
        postorder[postorderTrackingCount++] = node.id;
    }


    public static void main(String[] args) {
        int[] inorder = new int[] { 'a','d','e','f','g','h','m','z' };
        int[] preorder = new int[] { 'g','d','a','f','e','m','h','z' };
        //int[] postorder = new int[] { 7,4,2,5,8,6,3,1 };

        BinaryTree tree = new BinaryTree(inorder, preorder, true);
        System.out.println(tree.postorder());

        for (Integer integer : tree.postorder()) {
            System.out.print((char) integer.byteValue() +" ");
        }

//        BinaryTree tree2 = new BinaryTree(inorder, postorder, false);
//        System.out.println(tree2.preorder());
    }
}
