package tfw.immutable.ila.objectila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaSegmentTest {
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
