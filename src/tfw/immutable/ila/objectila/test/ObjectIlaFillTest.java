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

package tfw.immutable.ila.objectila.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFill;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;

public class ObjectIlaFillTest extends TestCase
{
	public void testObjectIlaFill()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		final Object element = new Object();
	
		Object[] array = new Object[LENGTH];
	
		Arrays.fill(array, element);
		
		ObjectIla ila = ObjectIlaFromArray.create(array);
		
		try
		{
			ObjectIlaFill.create(element, -1);
			fail("length < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = ObjectIlaCheck.check(ila,
			ObjectIlaFill.create(element, LENGTH));
		
		assertNull(s, s);
	}
}
