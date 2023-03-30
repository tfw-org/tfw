package tfw.immutable.ila.objectila;


import junit.framework.TestCase;
import tfw.immutable.ila.IlaTestDimensions;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaInterleave;

/**
 * 
 * @immutables.types=all
 */
public class ObjectIlaInterleaveTest extends TestCase
{
    public void testAll() throws Exception
    {
        
        final int length = IlaTestDimensions.defaultIlaLength();
        for (int jj = 2; jj < 6; ++jj)
        {
            final Object[][] target = new Object[jj][length];
            final Object[] array = new Object[jj * length];
            for (int ii = 0; ii < jj * length; ++ii)
            {
                array[ii] = target[ii % jj][ii / jj] = new Object();
            }
            ObjectIla[] ilas = new ObjectIla[jj];
            for (int ii = 0; ii < jj; ++ii)
            {
                ilas[ii] = ObjectIlaFromArray.create(target[ii]);
            }
            ObjectIla targetIla = ObjectIlaFromArray.create(array);
            ObjectIla actualIla = ObjectIlaInterleave.create(ilas);
            final Object epsilon = Object.class;
            ObjectIlaCheck.checkAll(targetIla, actualIla,
                                      IlaTestDimensions.defaultOffsetLength(),
                                      IlaTestDimensions.defaultMaxStride(),
                                      epsilon);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
