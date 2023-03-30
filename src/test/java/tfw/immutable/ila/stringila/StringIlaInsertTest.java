package tfw.immutable.ila.stringila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaInsert;

/**
 *
 * @immutables.types=all
 */
public class StringIlaInsertTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        final String[] array = new String[length];
        final String[] target = new String[length+1];
        for(int index = 0; index < length; ++index)
        {
            final String value = new String();
            int skipit = 0;
            for(int ii = 0; ii < array.length; ++ii)
            {
                if(index == ii)
                {
                    skipit = 1;
                    target[ii] = value;
                }
                target[ii + skipit] = array[ii] = new String();
            }
            StringIla origIla = StringIlaFromArray.create(array);
            StringIla targetIla = StringIlaFromArray.create(target);
            StringIla actualIla = StringIlaInsert.create(origIla, index,
                                                             value);
            final String epsilon = "";
            StringIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
