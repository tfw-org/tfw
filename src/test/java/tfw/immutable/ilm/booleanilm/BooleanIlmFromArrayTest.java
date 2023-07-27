package tfw.immutable.ilm.booleanilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class BooleanIlmFromArrayTest {
    @Test
    void testBooleanIlmFromArray() throws Exception {
        final Random random = new Random(0);
        boolean[] array = new boolean[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextBoolean();
        }

        BooleanIlm booleanIlm = BooleanIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, BooleanIlmUtil.toArray(booleanIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
