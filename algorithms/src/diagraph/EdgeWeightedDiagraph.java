package diagraph;

import java.util.List;

/** 
 * @author Apollo4634 
 * @create 2019/03/26
 */

public class EdgeWeightedDiagraph {

	private int E;
	private int V;
	List<DirectedEdge>[] adj;
			
	EdgeWeightedDiagraph(int[][] prerequisites, double[] weight) {
		
	}
	
	public int E() {
		return E;
	}
	
	public int V() {
		return V;
	}
	 
	List<DirectedEdge> adj(Node v) {
		return adj[v.val];
	}
}
