package tfw.immutable.ilm.charilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class CharIlmFromArrayTest {
    @Test
    void testCharIlmFromArray() throws Exception {
        final Random random = new Random(0);
        char[] array = new char[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (char) random.nextInt();
        }

        CharIlm charIlm = CharIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, CharIlmUtil.toArray(charIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
