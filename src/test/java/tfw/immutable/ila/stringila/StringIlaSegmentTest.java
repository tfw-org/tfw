package tfw.immutable.ila.stringila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaSegment;

/**
 *
 * @immutables.types=all
 */
public class StringIlaSegmentTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] master = new String[length];
        for(int ii = 0; ii < master.length; ++ii)
        {
            master[ii] = new String();
        }
        StringIla masterIla = StringIlaFromArray.create(master);
        StringIla checkIla = StringIlaSegment.create(masterIla, 0,
                                                         masterIla.length());
        final int offsetLength = IlaTestDimensions.defaultOffsetLength();
        final int maxStride = IlaTestDimensions.defaultMaxStride();
        final String epsilon = "";
        StringIlaCheck.checkWithoutCorrectness(checkIla, offsetLength,
                                                 epsilon);
        for(long start = 0; start < length; ++start)
        {
            for(long len = 0; len < length - start; ++len)
            {
                String[] array = new String[(int) len];
                for(int ii = 0; ii < array.length; ++ii)
                {
                    array[ii] = master[ii + (int) start];
                }
                StringIla targetIla = StringIlaFromArray.create(array);
                StringIla actualIla = StringIlaSegment.create(masterIla,
                                                                  start, len);
                StringIlaCheck.checkCorrectness(targetIla, actualIla,
                                                  offsetLength, maxStride,
                                                  epsilon);
            }
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
