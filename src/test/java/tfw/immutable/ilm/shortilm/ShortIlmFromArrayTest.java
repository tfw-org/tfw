package tfw.immutable.ilm.shortilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ShortIlmFromArrayTest {
    @Test
    void testShortIlmFromArray() throws Exception {
        final Random random = new Random(0);
        short[] array = new short[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (short) random.nextInt();
        }

        ShortIlm shortIlm = ShortIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, ShortIlmUtil.toArray(shortIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
