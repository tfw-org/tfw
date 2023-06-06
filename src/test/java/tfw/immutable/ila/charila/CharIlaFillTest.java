package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class CharIlaFillTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final char value = (char) random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = value;
        }
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaFill.create(value, length);
        final char epsilon = (char) 0;
        CharIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
