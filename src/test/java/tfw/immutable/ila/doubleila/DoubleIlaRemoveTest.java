package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class DoubleIlaRemoveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] array = new double[length];
        final double[] target = new double[length - 1];
        for (int index = 0; index < length; ++index) {
            int targetii = 0;
            for (int ii = 0; ii < array.length; ++ii) {
                array[ii] = random.nextDouble();
                if (ii != index) {
                    target[targetii++] = array[ii];
                }
            }
            DoubleIla origIla = DoubleIlaFromArray.create(array);
            DoubleIla targetIla = DoubleIlaFromArray.create(target);
            DoubleIla actualIla = DoubleIlaRemove.create(origIla, index);
            final double epsilon = 0.0;
            DoubleIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
