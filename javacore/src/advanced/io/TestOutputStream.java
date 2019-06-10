package advanced.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Apollo4634
 * @create 2019/06/10
 */

public class TestOutputStream {

    public static void generateChars(OutputStream out)
        throws IOException {

        int firstPrintableCharacter = 33;
        int numberOfPrintableCharacters = 94;
        int numberOfCharactersPerLine = 72;

        int start = firstPrintableCharacter;
        while (true) {
            for (int i = start; i < start+numberOfCharactersPerLine; i++) {
                out.write(((i-firstPrintableCharacter)%numberOfPrintableCharacters)+firstPrintableCharacter);
            }
            out.write('\r');
            out.write('\n');
            start = ((start+1)-firstPrintableCharacter)%numberOfPrintableCharacters + firstPrintableCharacter;
        }
    }
}
