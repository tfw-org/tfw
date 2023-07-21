package tfw.immutable.ila.objectila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ObjectIlaFillTest {
    @Test
    void testAll() throws Exception {
        final Object value = new Object();
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> actualIla = ObjectIlaFill.create(value, length);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
