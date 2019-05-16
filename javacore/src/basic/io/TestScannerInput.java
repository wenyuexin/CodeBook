package basic.io;

import java.util.LinkedList;
import java.util.Scanner;

public class TestScannerInput {
	public static void main(String[] args) {
		
		LinkedList<String> input = new LinkedList<>();
		if(args.length == 0) {
			@SuppressWarnings("resource")
			Scanner reader = new Scanner(System.in);
			for(int i=0; i<5; i++) {
				input.add(reader.nextLine());
			}
		}

		System.out.println(input);
		
	}
}
