package tfw.immutable.ila.objectila;

import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;

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
        ObjectIla<Object> targetIla = ObjectIlaFromArray.create(array);
        ObjectIla<Object> actualIla = ObjectIlaFill.create(value, length);
        final Object epsilon = Object.class;
        ObjectIlaCheck.checkAll(targetIla, actualIla,
                                  IlaTestDimensions.defaultOffsetLength(),
                                  IlaTestDimensions.defaultMaxStride(),
                                  epsilon,
                                  ObjectIlaCheck.OBJECT_CHECK_FACTORY);
    }
}
// AUTO GENERATED FROM TEMPLATE
