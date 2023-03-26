package tfw.tsm.ecd;

import junit.framework.TestCase;
import tfw.tsm.ecd.ObjectECD;
import tfw.value.ClassValueConstraint;
import tfw.value.ValueConstraint;


/**
 *
 */
public class EventChannelDescriptionTest extends TestCase
{
    public void testConstruction()
    {
        try
        {
            new TestECD(null, ClassValueConstraint.STRING);
            fail("constructor accepted null name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestECD(" ", ClassValueConstraint.STRING);
            fail("constructor accepted empty name");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestECD("A", null);
            fail("constructor accepted constraint");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }

        try
        {
            new TestECD("A", ClassValueConstraint.STRING);
        }
        catch (IllegalArgumentException expected)
        {
            fail("constructor did not accept a null codec");
        }
    }

    public void testEquals()
    {
		TestECD ecd1 = new TestECD("A", ClassValueConstraint.STRING);
		TestECD ecd2 = new TestECD("A", ClassValueConstraint.STRING);
        assertEquals("Equivalent instances not equal",ecd1, ecd2);
		assertFalse("equal to null.",ecd1.equals(null));
       
        ecd2 = new TestECD("different", ClassValueConstraint.STRING);
		assertFalse("different names equal",ecd1.equals(ecd2));
		
		ecd2 = new TestECD("A", ClassValueConstraint.BOOLEAN);
		assertFalse("different constraints equal",ecd1.equals(ecd2));
		
//		ecd2 = new TestECD("A", ClassValueConstraint.STRING);
//		assertFalse("different codecs equal",ecd1.equals(ecd2));
		
//		ecd2 = new TestECD("A", ClassValueConstraint.STRING,
//						StringCodec.INSTANCE, true, false);
//		assertFalse("different fire-on-connect equal",ecd1.equals(ecd2));
		
//		ecd2 = new TestECD("A", ClassValueConstraint.STRING,
//						StringCodec.INSTANCE, false, true);
//		assertFalse("different rollback participant equal",ecd1.equals(ecd2));
    }

    private class TestECD extends ObjectECD
    {
        public TestECD(String eventChannelName, ValueConstraint constraint)
        {
            super(eventChannelName, constraint);
        }
    }
}
