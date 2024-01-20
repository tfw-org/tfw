package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaMutateTest {
    @Test
    void testArguments() throws Exception {
        final ObjectIla ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();
        final Object value = new Object();

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaMutate.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaMutate.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaMutate.create(ila, ilaLength, value));
    }

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
