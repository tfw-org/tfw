package tfw.value.test;

import junit.framework.TestCase;
import tfw.value.IntegerConstraint;
import tfw.value.NullConstraint;
import tfw.value.ValueException;


/**
 *
 */
public class NullConstraintTest extends TestCase
{
    public void testIsCompatable()
    {
		NullConstraint nc = NullConstraint.INSTANCE;

        try
        {
            nc.isCompatible(null);
            fail("isCompatible() accepted null constraint");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        assertTrue("isCompatible() rejected itself", nc.isCompatible(nc));
        assertFalse("isCompatible() accepted an IntegerConstaint",
            nc.isCompatible(new IntegerConstraint(0, 1)));
    }
    
    public void testgetValueCompliance(){
		NullConstraint nc = NullConstraint.INSTANCE;

		try
		{
			nc.checkValue(new Object());
			fail("getValueCompliance() accepted null constraint");
		}
		catch (ValueException expected)
		{
			//System.out.println(expected);
		}
    	try{
    		nc.checkValue(null);
    	} catch (ValueException unexpected){
    		fail("checkValue() didn't accept null");
    	}
    }
}
