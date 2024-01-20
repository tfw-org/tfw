package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaReverseTest {
    @Test
    void testArguments() throws Exception {
        final ObjectIla ila = ObjectIlaFromArray.create(new Object[10]);
        final Object[] buffer = new Object[10];

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaReverse.create(null, buffer));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaReverse.create(ila, null));
    }

    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] reversed = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new Object();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(reversed);
        ObjectIla<Object> actualIla = ObjectIlaReverse.create(origIla, new Object[1000]);
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
