package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaFill;

/**
 *
 * @immutables.types=all
 */
public class ByteIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final byte value = (byte)random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaFill.create(value, length);
        final byte epsilon = (byte)0;
        ByteIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
