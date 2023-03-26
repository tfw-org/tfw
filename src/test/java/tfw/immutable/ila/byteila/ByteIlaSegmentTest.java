package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class ByteIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] master = new byte[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = (byte)random.nextInt();
        }
        ByteIla masterIla = ByteIlaFromArray.create(master);
        ByteIla checkIla = ByteIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final byte epsilon = (byte)0;
        ByteIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                byte[] array = new byte[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                ByteIla targetIla = ByteIlaFromArray.create(array);
                ByteIla actualIla = ByteIlaSegment.create(masterIla,
                                                                  start, len);
                ByteIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
