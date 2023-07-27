package tfw.immutable.ilm.floatilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class FloatIlmFromArrayTest {
    @Test
    void testFloatIlmFromArray() throws Exception {
        final Random random = new Random(0);
        float[] array = new float[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextFloat();
        }

        FloatIlm floatIlm = FloatIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, FloatIlmUtil.toArray(floatIlm)));
    }
}
// AUTO GENERATED FROM TEMPLATE
