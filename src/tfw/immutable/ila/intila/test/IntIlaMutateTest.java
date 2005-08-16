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
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ila.intila.IntIlaMutate;

public class IntIlaMutateTest extends TestCase
{
	public void testIntIlaMutate()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		int[] array = new int[LENGTH];
		int element = random.nextInt();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = random.nextInt();
		}
		
		IntIla ila = IntIlaFromArray.create(array);
		
		try
		{
			IntIlaMutate.create(null, 0, element);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			IntIlaMutate.create(ila, -1, element);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			IntIlaMutate.create(ila, LENGTH, element);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			int[] a = new int[LENGTH];
			
			System.arraycopy(array, 0, a, 0, LENGTH);
			a[i] = element;
			
			IntIla ia = IntIlaFromArray.create(a);
			
			String s = IntIlaCheck.check(ia,
				IntIlaMutate.create(ila, i, element));
			
			assertNull(s, s);
		}
	}
}