package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
public class ShortIlaAddTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] leftArray = new short[length];
        final short[] rightArray = new short[length];
        final short[] array = new short[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (short) random.nextInt();
            rightArray[ii] = (short) random.nextInt();
            array[ii] = (short) (leftArray[ii] + rightArray[ii]);
        }
        ShortIla leftIla = ShortIlaFromArray.create(leftArray);
        ShortIla rightIla = ShortIlaFromArray.create(rightArray);
        ShortIla targetIla = ShortIlaFromArray.create(array);
        ShortIla actualIla = ShortIlaAdd.create(leftIla, rightIla);
        final short epsilon = (short) 0.0;
        ShortIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
