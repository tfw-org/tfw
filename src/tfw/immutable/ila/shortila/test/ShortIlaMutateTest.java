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

package tfw.immutable.ila.shortila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ila.shortila.ShortIlaMutate;

public class ShortIlaMutateTest extends TestCase
{
	public void testShortIlaMutate()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		short[] array = new short[LENGTH];
		short element = (short)random.nextInt();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = (short)random.nextInt();
		}
		
		ShortIla ila = ShortIlaFromArray.create(array);
		
		try
		{
			ShortIlaMutate.create(null, 0, element);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ShortIlaMutate.create(ila, -1, element);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ShortIlaMutate.create(ila, LENGTH, element);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			short[] a = new short[LENGTH];
			
			System.arraycopy(array, 0, a, 0, LENGTH);
			a[i] = element;
			
			ShortIla ia = ShortIlaFromArray.create(a);
			
			String s = ShortIlaTest.check(ia,
				ShortIlaMutate.create(ila, i, element));
			
			assertNull(s, s);
		}
	}
}