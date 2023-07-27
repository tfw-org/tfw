package tfw.immutable.ilm.longilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class LongIlmFromArrayTest {
    @Test
    void testLongIlmFromArray() throws Exception {
        final Random random = new Random(0);
        long[] array = new long[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextLong();
        }

        LongIlm longIlm = LongIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, LongIlmUtil.toArray(longIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
