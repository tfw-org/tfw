package tfw.immutable.ila.charila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=numeric
 */
class CharIlaMultiplyTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] leftArray = new char[length];
        final char[] rightArray = new char[length];
        final char[] array = new char[length];
        for (int ii = 0; ii < leftArray.length; ++ii) {
            leftArray[ii] = (char) random.nextInt();
            rightArray[ii] = (char) random.nextInt();
            array[ii] = (char) (leftArray[ii] * rightArray[ii]);
        }
        CharIla leftIla = CharIlaFromArray.create(leftArray);
        CharIla rightIla = CharIlaFromArray.create(rightArray);
        CharIla targetIla = CharIlaFromArray.create(array);
        CharIla actualIla = CharIlaMultiply.create(leftIla, rightIla);
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
