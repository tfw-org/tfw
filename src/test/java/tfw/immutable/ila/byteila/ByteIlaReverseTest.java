package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaReverseTest {
    @Test
    void testArguments() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final byte[] buffer = new byte[10];

        assertThrows(IllegalArgumentException.class, () -> ByteIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] reversed = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        ByteIla origIla = ByteIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(reversed);
        ByteIla actualIla = ByteIlaReverse.create(origIla, new byte[1000]);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
