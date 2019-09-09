package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 无向图的割边（连通桥）问题
 *
 * @author Apollo4634
 * @create 2019/09/09
 */

public class CutEdges {
    private static int index;
    private static List<String> list;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(); // 顶点数
        int E = sc.nextInt(); // 边数

        int[][] edges = new int[V + 1][V + 1];
        int[] num = new int[V + 1]; // 存储第一遍dfs遍历的时间戳
        int[] low = new int[V + 1]; // 存储最小时间戳的数组

        int v1, v2;
        for (int i = 1; i <= E; i++) {
            v1 = sc.nextInt();
            v2 = sc.nextInt();
            edges[v1][v2] = 1;
            edges[v2][v1] = 1;
        }

        int root = 1;
        list = new LinkedList<>();
        dfs(1, root, low, num, V, edges);

        System.out.println(list);
    }

    private static void dfs(int child, int father, int[] low, int[] num, int n, int[][] edges) {
        int i, j;
        index++;
        num[child] = index;
        low[child] = index;
        for (i = 1; i <= n; i++) {
            if (edges[child][i] == 1) {
                if (num[i] == 0) {
                    dfs(i, child, low, num, n, edges);
                    low[child] = Math.min(low[i], low[child]);

                    // 表示不经过父节点，该点就不能达到祖先（包括父节点）那两点组成的边即割边
                    if (low[i] > num[child]) {
                        //System.out.println(child + " - " + i);
                        list.add("["+child + "," + i+"]");
                    }
                } else if (i != father) {
                    low[child] = Math.min(low[child], num[i]);
                }
            }
        }
    }
}
