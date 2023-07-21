package tfw.immutable.ila.floatila;

import java.util.Random;
import org.junit.jupiter.api.Test;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;

/**
 *
 * @immutables.types=numericnotchar
 */
class FloatIlaFromCastCharIlaTest {
    @Test
    void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final char[] array = new char[length];
        final float[] target = new float[length];
        for (int ii = 0; ii < array.length; ++ii) {
            array[ii] = (char) random.nextInt();
            target[ii] = (float) array[ii];
        }
        CharIla ila = CharIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastCharIla.create(ila, 100);
        final float epsilon = 0.0f;
        FloatIlaCheck.checkAll(
                targetIla,
                actualIla,
                IlaTestDimensions.defaultOffsetLength(),
                IlaTestDimensions.defaultMaxStride(),
                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
