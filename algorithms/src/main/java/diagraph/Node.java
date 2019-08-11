package diagraph;

import java.util.List;

/**
 * @author Apollo4634
 * @create 2019/03/26
 */

public class Node implements Comparable<Node> {
	public int id;
	public double val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int id, double val, List<Node> neighbors) {
        this.id = id;
        this.val = val;
        this.neighbors = neighbors;
    }

    @Override
    public int compareTo(Node o) {
        return (int) (this.val - o.val);
    }
}
