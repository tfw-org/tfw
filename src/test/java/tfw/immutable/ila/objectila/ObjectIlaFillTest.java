package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaFillTest {
    @Test
    void testArguments() {
        final Object value = new Object();

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaFill.create(value, -1));
    }

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

        ObjectIlaCheck.check(targetIla, actualIla);
    }
}
// AUTO GENERATED FROM TEMPLATE
