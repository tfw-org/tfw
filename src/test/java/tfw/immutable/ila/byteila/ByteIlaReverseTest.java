package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaReverse;

/**
 *
 * @immutables.types=all
 */
public class ByteIlaReverseTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final byte[] reversed = new byte[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (byte)random.nextInt();
        }
        for(int ii = 0; ii < reversed.length; ++ii)
        {
            reversed[ii] = array[length - 1 - ii];
        }
        ByteIla origIla = ByteIlaFromArray.create(array);
        ByteIla targetIla = ByteIlaFromArray.create(reversed);
        ByteIla actualIla = ByteIlaReverse.create(origIla);
        final byte epsilon = (byte)0;
        ByteIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE