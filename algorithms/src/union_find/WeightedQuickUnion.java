package union_find;

/** 
 * @author Apollo4634 
 * @create 2019/01/17
 */

public class WeightedQuickUnion extends AbstractUnionFind {
	private int[] sz;	//各个根节点对应的分量大小

	public WeightedQuickUnion(int N) {
		super(N);
		sz = new int[N];
		for (int i = 0; i < N; i++) sz[i] = 1;
	}

	@Override
	public int find(int p) {		
		while (p != id[p]) p = id[p];
		return p;
	}

	@Override
	public void union(int p, int q) {
		int pId = find(p);
		int qId = find(q);
		if(pId == qId)	return;

		if (sz[pId] < sz[qId])	{ id[pId] = qId; sz[qId] += sz[pId]; }  
		else				    { id[qId] = pId; sz[pId] += sz[qId]; }
		count -= 1;
	}	
}

