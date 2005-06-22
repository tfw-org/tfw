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

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaSegment;
import tfw.immutable.ila.longila.LongIlaFromArray;

public class LongIlaSegmentTest extends TestCase
{
	public void testLongIlaSegment()
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		long[] array = new long[LENGTH];
		
		for (int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextLong();
		}
		
		LongIla ila = LongIlaFromArray.create(array);
		
		for (int start=0 ; start < array.length ; start++)
		{
			for (int length=0 ; length < array.length - start ; length++)
			{
				long[] a = new long[length];
				
				System.arraycopy(array, start, a, 0, length);
				
				LongIla i = LongIlaFromArray.create(a);
		
				String s = LongIlaTest.check(i,
					LongIlaSegment.create(ila, start, length));
		
				assertNull(s, s);
			}
		}
	}
}
