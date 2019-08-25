package demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Apollo4634
 * @create 2019/08/25
 */

public class TextFileIO {
    public static void main(String[] args) {
        //Generate data
        int numsLen = 20;
        int randomNumberOrigin = -100;
        int randomNumberBound = 100;
        int[] nums = new Random().ints(numsLen, randomNumberOrigin, randomNumberBound).toArray();
        System.out.println(Arrays.toString(nums));

        //Write file
        try (FileWriter fileWriter = new FileWriter("./data_TextFileIO.txt")) {
            for (int num : nums) {
                fileWriter.write(num + "\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Read file
        List<Integer> list = new LinkedList<>();
        try (FileReader fileReader = new FileReader("./data_TextFileIO.txt");
             BufferedReader reader = new BufferedReader(fileReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(Integer.parseInt(line));
            }
            System.out.println(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
