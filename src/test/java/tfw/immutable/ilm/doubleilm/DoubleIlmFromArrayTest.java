package tfw.immutable.ilm.doubleilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class DoubleIlmFromArrayTest {
    @Test
    void testDoubleIlmFromArray() throws Exception {
        final Random random = new Random(0);
        double[] array = new double[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextDouble();
        }

        DoubleIlm doubleIlm = DoubleIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, doubleIlm.toArray()));
    }
}
// AUTO GENERATED FROM TEMPLATE
