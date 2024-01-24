package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final Random random = new Random(0);
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final long ilaLength = ila.length();
        final byte value = (byte) random.nextInt();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaMutate.create(ila, ilaLength, value));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = (byte) random.nextInt();
            }
            final byte value = (byte) random.nextInt();
            target[index] = value;
            ByteIla origIla = ByteIlaFromArray.create(array);
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaMutate.create(origIla, index, value);

            ByteIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
