package tfw.immutable.ila.stringila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class StringIlaRemoveTest extends TestCase {
    public void testAll() throws Exception {

        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        final String[] target = new String[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = new String();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            StringIla origIla = StringIlaFromArray.create(array);
            StringIla targetIla = StringIlaFromArray.create(target);
            StringIla actualIla = StringIlaRemove.create(origIla, index);
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
