package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
public class IntIlaDivideTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final int[] leftArray = new int[length];
        final int[] rightArray = new int[length];
        final int[] array = new int[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = random.nextInt();
            rightArray[ii] = random.nextInt();
            array[ii] = (int) (leftArray[ii] / rightArray[ii]);
        }
        IntIla leftIla = IntIlaFromArray.create(leftArray);
        IntIla rightIla = IntIlaFromArray.create(rightArray);
        IntIla targetIla = IntIlaFromArray.create(array);
        IntIla actualIla = IntIlaDivide.create(leftIla, rightIla);
        final int epsilon = (int) 0.0;
        IntIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
