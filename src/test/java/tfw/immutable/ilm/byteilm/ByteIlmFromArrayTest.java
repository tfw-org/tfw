package tfw.immutable.ilm.byteilm;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ByteIlmFromArrayTest {
    @Test
    void testByteIlmFromArray() throws Exception {
        final Random random = new Random(0);
        byte[] array = new byte[6];

        for (int i = 0; i < array.length; i++) {
            array[i] = (byte) random.nextInt();
        }

        ByteIlm byteIlm = ByteIlmFromArray.create(array, array.length / 2);

        assertTrue(Arrays.equals(array, byteIlm.toArray()));
    }
}
// AUTO GENERATED FROM TEMPLATE
