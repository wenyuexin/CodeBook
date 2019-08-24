package interview.acmcoder;

import java.util.Scanner;

/**
 * 有股神吗？ 有，小赛就是！
 *
 * 经过严密的计算，小赛买了一支股票，他知道从他买股票的那天开始，股票会有以下变化：
 * 第一天不变，以后涨一天，跌一天，涨两天，跌一天，涨三天，跌一天...依此类推。
 *
 * 为方便计算，假设每次涨和跌皆为1，股票初始单价也为1，请计算买股票的第n天每股股票值多少钱？
 *
 * @author Apollo4634
 * @create 2019/08/22
 */

public class StockGod {

    static class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            String str;
            int day = sc.nextInt();
            day -= 1;

            int n = (int) ((Math.sqrt(9+8*day)-3)/2.0);
            int value = (n*(n-1))/2;
            value += (day - n*(n+3)/2);
            System.out.println(value + 1);
        }
    }
}
