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

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaMutate;

public class StringIlaMutateTest extends TestCase
{
	public void testStringIlaMutate()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		String[] array = new String[LENGTH];
		String element = new String();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = new String();
		}
		
		StringIla ila = StringIlaFromArray.create(array);
		
		try
		{
			StringIlaMutate.create(null, 0, element);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			StringIlaMutate.create(ila, -1, element);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			StringIlaMutate.create(ila, LENGTH, element);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			String[] a = new String[LENGTH];
			
			System.arraycopy(array, 0, a, 0, LENGTH);
			a[i] = element;
			
			StringIla ia = StringIlaFromArray.create(a);
			
			String s = StringIlaCheck.check(ia,
				StringIlaMutate.create(ila, i, element));
			
			assertNull(s, s);
		}
	}
}