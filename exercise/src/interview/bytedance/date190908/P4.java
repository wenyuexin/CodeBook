package interview.bytedance.date190908;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 数字按特定的编码方式转字符串
 *
 * @author Apollo4634
 * @create 2019/09/08
 */

public class P4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine().trim();
        char[] digits = number.toCharArray();

        List<String> list = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        helper(digits, 0, sb, list);
    }

    private static void helper(char[] digits, int idx, StringBuilder sb, List<String> list) {
        if (idx == digits.length) {
            list.add(sb.toString());
        }

        if (digits[idx] == 0) return;
        helper(digits, idx+1, sb, list);


        if (digits[idx] > '2') {
            sb.append((char) ('B'+digits[idx]-'2'));
            helper(digits, idx+1, sb, list);
            sb.delete(sb.length()-1, sb.length());
        }
        if (digits[idx] == '2' && idx+1 < digits.length && digits[idx] > '6') {
            sb.append((char) ('B'+digits[idx]-'2'));
            helper(digits, idx+1, sb, list);
            sb.delete(sb.length()-1, sb.length());
        }

        if (idx+2 == digits.length) {
            list.add(sb.toString());
        }
        helper(digits, idx+2, sb, list);
    }
}
