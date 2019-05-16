package diagraph;

import java.util.List;

/**
 * @author Apollo4634
 * @create 2019/03/26
 */

public class Node {
	public int id;
	public int val;
    public List<Node> neighbors;

    public Node() {}

    public Node(int val,List<Node> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }
}
