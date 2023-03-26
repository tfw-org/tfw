package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaSubtract;

/**
 *
 * @immutables.types=numeric
 */
public class ByteIlaSubtractTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] leftArray = new byte[length];
        final byte[] rightArray = new byte[length];
        final byte[] array = new byte[length];
        for(int ii = 0; ii < leftArray.length; ++ii)
        {
            leftArray[ii] = (byte)random.nextInt();
            rightArray[ii] = (byte)random.nextInt();
            array[ii] = (byte) (leftArray[ii] - rightArray[ii]);
        }
        ByteIla leftIla = ByteIlaFromArray.create(leftArray);
        ByteIla rightIla = ByteIlaFromArray.create(rightArray);
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaSubtract.create(leftIla, rightIla);
        final byte epsilon = (byte) 0.0;
        ByteIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
