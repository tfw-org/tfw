package tfw.immutable.ila.charila;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import junit.framework.Assert;
import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class CharIlaFromUtf8ByteIlaTest extends TestCase {
    public void testCharIlaFromUTF8ByteIla() throws Exception {
        // Create all unicode characters, valid and invalid.
        final int unicodeSpace = 1 << 21;
        final char[] characters = new char[unicodeSpace];

        for (int i = 0; i < characters.length; i++) {
            characters[i] = (char) i;
        }

        // Create UTF-8 bytes from full unicode character space.
        final String string = new String(characters);
        final byte[] utf8Bytes = string.getBytes(StandardCharsets.UTF_8);

        // Create characters from UTF-8 bytes using java.lang.String.
        final String stringFromUtf8Bytes = new String(utf8Bytes, StandardCharsets.UTF_8);
        final char[] charactersFromString = stringFromUtf8Bytes.toCharArray();

        // Create characters from UTF-8 bytes using CharIlaFromUTF8Bytes.
        final ByteIla utf8ByteIla = ByteIlaFromArray.create(utf8Bytes);
        final CharIla charIlaFromUtf8ByteIla = CharIlaFromUtf8ByteIla.create(utf8ByteIla, 0, 0);
        final int charIlaLength = (int) charIlaFromUtf8ByteIla.length();
        final char[] charactersFromCharIla = new char[charIlaLength];

        charIlaFromUtf8ByteIla.toArray(charactersFromCharIla, 0, 0, charIlaLength);

        // Compare the two arrays.
        Assert.assertTrue(Arrays.equals(charactersFromString, charactersFromCharIla));
    }
}
