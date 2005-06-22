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

package tfw.immutable.ila.intila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaSegment;
import tfw.immutable.ila.intila.IntIlaFromArray;

public class IntIlaSegmentTest extends TestCase
{
	public void testIntIlaSegment()
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		int[] array = new int[LENGTH];
		
		for (int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextInt();
		}
		
		IntIla ila = IntIlaFromArray.create(array);
		
		for (int start=0 ; start < array.length ; start++)
		{
			for (int length=0 ; length < array.length - start ; length++)
			{
				int[] a = new int[length];
				
				System.arraycopy(array, start, a, 0, length);
				
				IntIla i = IntIlaFromArray.create(a);
		
				String s = IntIlaTest.check(i,
					IntIlaSegment.create(ila, start, length));
		
				assertNull(s, s);
			}
		}
	}
}
