package interview.bytedance;

import java.util.Scanner;

/**
 * 豆油
 *
 * 平台上有很多用户。若两个用户互动次数不少于3，则认为两个人互为豆油。
 * 豆油具有传递性，如果AB是豆油，BC是豆油，那么AC也互为豆油。
 * 假设豆油瓶是所有直接或间接具有豆油关系的用户的集合。
 * 用N*M的矩阵M存储互动次数，M[i][j]是用户i和用户j之间的互动次数。
 * 并设定自己和自己之间的互动次数为0。求豆油瓶的个数。
 *
 * 可以使用并查集解题
 *
 * @author Apollo4634
 * @create 2019/08/25
 */

public class BeanOil {

    private static class UnionFind {
        int[] id;
        int[] sz;

        UnionFind(int N) {
            id = new int[N];
            sz = new int[N];
            for (int i = 0; i < N; i++) {
                id[i] = i;
                sz[i] = 1;
            }
        }

        int find(int p) {
            while (p != id[p])
                p = id[p];
            return p;
        }

        void union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);
            if (sz[pRoot] < sz[qRoot]) {
                id[pRoot] = qRoot;
                sz[qRoot] += sz[pRoot];
            } else {
                id[qRoot] = pRoot;
                sz[pRoot] += sz[qRoot];
            }
        }

        int maxComponentSize() {
            int max = sz[0];
            for (int n : sz) {
                if (n > max) max = n;
            }
            return max;
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[][] matrix = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = sc.nextInt();
            }
        }

        UnionFind uf = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (matrix[i][j] >= 3) uf.union(i, j);
            }
        }

        System.out.println(uf.maxComponentSize());
    }
}
