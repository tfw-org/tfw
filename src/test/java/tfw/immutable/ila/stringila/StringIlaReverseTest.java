package tfw.immutable.ila.stringila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class StringIlaReverseTest extends TestCase {
    public void testAll() throws Exception {

        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        final String[] reversed = new String[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new String();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        StringIla origIla = StringIlaFromArray.create(array);
        StringIla targetIla = StringIlaFromArray.create(reversed);
        StringIla actualIla = StringIlaReverse.create(origIla);
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
