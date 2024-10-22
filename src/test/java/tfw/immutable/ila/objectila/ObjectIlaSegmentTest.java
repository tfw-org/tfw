package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaSegmentTest {
    @Test
    void testArguments() throws Exception {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, ilaLength + 1));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(null, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, -1, 0));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, 0, -1));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, ilaLength + 1, 0));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaSegment.create(ila, 0, ilaLength + 1));
    }

    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] master = new Object[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = new Object();
        }
        ObjectIla<Object> masterIla = ObjectIlaFromArray.create(master);
        ObjectIla<Object> checkIla = ObjectIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                Object[] array = new Object[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
                ObjectIla<Object> actualIla = ObjectIlaSegment.create(masterIla, start, len);
                ObjectIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
