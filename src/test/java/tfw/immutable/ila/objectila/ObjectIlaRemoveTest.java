package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaRemoveTest {
    @Test
    void testArguments() throws Exception {
        final ObjectIla ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaRemove.create(null, 0));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaRemove.create(ila, -1));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaRemove.create(ila, ilaLength));
    }

    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] target = new Object[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = new Object();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaRemove.create(origIla, index);
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
