package tfw.immutable.ila.objectila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaFill;

/**
 *
 * @immutables.types=all
 */
public class ObjectIlaFillTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final Object value = new Object();
        final int length = IlaTestDimensions.defaultIlaLength();
        final Object[] array = new Object[length];
        for(int ii = 0; ii < array.length; ++ii)
        {
            array[ii] = value;
        }
        ObjectIla targetIla = ObjectIlaFromArray.create(array);
        ObjectIla actualIla = ObjectIlaFill.create(value, length);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon);
    }
}
// AUTO GENERATED FROM TEMPLATE
