package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaScalarAddTest {
    @Test
    void testArguments() {
        final Random random = new Random(0);
        final byte value = (byte) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaScalarAdd.create(null, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte scalar = (byte) random.nextInt();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (byte) random.nextInt();
            target[ii] = (byte) (array[ii] + scalar);
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(target);
        ByteIla actualIla = ByteIlaScalarAdd.create(ila, scalar);

        ByteIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
