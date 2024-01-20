package tfw.immutable.ila.charila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class CharIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final CharIla ila = CharIlaFromArray.create(new char[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> CharIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] master = new char[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = (char) random.nextInt();
        }
        CharIla masterIla = CharIlaFromArray.create(master);
        CharIla checkIla = CharIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final char epsilon = (char) 0;
        CharIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                char[] array = new char[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                CharIla targetIla = CharIlaFromArray.create(array);
                CharIla actualIla = CharIlaSegment.create(masterIla, start, len);
                CharIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
