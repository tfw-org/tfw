package tfw.immutable.ila.objectila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ObjectIlaDecimateTest {
    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new Object();
        }
        ObjectIla<Object> ila = ObjectIlaFromArray.create(array);
        for (int factor = 2; factor <= length; ++factor) {
            final int targetLength = (length + factor - 1) / factor;
            final Object[] target = new Object[targetLength];
            for (int ii = 0; ii < target.length; ++ii) {
                target[ii] = array[ii * factor];
            }
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaDecimate.create(ila, factor, new Object[100]);
            final Object epsilon = Object.class;
            ObjectIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
