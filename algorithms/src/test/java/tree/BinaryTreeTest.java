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
        int[] inorder = new int[] { 'a','d','e','f','g','h','m','z' };
        int[] preorder = new int[] { 'g','d','a','f','e','m','h','z' };
        int[] postorder = new int[] { 97, 101, 102, 100, 104, 122, 109, 103 };

        BinaryTree tree = new BinaryTree(inorder, preorder, true);
        //System.out.println(tree.postorder());

        List<Integer> ans = Arrays.stream(postorder).boxed().collect(Collectors.toList());
        assertEquals(tree.postorder(), ans);
    }

}
