package book_9787121310928.utility;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class ListNode {
    public int value;
    public ListNode next;

    public ListNode(int value) {
        this(value, null);
    }

    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public void printList() {
        ListNode node = this;
        while (node != null) {
            System.out.print(node.toString() + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }

    public static void printList(ListNode root) {
        ListNode node = root;
        while (node != null) {
            System.out.print(node.toString() + " -> ");
            node = node.next;
        }
        System.out.println("null");
    }
}
