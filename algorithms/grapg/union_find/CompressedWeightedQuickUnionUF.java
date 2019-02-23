package grapg.union_find;

/** 
 * @author Apollo4634 
 * @creation 2019/01/17 19:01
 */

public class CompressedWeightedQuickUnionUF implements UnionFind {
	private int[] id;	//父链接数组，下标表示触点号
	private int[] sz;	//各个根节点对应的分量大小
	private int count;	//连通分量的数量

	public CompressedWeightedQuickUnionUF(int N) {
		count = N;

		id = new int[N];
		for(int i = 0; i < N; i++)
			id[i] = i;

		sz = new int[N];
		for(int i = 0; i < N; i++)
			sz[i] = 1;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	public int find(int p) {
		int pCopy = p;
		while(p != id[p]) p = id[p];

		int pRoot = p;
		p = pCopy;
		while(p != id[p]) {
			pCopy = p;
			p = id[p];
			id[pCopy] = pRoot;
		}
		return p;
	}

	public void union(int p, int q) {
		int i = find(p);
		int j = find(q);
		if(i == j)	return;

		if(sz[i] < sz[j]) { 
			id[i] = j; sz[j] += sz[i]; 
		} else {
			id[j] = i; sz[i] += sz[j]; 
		}

		count--;
	}
}
