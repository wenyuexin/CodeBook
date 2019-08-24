package interview.meituan;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 美团骑手包裹区间分组
 *
 * @author Apollo4634
 * @create 2019/08/22
 */

public class Problem_1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < input.length(); i++) {
            map.put(input.charAt(i), i);
        }

        int idx = 0;
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            count = Math.max(count, map.get(ch));
            if (i == count) {
                if (count == input.length()-1) {
                    System.out.println(count-idx+1); break;
                } else {
                    System.out.println(count-idx+1+"");
                    idx = count + 1;
                }
            }
        }
    }
}
