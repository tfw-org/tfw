package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ila.floatila.FloatIlaFromCastShortIla;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;

/**
 *
 * @immutables.types=numericnotshort
 */
public class FloatIlaFromCastShortIlaTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final short[] array = new short[length];
        final float[] target = new float[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = (short) random.nextInt();
            target[ii] = (float) array[ii];
        }
        ShortIla ila = ShortIlaFromArray.create(array);
        FloatIla targetIla = FloatIlaFromArray.create(target);
        FloatIla actualIla = FloatIlaFromCastShortIla.create(ila);
        final float epsilon = (float) 0.0;
        FloatIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
