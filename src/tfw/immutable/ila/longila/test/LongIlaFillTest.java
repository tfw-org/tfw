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

package tfw.immutable.ila.longila.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFill;
import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaFillTest extends TestCase
{
	public void testLongIlaFill()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		final long element = random.nextLong();
	
		long[] array = new long[LENGTH];
	
		Arrays.fill(array, element);
		
		LongIla ila = LongIlaFromArray.create(array);
		
		try
		{
			LongIlaFill.create(element, -1);
			fail("length < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = LongIlaTest.check(ila,
			LongIlaFill.create(element, LENGTH));
		
		assertNull(s, s);
	}
}
