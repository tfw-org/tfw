package tfw.immutable.ila.stringila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class StringIlaFillTest {
    @Test
    void testAll() throws Exception {
        final String value = new String();
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        StringIla targetIla = StringIlaFromArray.create(array);
        StringIla actualIla = StringIlaFill.create(value, length);
        final String epsilon = "";
        StringIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
