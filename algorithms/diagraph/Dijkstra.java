package diagraph;

import priority_queue.IndexMinPQ;

/** 
 * @author Apollo4634 
 * @create 2019/03/26
 */

public class Dijkstra {
	
	private Node[] edgeTo;
	private double[] distTo;
	private IndexMinPQ<Double> pq;
	
	Dijkstra(EdgeWeightedDiagraph G, Node s) {
		edgeTo = new Node[G.V()];
		distTo = new double[G.V()];
		pq = new IndexMinPQ<Double>(G.V());
		
		for (int i = 0; i < G.V(); i++) {
			distTo[i] = Double.POSITIVE_INFINITY;
		}
		distTo[s.val] = 0;
		
		pq.insert(s.val, 0.0);
		while (!pq.isEmpty()) {
			relax(G, s);
		}
	}
	
	
	//顶点的松弛
	private void relax(EdgeWeightedDiagraph G, Node v) {
		for(DirectedEdge e: G.adj(v)) {
			Node w = e.to();
			if (distTo[w.val] > distTo[v.val] + e.weight()) {
				distTo[w.val] = distTo[v.val] + e.weight();
				edgeTo[w.val] = v;
				if (pq.contains(w.val)) pq.change(w.val, distTo[w.val]);
				else                pq.insert(w.val, distTo[w.val]);
			}
		}
	}
}
