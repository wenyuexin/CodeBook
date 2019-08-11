package union_find;

/**
 * @author Apollo4634
 * @create 2019/07/26
 */

public class QuickUnion extends AbstractUnionFind {
    public QuickUnion(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        while (id[p] != p) {
            p = id[p];
        }
        return p;
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot != qRoot) {
            id[qRoot] = pRoot;
            count -= 1;
        }
    }
}
