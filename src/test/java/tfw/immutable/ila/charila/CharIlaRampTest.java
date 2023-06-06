package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
public class CharIlaRampTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final char startValue = (char) random.nextInt();
        final char increment = (char) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        char value = startValue;
        for (int ii = 0; ii < array.length; ++ii, value += increment) {
            array[ii] = value;
        }
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaRamp.create(startValue, increment, length);
        final char epsilon = (char) 0.000001;
        CharIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
