package tfw.immutable.ila.objectila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class ObjectIlaMutateTest {
    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] target = new Object[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = new Object();
            }
            final Object value = new Object();
            target[index] = value;
            ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaMutate.create(origIla, index, value);
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
