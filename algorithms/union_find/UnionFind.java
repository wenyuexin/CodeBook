package union_find;

/** 
 * @author Apollo4634 
 * @creation 2019/01/17 18:01
 */

public interface UnionFind {
	
	//p所在的分量的标识符
	int find(int p);
	
	//连通分量的数量
	int count();
	
	//在触点p和触点q之间添加连接
	void union(int p, int q);
	
	//判断触点p和触点q是否连接，若连接则返回true
	boolean connected(int p, int q);
}
