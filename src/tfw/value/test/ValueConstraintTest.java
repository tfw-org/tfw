package tfw.value.test;

import tfw.value.ClassValueConstraint;
import tfw.value.ValueException;

import junit.framework.TestCase;


/**
 *
 *
 */
public class ValueConstraintTest extends TestCase
{
	private final class MyConstraint extends ClassValueConstraint{
		public MyConstraint(Class valueType){
			super(valueType);
		}
	}
	
    public void testNullValueType()
    {
        try
        {
            new MyConstraint(null);
            fail("Constructor accepted null valueConstraint");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
    }

    public void testGetValueCompliance()
    {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);
        String result = vc.getValueCompliance(new Integer(0));
        assertEquals("Valid class type rejected!", ClassValueConstraint.VALID, result);
        result = vc.getValueCompliance(null);
        assertFalse("null accepted!", ClassValueConstraint.VALID.equals(result));
        result = vc.getValueCompliance(new Object());
        assertFalse("accepted invalid type!",
            ClassValueConstraint.VALID.equals(result));
    }

    public void testIsValid()
    {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);
        assertTrue("rejected valid value!", vc.isValid(new Integer(1)));
        assertFalse("accepted an invalid value!", vc.isValid(new Object()));
    }

    public void testCheckValue()
    {
        ClassValueConstraint vc = ClassValueConstraint.getInstance(Integer.class);

        try
        {
            vc.checkValue(new Object());
            fail("checkValue() accepted invalid value");
        }
        catch (ValueException expected)
        {
            //System.out.println(expected);
        }
        
        try
        {
        	vc.checkValue(new Integer(0));
        } catch (ValueException unexpected)
        {
        	fail("checkValue() threw exception on valid value");
        }
    }
}
