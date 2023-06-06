package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class BooleanIlaFillTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final boolean value = random.nextBoolean();
        final int length = IlaTestDimensions.defaultIlaLength();
        final boolean[] array = new boolean[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        BooleanIla targetIla = BooleanIlaFromArray.create(array);
        BooleanIla actualIla = BooleanIlaFill.create(value, length);
        final boolean epsilon = false;
        BooleanIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
