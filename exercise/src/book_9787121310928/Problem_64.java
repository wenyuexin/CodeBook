package book_9787121310928;

import java.util.Random;

/**
 * 不使用乘法、除法、for、while、if else、switch case、？：运算符
 * 计算1+2+...+n
 *
 * 够无聊的题，硬要说的话大体上有两个思路：
 * 一是按某种顺序以及组合方式，将1至n这些数加起来；
 * 二是使用 n*(n+1)/2 这个公式
 *
 * 对于第一种思路，由于需要将多个数字相加，但是又不能用循环，
 * 因此可以借助递归或者其他方法实现循环的效果。
 * 如果是递归，关那么键问题就是如何不使用if语句等方法结束递归
 *
 * 对于第二种思路，问题在于如果替换乘法和除法，除法可以用无符号右移，
 * 那么关键就是替换乘法
 *
 * 书上的方法感觉都不太好，对于java来说利用构造函数计算的方法还凑合
 *
 * @author Apollo4634
 * @create 2019/08/15
 */

public class Problem_64 {

//    static class Solution {
//        int calcSum(int n) {
//            int sum = 0;
//            return calc();
//        }
//    }

    //居然说这题是考察发散思维，这就很发散（手动滑稽）
    static class Solution2 {
        int calcSum(int n) {
            return ((int) Math.pow(n, 2) + n) >>> 1;
        }
    }

    public static void main(String[] args) {
        int n = new Random().nextInt(10000);
        System.out.println((n*n+n)/2);
        System.out.println(new Solution2().calcSum(n));
    }
}
