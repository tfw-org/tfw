package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] target = new byte[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = (byte) random.nextInt();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            ByteIla origIla = ByteIlaFromArray.create(array);
            ByteIla targetIla = ByteIlaFromArray.create(target);
            ByteIla actualIla = ByteIlaRemove.create(origIla, index);
            final byte epsilon = (byte) 0;
            ByteIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
