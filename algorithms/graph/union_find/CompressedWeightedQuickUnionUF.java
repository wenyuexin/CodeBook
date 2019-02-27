package graph.union_find;

/** 
 * 在WeightedQuickUnionUF基础上进行改进，即
 * 在find()中添加一个循环，将路径上所有的节点直接连接到根节点即可
 * 
 * @author Apollo4634 
 * @creation 2019/01/17
 */

public class CompressedWeightedQuickUnionUF implements UnionFind {
	private int[] id;	//父链接数组，下标表示触点号
	private int[] sz;	//各个根节点对应的分量大小
	private int count;	//连通分量的数量

	public CompressedWeightedQuickUnionUF(int N) {
		count = N;

		id = new int[N];
		for(int i = 0; i < N; i++) id[i] = i;

		sz = new int[N];
		for(int i = 0; i < N; i++) sz[i] = 1;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public int find(int p) {
		int pCopy = p;
		while (p != id[p]) p = id[p];

		int pRoot = p;
		p = pCopy;
		while (p != id[p]) {
			pCopy = p;
			p = id[p];
			id[pCopy] = pRoot;
		}
		return p;
	}

	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);
		if(pId == qId)	return;

		if (sz[pId] < sz[qId]) { 
			id[pId] = qId; sz[qId] += sz[pId]; 
		} else {
			id[qId] = pId; sz[pId] += sz[qId]; 
		}
		count -= 1;
	}
}
