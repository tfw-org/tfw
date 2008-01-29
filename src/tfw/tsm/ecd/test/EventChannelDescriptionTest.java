/*
 * The Framework Project
 * Copyright (C) 2005 Anonymous
 * 
 * This library is free software; you can
 * redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your
 * option) any later version.
 * 
 * This library is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU
 * Lesser General Public License along with this
 * library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307 USA
 */
package tfw.tsm.ecd.test;

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
		
		ecd2 = new TestECD("A", ClassValueConstraint.STRING);
		assertFalse("different codecs equal",ecd1.equals(ecd2));
		
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
