package demo;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Apollo4634
 * @create 2019/08/22
 */

class MainDemo {

    static void inputDemo() {
        Scanner sc = new Scanner(System.in);

        //输入数字和int数组
        int N = sc.nextInt();
        int[] nums = new int[N];
        for (int i = 0; i < N; i++) {
            nums[i] = sc.nextInt();
        }

        //不定长的数组
        List<Integer> list = new LinkedList<>();
        while(sc.hasNext()){
            list.add(sc.nextInt());
        }

        //输入字符串
        String string = sc.nextLine().trim();

        //输入字符串数组
        String line = sc.nextLine();
        String[] strs = line.split(" ");
    }


    static void outputDemo() {
        //通用输出
        System.out.println("output");

        //输出浮点数
        System.out.printf("%2.3f", 3.5);
    }


    public static void main(String[] args) {
        inputDemo();
        outputDemo();
    }
}
