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

package tfw.immutable.ila.booleanila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ila.booleanila.BooleanIlaMutate;

public class BooleanIlaMutateTest extends TestCase
{
	public void testBooleanIlaMutate()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		boolean[] array = new boolean[LENGTH];
		boolean element = random.nextBoolean();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = random.nextBoolean();
		}
		
		BooleanIla ila = BooleanIlaFromArray.create(array);
		
		try
		{
			BooleanIlaMutate.create(null, 0, element);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			BooleanIlaMutate.create(ila, -1, element);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			BooleanIlaMutate.create(ila, LENGTH, element);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			boolean[] a = new boolean[LENGTH];
			
			System.arraycopy(array, 0, a, 0, LENGTH);
			a[i] = element;
			
			BooleanIla ia = BooleanIlaFromArray.create(a);
			
			String s = BooleanIlaCheck.check(ia,
				BooleanIlaMutate.create(ila, i, element));
			
			assertNull(s, s);
		}
	}
}