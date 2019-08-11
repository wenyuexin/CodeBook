package diagraph;



/**
 * @author Apollo4634
 * @create 2019/03/26
 */

public class DirectedEdge {
	
	Node from;
	Node to;
	double weight;
	
	DirectedEdge() {
		this(null, null, Double.POSITIVE_INFINITY);
	}
	
	DirectedEdge(Node from, Node to) {
		this(from, to, Double.POSITIVE_INFINITY);
	}
	
	DirectedEdge(Node from, Node to, double weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
	
	public Node from() {
		return from;
	}
	
	public Node to() {
		return to;
	}

	public double weight() {
		return weight;
	}
}
