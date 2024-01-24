package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaDecimateTest {
    @Test
    void testArguments() {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final byte[] buffer = new byte[10];

        assertThrows(IllegalArgumentException.class, () -> ByteIlaDecimate.create(null, 2, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaDecimate.create(ila, 2, null));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaDecimate.create(ila, 1, buffer));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaDecimate.create(ila, 2, new byte[0]));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final byte[] target = new byte[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaDecimate.create(ila, factor, new byte[100]);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
