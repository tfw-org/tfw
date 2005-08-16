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
import tfw.immutable.ila.floatila.FloatIlaMultiply;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

public class FloatIlaMultiplyTest extends TestCase
{
	public void testFloatIlaMultiply()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		float[] array1 = new float[LENGTH];
		float[] array2 = new float[LENGTH];
		float[] array3 = new float[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array1[i] = random.nextFloat();
			array2[i] = random.nextFloat();
			array3[i] = array1[i] * array2[i];
		}
		
		FloatIla ila1 = FloatIlaFromArray.create(array1);
		FloatIla ila2 = FloatIlaFromArray.create(array2);
		FloatIla ila3 = FloatIlaFromArray.create(array3);
		
		try
		{
			FloatIlaMultiply.create(null, ila2);
			fail("firstIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlaMultiply.create(ila1, null);
			fail("secondIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = FloatIlaCheck.check(ila3,
			FloatIlaMultiply.create(ila1, ila2));
		
		assertNull(s, s);
	}
}