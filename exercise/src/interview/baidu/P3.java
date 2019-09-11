package interview.baidu;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 小度给定你一棵拥有n个点的树，每次删去当前所有的叶子节点(即度数
 * 小于等于1的节点)和叶子节点所连接的边，直到所有的点都被删除了为
 * 止。你需要对于每个点，求出它是第几次操作中被删除的。
 *
 * @author Apollo4634
 * @create 2019/09/10
 */
public class P3 {
    private static class Node {
        int id;
        int parent;
        Set<Integer> children;

        Node(int id) {
            this.id = id;
            children = new HashSet<>();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        Node[] nodes = new Node[n+1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node(i);
        }
        
        for (int i = 0; i < n-1; i++) {
            int parent = sc.nextInt();
            int child = sc.nextInt();
            nodes[parent].children.add(child);
            nodes[child].parent = parent;
        }

        Set<Integer> leaves = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            if (nodes[i].children.size() == 0) {
                leaves.add(i);
            }
        }

        int count = 1;
        int[] ans = new int[n+1];
        Set<Integer> parents;
        while (!leaves.isEmpty()) {
            if (leaves.size() == 1) {
                System.out.println();
            }
            parents = new HashSet<>();
            for (Integer leaf : leaves) {
                if (!nodes[leaf].children.isEmpty()) continue;
                parents.add(nodes[leaf].parent);
                nodes[nodes[leaf].parent].children.remove(nodes[leaf]);
                ans[leaf] = count;
            }
            leaves = parents;
            count += 1;
        }

        for (int i = 1; i <= n; i++) {
            System.out.print(ans[i]);
        }
        System.out.println();
    }
}
