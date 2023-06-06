package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

/**
 *
 * @immutables.types=all
 */
public class FloatIlaInterleaveTest extends TestCase {
    public void testAll() throws Exception {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj) {
            final float[][] target = new float[jj][length];
            final float[] array = new float[jj * length];
            for (int ii = 0; ii < jj * length; ++ii) {
                array[ii] = target[ii % jj][ii / jj] = random.nextFloat();
            }
            FloatIla[] ilas = new FloatIla[jj];
            for (int ii = 0; ii < jj; ++ii) {
                ilas[ii] = FloatIlaFromArray.create(target[ii]);
            }
            FloatIla targetIla = FloatIlaFromArray.create(array);
            FloatIla actualIla = FloatIlaInterleave.create(ilas);
            final float epsilon = 0.0f;
            FloatIlaCheck.checkAll(
                    targetIla,
                    actualIla,
                    IlaTestDimensions.defaultOffsetLength(),
                    IlaTestDimensions.defaultMaxStride(),
                    epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
