package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class ShortIlaSegmentTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] master = new short[length];
        for (int ii = 0; ii < master.length; ++ii) {
            master[ii] = (short) random.nextInt();
        }
        ShortIla masterIla = ShortIlaFromArray.create(master);
        ShortIla checkIla = ShortIlaSegment.create(masterIla, 0, masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final short epsilon = (short) 0;
        ShortIlaCheck.checkWithoutCorrectness(checkIla, offsetLength, epsilon);
        for (long start = 0; start < length; ++start) {
            for (long len = 0; len < length - start; ++len) {
                short[] array = new short[(int) len];
                for (int ii = 0; ii < array.length; ++ii) {
                    array[ii] = master[ii + (int) start];
                }
                ShortIla targetIla = ShortIlaFromArray.create(array);
                ShortIla actualIla = ShortIlaSegment.create(masterIla, start, len);
                ShortIlaCheck.checkCorrectness(targetIla, actualIla, offsetLength, maxStride, epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
