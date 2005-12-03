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
 * witout even the implied warranty of
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
package tfw.tsm.test;

import junit.framework.TestCase;
import tfw.tsm.Validator;
import tfw.tsm.ecd.EventChannelDescription;
import tfw.tsm.ecd.ObjectECD;
import tfw.tsm.ecd.RollbackECD;
import tfw.tsm.ecd.StatelessTriggerECD;
import tfw.tsm.ecd.StringECD;


/**
 *
 */
public class ValidatorTest extends TestCase
{
    public void testConstruction()
    {
        ObjectECD[] sinks = new ObjectECD[]
            {
                new StringECD("Test")
            };

		try
		{
			new MyValidator(null, sinks, null, null);

			fail("constructor accepted null name");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}

        try
        {
            new MyValidator("Test", new ObjectECD[]{ null },
                null, null);

            fail("constructor accepted null element in sink event channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
        
		try
		{
			new MyValidator("Test", sinks, new ObjectECD[]{ null }, null);

			fail("constructor accepted null element in non-triggering sinks");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}

        try
        {
            new MyValidator("Test", null, null, null);
            fail("constructor accepted null sink event channels");
        }
        catch (IllegalArgumentException expected)
        {
            //System.out.println(expected);
        }
		StatelessTriggerECD[] statelessTriggers = new StatelessTriggerECD[]
			{
				new StatelessTriggerECD("test")
			};
    }

    private class MyValidator extends Validator
    {
        public MyValidator(String name,
            ObjectECD[] triggeringSinks,
            ObjectECD[] nonTriggeringSinks,
            RollbackECD[] initiators)
        {
            super(name, triggeringSinks, nonTriggeringSinks, initiators);
        }

        public void validateState()
        {
        }
    }
}
