package tfw.immutable.ila.shortila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaFromCastByteIla;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

/**
 *
 * @immutables.types=numericnotbyte
 */
public class ShortIlaFromCastByteIlaTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final byte[] array = new byte[length];
        final short[] target = new short[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (byte) random.nextInt();
            target[ii] = (short) array[ii];
        }
        ByteIla ila = ByteIlaFromArray.create(array);
        ShortIla targetIla = ShortIlaFromArray.create(target);
        ShortIla actualIla = ShortIlaFromCastByteIla.create(ila);
        final short epsilon = (short) 0.0;
        ShortIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
