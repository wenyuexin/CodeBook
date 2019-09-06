package interview.zhaoyin;

import java.util.Scanner;

/**
 * 某销售公司每个月都会对员工的销售业绩做等级评价，优秀为A，合格为B，较差为C。
 * 公司年底都会整理连续n个月(n<=12)的业绩，如果业绩中超过个C (较差)
 * 或者超过两个连续的B (合格)，则不会对该员工发放额外 的业绩奖金。
 * 请给出所有可奖励的情况数量。
 *
 * 输入: 2
 * 输出: 8
 * 解释[AA,AB,AC,BA,BB,BC,CA,CB]是可奖励的，[CC]是不可奖励的
 *
 * @author Apollo4634
 * @create 2019/09/06
 */

public class SalesPerformanceAssessment {
    public static class Main {
        private static int count;

        public static void main(String[] axrga) {
            Scanner sc = new Scanner(System.in);
            int n = sc.nextInt();
            count = 0;
            helper(n, 0, 0, 0);
            System.out.println(count);
        }

        private static void helper(int n, int idx, int bCount, int cCount) {
            if (idx == n) {
                count += 1; return;
            }
            helper(n, idx+1, 0, cCount); //a
            if (bCount <= 1) {
                helper(n, idx + 1, 1, cCount); //b
            }
            if (cCount == 0) {
                helper(n, idx+1, 0, 1); //c
            }
        }
    }
}
