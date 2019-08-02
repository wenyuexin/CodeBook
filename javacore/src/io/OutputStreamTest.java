package io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Apollo4634
 * @create 2019/06/10
 */

public class OutputStreamTest {

    private static void generateChars(OutputStream out)
        throws IOException {

        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;

        int start = firstPrintableCharacter;
        int counter = 100;
        while (counter>0) {
            for (int i = start; i < start+numberOfCharactersPerLine; i++) {
                out.write(((i-firstPrintableCharacter)%numberOfPrintableCharacters)+firstPrintableCharacter);
            }
            out.write('\r');
            out.write('\n');
            start = ((start+1)-firstPrintableCharacter)%numberOfPrintableCharacters + firstPrintableCharacter;
            counter -= 1;
        }
    }

    public static void main(String[] args) throws IOException {
        OutputStream out = new FileOutputStream("D:/test.txt");
        generateChars(out);
    }
}
