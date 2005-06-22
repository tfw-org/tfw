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

package tfw.immutable.ila.floatila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ila.floatila.FloatIlaRemove;

public class FloatIlaRemoveTest extends TestCase
{
	public void testFloatIlaRemove()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		float[] array = new float[LENGTH];
		float element = random.nextFloat();
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = random.nextFloat();
		}
		
		FloatIla ila = FloatIlaFromArray.create(array);
		
		try
		{
			FloatIlaRemove.create(null, 0);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlaRemove.create(ila, -1);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlaRemove.create(ila, LENGTH);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			float[] a = new float[LENGTH - 1];
			
			System.arraycopy(array, 0, a, 0, i);
			System.arraycopy(array, i + 1, a, i, LENGTH - i - 1);
			
			FloatIla ia = FloatIlaFromArray.create(a);
			
			String s = FloatIlaTest.check(ia,
				FloatIlaRemove.create(ila, i));
			
			assertNull(s, s);
		}
	}
}