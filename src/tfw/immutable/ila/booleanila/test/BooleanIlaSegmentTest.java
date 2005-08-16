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

package tfw.immutable.ila.booleanila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaSegment;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

public class BooleanIlaSegmentTest extends TestCase
{
	public void testBooleanIlaSegment()
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		boolean[] array = new boolean[LENGTH];
		
		for (int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextBoolean();
		}
		
		BooleanIla ila = BooleanIlaFromArray.create(array);
		
		for (int start=0 ; start < array.length ; start++)
		{
			for (int length=0 ; length < array.length - start ; length++)
			{
				boolean[] a = new boolean[length];
				
				System.arraycopy(array, start, a, 0, length);
				
				BooleanIla i = BooleanIlaFromArray.create(a);
		
				String s = BooleanIlaCheck.check(i,
					BooleanIlaSegment.create(ila, start, length));
		
				assertNull(s, s);
			}
		}
	}
}
