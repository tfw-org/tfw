package tfw.immutable.ila.byteila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaRamp;

/**
 *
 * @immutables.types=numeric
 */
public class ByteIlaRampTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final byte startValue = (byte)random.nextInt();
        final byte increment = (byte)random.nextInt();
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        byte value = startValue;
        for(int ii = 0; ii < array.length; ++ii, value += increment)
        {
            array[ii] = value;
        }
        ByteIla targetIla = ByteIlaFromArray.create(array);
        ByteIla actualIla = ByteIlaRamp.create(startValue, increment,
                                                       length);
        final byte epsilon = (byte) 0.000001;
        ByteIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
