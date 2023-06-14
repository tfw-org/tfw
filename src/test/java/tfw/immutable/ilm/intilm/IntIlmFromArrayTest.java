package tfw.immutable.ilm.intilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class IntIlmFromArrayTest {
    @Test
    void testIntIlmFromArray() throws Exception {
        final Random random = new Random(0);
        int[] array = new int[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt();
        }

        IntIlm intIlm = IntIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, intIlm.toArray()));
    }
}
// AUTO GENERATED FROM TEMPLATE
