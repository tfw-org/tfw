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

package tfw.immutable.ila.objectila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ila.objectila.ObjectIlaInsert;

public class ObjectIlaInsertTest extends TestCase
{
	public void testObjectIlaInsert()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		Object[] array = new Object[LENGTH];
		Object element = new Object();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = new Object();
		}
		
		ObjectIla ila = ObjectIlaFromArray.create(array);
		
		try
		{
			ObjectIlaInsert.create(null, 0, element);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ObjectIlaInsert.create(ila, -1, element);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ObjectIlaInsert.create(ila, LENGTH+1, element);
			fail("index > ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			Object[] a = new Object[LENGTH + 1];
			
			System.arraycopy(array, 0, a, 0, i);
			a[i] = element;
			System.arraycopy(array, i, a, i + 1, LENGTH - i);
			
			ObjectIla ia = ObjectIlaFromArray.create(a);
			
			String s = ObjectIlaTest.check(ia,
				ObjectIlaInsert.create(ila, i, element));
			
			assertNull(s, s);
		}
	}
}