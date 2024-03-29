package tfw.immutable.ila.byteila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ByteIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final ByteIla ila = ByteIlaFromArray.create(new byte[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> ByteIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] master = new byte[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = (byte) random.nextInt();
        }
        ByteIla masterIla = ByteIlaFromArray.create(master);
        ByteIla checkIla = ByteIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final byte epsilon = (byte) 0;
        ByteIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                byte[] array = new byte[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                ByteIla targetIla = ByteIlaFromArray.create(array);
                ByteIla actualIla = ByteIlaSegment.create(masterIla, start, len);
                ByteIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
