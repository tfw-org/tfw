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

package tfw.immutable.ila.stringila.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFill;
import tfw.immutable.ila.stringila.StringIlaFromArray;

public class StringIlaFillTest extends TestCase
{
	public void testStringIlaFill()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		final String element = new String();
	
		String[] array = new String[LENGTH];
	
		Arrays.fill(array, element);
		
		StringIla ila = StringIlaFromArray.create(array);
		
		try
		{
			StringIlaFill.create(element, -1);
			fail("length < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = StringIlaCheck.check(ila,
			StringIlaFill.create(element, LENGTH));
		
		assertNull(s, s);
	}
}
