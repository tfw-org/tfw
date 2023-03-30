package tfw.immutable.ila.doubleila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaDivide;

/**
 *
 * @immutables.types=numeric
 */
public class DoubleIlaDivideTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final double[] leftArray = new double[length];
        final double[] rightArray = new double[length];
        final double[] array = new double[length];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            leftArray[ii] = random.nextDouble();
            rightArray[ii] = random.nextDouble();
            array[ii] = (double) (leftArray[ii] / rightArray[ii]);
        }
        DoubleIla leftIla = DoubleIlaFromArray.create(leftArray);
        DoubleIla rightIla = DoubleIlaFromArray.create(rightArray);
        DoubleIla targetIla = DoubleIlaFromArray.create(array);
        DoubleIla actualIla = DoubleIlaDivide.create(leftIla, rightIla);
        final double epsilon = (double) 0.0;
        DoubleIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
