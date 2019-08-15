package book_9787121310928;

/**
 * 栈的压入和弹出序列
 *
 * 栈符合FILO。当一组元素全部压入后再全部弹出时，入栈、出栈顺序就互为镜像。
 * 因此，需要考虑有元素压入后直接弹出的情况，
 * 假设连续压入了 [1,2,3,...,n-1,n]，此时直接弹出元素n，
 * 那元素n就是出栈序列中的第1个元素，若继续弹出元素n-1，则n-1是第2个出栈元素。
 * 综上所述，可以正向遍历入栈元素，并反向对比出栈序列中的元素。
 * 如果相等说明该元素入栈后，栈内的哪些元素是连续出栈的，
 * 如果不相等则说明该元素出栈后，栈内的元素没有全部弹出，有新的元素元素压入。
 *
 * @author Apollo4634
 * @create 2019/08/15
 */

public class Problem_31 {

    static class Solution {
        boolean isStackSequence(int[] inSeq, int[] outSeq) {
            if (inSeq == null && outSeq == null) return true;
            if (inSeq == null ^ outSeq == null) return false;
            if (inSeq.length != outSeq.length) return false;
            int left = 0;
            int right = outSeq.length - 1;
            for (int num : inSeq) {
                if (num == outSeq[right])
                    right -= 1;
                else if (num == outSeq[left]) {
                    left += 1;
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    public static void main(String[] args) {
        int[] inSeq = new int[] { 1,2,3,4,5 };
        //int[] outSeq = new int[] { 4,3,5,1,2 }; //false
        int[] outSeq = new int[] { 4,5,3,2,1 }; //true
        //int[] outSeq = new int[] { 2,3,5,4,1 }; //true
        System.out.println(new Solution().isStackSequence(inSeq, outSeq));
    }
}
