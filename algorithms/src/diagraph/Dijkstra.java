package diagraph;

import java.util.PriorityQueue;

/**
 * 未完成
 *
 * @author Apollo4634 
 * @create 2019/03/26
 */

public class Dijkstra {
	private Node[] edgeTo;
	private boolean[] marked;
	private PriorityQueue<Node> pq;
	
	Dijkstra(EdgeWeightedDiagraph G, Node s) {
		edgeTo = new Node[G.V()];
		marked = new boolean[G.V()];
		pq = new PriorityQueue<>(G.V());

		//修改最短路径的路径总权重
		for (int i = 0; i < G.V(); i++) {
			marked[i] = false;
		}

		s.val = 0;
		pq.add(s);
		while (!pq.isEmpty()) {
			relax(G, s);
		}
	}
	
	
	//顶点的松弛
	private void relax(EdgeWeightedDiagraph G, Node v) {
		for(DirectedEdge e: G.adj(v)) {
			Node w = e.to();
			if (!marked[w.id]) {
				marked[w.id] = true;
				w.val = Double.POSITIVE_INFINITY;
			}
			if (w.val > v.val + e.weight()) {
				w.val = v.val + e.weight();
				edgeTo[w.id] = v;
				if (!pq.contains(w)) pq.add(w);
			}
		}
	}
}
