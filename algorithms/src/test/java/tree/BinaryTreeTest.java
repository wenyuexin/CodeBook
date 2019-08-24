package tree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author Apollo4634
 * @create 2019/08/23
 */

public class BinaryTreeTest {

    @Test
    public void testPostorder() {
        int[] inorder = new int[] { 8,4,2,5,1,6,3,7 };
        int[] preorder = new int[] { 1,2,4,8,5,3,6,7 };

        BinaryTree tree = new BinaryTree(inorder, preorder, true);
        System.out.println(tree.postorder());

        //List<Integer> ans = Arrays.stream(postorder).boxed().collect(Collectors.toList());
        //assertEquals(tree.postorder(), ans);
    }

    @Test
    public void testPostorder2() {
        int[] inorder = new int[] { 'a','d','e','f','g','h','m','z' };
        int[] preorder = new int[] { 'g','d','a','f','e','m','h','z' };

        BinaryTree tree = new BinaryTree(inorder, preorder, true);
        List<Integer> list = tree.postorder();
        for (Integer integer : list) {
            System.out.print((char) integer.byteValue() + " ");
        }
        System.out.println();
    }


    @Test
    public void testPreorder() {
        int[] inorder = new int[] { 8,4,2,5,1,6,3,7 };
        int[] postorder = new int[] { 1,2,4,8,5,3,6,7 };

        BinaryTree tree = new BinaryTree(inorder, postorder, false);
        System.out.println(tree.preorder());
    }

    @Test
    public void testPreorder2() {
        int[] inorder = new int[] { 'a','d','e','f','g','h','m','z' };
        int[] postorder = new int[] { 'g','d','a','f','e','m','h','z' };

        BinaryTree tree = new BinaryTree(inorder, postorder, false);
        List<Integer> list = tree.preorder();
        for (Integer integer : list) {
            System.out.print((char) integer.byteValue() + " ");
        }
        System.out.println();
    }
}
