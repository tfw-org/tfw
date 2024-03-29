package tfw.immutable.ila.objectila;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

class ObjectIlaInsertTest {
    @Test
    void testArguments() throws Exception {
        final ObjectIla<Object> ila = ObjectIlaFromArray.create(new Object[10]);
        final long ilaLength = ila.length();
        final Object value = new Object();

        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInsert.create(null, 0, value));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInsert.create(ila, -1, value));
        assertThrows(IllegalArgumentException.class, () -> ObjectIlaInsert.create(ila, ilaLength + 1, value));
    }

    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] target = new Object[length + 1];
        for (int index = 0; index < length; ++index) {
            final Object value = new Object();
            int skipit = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                if (index == ii) {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = new Object();
            }
            ObjectIla<Object> origIla = ObjectIlaFromArray.create(array);
            ObjectIla<Object> targetIla = ObjectIlaFromArray.create(target);
            ObjectIla<Object> actualIla = ObjectIlaInsert.create(origIla, index, value);

            ObjectIlaCheck.check(targetIla, actualIla);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
