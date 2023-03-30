package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ila.charila.CharIlaFromCastFloatIla;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

/**
 *
 * @immutables.types=numericnotfloat
 */
public class CharIlaFromCastFloatIlaTest extends TestCase
{
    public void testAll() throws Exception
    {
        final Random random = new Random(0);
        final int length = IlaTestDimensions.defaultIlaLength();
        final float[] array = new float[length];
        final char[] target = new char[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = random.nextFloat();
            target[ii] = (char) array[ii];
        }
        FloatIla ila = FloatIlaFromArray.create(array);
        CharIla targetIla = CharIlaFromArray.create(target);
        CharIla actualIla = CharIlaFromCastFloatIla.create(ila);
        final char epsilon = (char) 0.0;
        CharIlaCheck.checkAll(targetIla, actualIla,
                                IlaTestDimensions.defaultOffsetLength(),
                                IlaTestDimensions.defaultMaxStride(),
                                epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
