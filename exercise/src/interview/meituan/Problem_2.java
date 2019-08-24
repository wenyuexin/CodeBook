package interview.meituan;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * 火星文字典
 *
 * @author Apollo4634
 * @create 2019/08/22
 */

public class Problem_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] strs = line.split(" ");
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> degree = new HashMap<>();
        for (String str : strs) {
            for (char c : str.toCharArray()) {
                degree.put(c, 0);
            }
        }
        Map<Character, Set<Character>> map = new HashMap<>();
        for (int i = 0; i < strs.length - 1; i++) {
            String cur = strs[i];
            String next = strs[i + 1];
            int len = Math.min(cur.length(), next.length());
            for (int j = 0; j < len; j++) {
                char c1 = cur.charAt(j);
                char c2 = next.charAt(j);
                if (c1 != c2) {
                    Set<Character> set = new HashSet<>();
                    if (map.containsKey(c1)) set = map.get(c1);
                    if (!set.contains(c2)) {
                        set.add(c2);
                        map.put(c1, set);
                        degree.put(c2, degree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        LinkedList<Character> queue = new LinkedList<>();
        for (Map.Entry<Character, Integer> entry : degree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            char parent = queue.poll();
            sb.append(parent);
            if (map.containsKey(parent)) {
                for (char child : map.get(parent)) {
                    degree.put(child, degree.get(child) - 1);
                    if (degree.get(child) == 0) queue.offer(child);
                }
            }
        }
        System.out.println(sb.toString());
    }
}