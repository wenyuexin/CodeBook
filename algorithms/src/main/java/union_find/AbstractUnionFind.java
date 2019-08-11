package union_find;

/**
 * @author Apollo4634
 * @create 2019/07/26
 */

public abstract class AbstractUnionFind implements UnionFind {
    int[] id; //分量id
    int count; //分量数量

    protected AbstractUnionFind(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    //p所在的分量的标识符
    public abstract int find(int p);

    //连通分量的数量
    @Override
    public int count() {
        return count;
    }

    //在节点p和节点q之间添加连接
    public abstract void union(int p, int q);

    //判断节点p和节点q是否连接，若连接则返回true
    @Override
    public boolean connected(int p, int q) {
        return (find(p) == find(q));
    }
}
