package tfw.immutable.ila.objectila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ObjectIlaReverseTest extends TestCase {
    public void testAll() throws Exception {

        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        final Object[] reversed = new Object[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = new Object();
        }
        for (int ii = 0; ii < reversed.length; ++ii) {
            reversed[ii] = array[length - 1 - ii];
        }
        ObjectIla origIla = ObjectIlaFromArray.create(array);
        ObjectIla targetIla = ObjectIlaFromArray.create(reversed);
        ObjectIla actualIla = ObjectIlaReverse.create(origIla);
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
