package control_flow;

/**
 * 注意如果switch语句中不加break，会执行对应case之后的所有语句
 *
 * @author Apollo4634
 * @create 2019/09/04
 */

public class SwitchTest {
    public static void main(String[] args) {
        System.out.println(switchInt(4));
    }

    private static int switchInt(int x) {
        int j = 1;
        switch (x) {
            case 1: j++;
            case 2: j++;
            case 3: j++;
            case 4: j++; //j=1+1
            case 5: j++; //j=2+1
            default: j++; //j=3+1
        }
        return j+x; //4+4
    }
}
