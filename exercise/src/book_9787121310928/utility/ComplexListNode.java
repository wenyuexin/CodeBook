package book_9787121310928.utility;

/**
 * @author Apollo4634
 * @create 2019/08/16
 */

public class ComplexListNode {
    public int id;
    public double value;
    public ComplexListNode next;
    public ComplexListNode sibling;


    public ComplexListNode(int id) {
        this(id, 0, null, null);
    }

    public ComplexListNode(double value) {
        this(0, value, null, null);
    }

    public ComplexListNode(int id, double value) {
        this(id, value, null, null);
    }

    public ComplexListNode(int id, double value, ComplexListNode next, ComplexListNode sibling) {
        this.id = id;
        this.value = value;
        this.next = next;
        this.sibling = sibling;
    }


    @Override
    public String toString() {
        String siblingStr = (sibling == null)? "null" : String.valueOf(sibling.id);
        return "[" + id + ", " + value + ", " + siblingStr + "]";
    }

    public void printList() {
        ComplexListNode node = this;
        while (node != null) {
            System.out.print(node.toString() + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }

    public static void printList(ComplexListNode root) {
        ComplexListNode node = root;
        while (node != null) {
            System.out.print(node.toString() + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }
}
