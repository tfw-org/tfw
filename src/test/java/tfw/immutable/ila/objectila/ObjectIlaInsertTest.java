package tfw.immutable.ila.objectila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ObjectIlaInsertTest extends TestCase {
    public void testAll() throws Exception {
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
            ObjectIla origIla = ObjectIlaFromArray.create(array);
            ObjectIla targetIla = ObjectIlaFromArray.create(target);
            ObjectIla actualIla = ObjectIlaInsert.create(origIla, index, value);
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
