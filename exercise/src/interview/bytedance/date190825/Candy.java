package interview.bytedance.date190825;


import java.util.Scanner;

/**
 * 糖果
 *
 * 有一些糖果，每个糖果有一个甜度。
 * 若两个糖果的甜度的最大公约数大于1，则认为两个糖果存在连接。
 * 初始条件下可以选择一个糖果，然后得到与之存在连接的糖果，以此类推。
 * 求最多能获得多少个糖果。
 *
 * 可以用并查集来做，目标是求得最大联通分量中的节点数
 *
 * @author Apollo4634
 * @create 2019/08/25
 */

public class Candy {

    static class UnionFind {
        int[] id;
        private int[] sz;

        UnionFind(int N) {
            id = new int[N];
            for (int i = 0; i < N; i++) id[i] = i;
            sz = new int[N];
            for (int i = 0; i < N; i++) sz[i] = 1;
        }

        int find(int p) {
            while (p != id[p]) p = id[p];
            return p;
        }

        void union(int p, int q) {
            int pId = find(p);
            int qId = find(q);
            if (pId == qId) return;
            if (sz[pId] < sz[qId]) {
                id[pId] = qId;
                sz[qId] += sz[pId];
            } else {
                id[qId] = pId;
                sz[pId] += sz[qId];
            }
        }

        int maxSize() {
            int max = sz[0];
            for (int n : sz) {
                if (n > max) max = n;
            }
            return max;
        }
    }

    private static boolean greatestCommonDivisorIsBiggerThanOne(int a, int b) {
        while(b != 0) {
            int r= a%b;
            a = b;
            b = r;
        }
        return a > 1;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = sc.nextInt();
        }

        UnionFind uf = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (greatestCommonDivisorIsBiggerThanOne(nums[i], nums[j])) {
                    uf.union(i, j);
                }
            }
        }
        System.out.println(uf.maxSize());
    }
}
