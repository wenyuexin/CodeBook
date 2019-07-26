package union_find;

/**
 * @author Apollo4634
 * @create 2019/07/26
 */

public class QuickFind extends AbstractUnionFind {
    public QuickFind(int N) {
        super(N);
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q) {
        if (id[p] == id[q]) return;
        for (int i = 0; i < count; i++) {
            if (id[i] == id[q]) id[i] = id[p];
        }
        count -= 1;
    }
}
