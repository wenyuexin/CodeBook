package book_9787121310928;

import java.util.Arrays;

/**
 * 二叉搜索树的后序遍历序列
 * 判断一个元素互异的整数数组是否为某个二叉搜索树的后序遍历结果
 *
 * @author Apollo4634
 * @create 2019/08/15
 */

public class Problem_33 {

    static class Solution {
        boolean isPostorderSequence(int[] seq) {
            if (seq == null || seq.length < 3) return true;
            int[] inorder = new int[seq.length];
            System.arraycopy(seq, 0, inorder, 0, seq.length);
            Arrays.sort(inorder);
            return check(seq, seq.length-1, inorder,0, seq.length);
        }

        private boolean check(int[] seq, int rootIdx, int[] inorder, int left, int right) {
            if (left >= right) return true;
            int index = Arrays.binarySearch(inorder, left, right, seq[rootIdx]);
            if (index < 0) return false;
            if (!check(seq, rootIdx-1, inorder, index+1, right)) return false;
            return check(seq, rootIdx-(right-index), inorder, left, index);
        }
    }


    public static void main(String[] args) {
        //int[] sequence = new int[] { 7,6,9 }; //true
        int[] sequence = new int[] { 7,4,6,5 }; //false
        //int[] sequence = new int[] { 7,6,9,11,10,8 }; //true
        System.out.println(new Solution().isPostorderSequence(sequence));
    }
}
