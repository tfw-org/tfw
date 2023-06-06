package tfw.immutable.ila.objectila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ObjectIlaRemoveTest extends TestCase {
    public void testAll() throws Exception {
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
            ObjectIla origIla = ObjectIlaFromArray.create(array);
            ObjectIla targetIla = ObjectIlaFromArray.create(target);
            ObjectIla actualIla = ObjectIlaRemove.create(origIla, index);
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
