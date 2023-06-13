package tfw.immutable.ila.stringila;

import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
class StringIlaMutateTest {
    @Test
    void testAll() throws Exception {
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        final String[] target = new String[length];
        for (int index = 0; index < length; ++index) {
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = target[ii] = new String();
            }
            final String value = new String();
            target[index] = value;
            StringIla origIla = StringIlaFromArray.create(array);
            StringIla targetIla = StringIlaFromArray.create(target);
            StringIla actualIla = StringIlaMutate.create(origIla, index, value);
            final String epsilon = "";
            StringIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
