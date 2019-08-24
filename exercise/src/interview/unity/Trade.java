package interview.unity;

import java.util.Scanner;

/**
 * 某种物品的交易价格每日都在波动，每次交易都需要一定手续费
 * 假设已经购买了一个物品，则卖出之前不得买入，
 * 求能够获得的最大利润。
 *
 * 输入：
 * 第一行，F为手续费，N为数组prices的长度
 * 第二行，记录了每日价格的数组
 *
 * 示例：
 * 2 6
 * 1 3 2 8 4 9
 *
 * 买入 prices[0]=1
 * 卖出 prices[3]=8
 * 买入 prices[4]=4
 * 卖出 prices[5]=9
 * 此时有最大利润 (8-1-2)+(9-4-2)=8
 *
 * @author Apollo4634
 * @create 2019/08/23
 */

class Trade {
    public static class Main {
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            int F = sc.nextInt();
            int N = sc.nextInt();

            int[] prices = new int[N]; //[1,3,2,8,4,9]
            for (int i = 0; i < N; i++) {
                prices[i] = sc.nextInt();
            }

            int maxProfit = 0;
            for (int i = 0; i < N-1; i++) {
                int profit = calc(prices, i, 0);
                if (profit > maxProfit) maxProfit = profit;
            }
            System.out.println(maxProfit);
        }


        private static int calc(int[] prices, int idxToBuy, int maxProfit) {
            int profit = 0;
            for (int i = idxToBuy+1; i < prices.length; i++) {
                int newProfit = maxProfit;
                int trade = prices[i] - prices[idxToBuy] - 2;
                if (trade > 0) {
                    newProfit = calc(prices, i+1, newProfit+trade);
                }
                if (newProfit > profit) profit = newProfit;
            }

            if (profit > maxProfit) maxProfit = profit;
            return maxProfit;
        }
    }
}
