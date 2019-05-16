package graph.union_find;

/** 
 * @author Apollo4634 
 * @create 2019/02/27
 */

public class WeightedQuickFindUF implements UnionFind {
	private int[] id;	//父链接数组，下标表示触点号
	private int count;	//连通分量的数量

	public WeightedQuickFindUF(int N) {
		count = N;
		id = new int[N];
		for (int i = 0; i < N; i++)
			id[i] = i;
	}

	public int count() {
		return count;
	}

	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}
	
	public int find(int p) {
		return id[p];
	}

	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);
		if (pId == qId) return;

		for (int i = 0; i < id.length; i++) {
			if (id[i] == pId) id[i] = qId;
		}
		count -= 1;
	}
}
