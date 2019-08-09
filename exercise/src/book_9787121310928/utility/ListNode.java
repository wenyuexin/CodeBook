package book_9787121310928.utility;

/**
 * @author Apollo4634
 * @create 2019/08/09
 */

public class ListNode {
    public int key;
    public ListNode next;

    public ListNode(int key) {
        this.key = key;
    }

    public ListNode(int key, ListNode next) {
        this.key = key;
        this.next = next;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
    }
}
