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

package tfw.immutable.ila.doubleila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaAdd;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class DoubleIlaAddTest extends TestCase
{
	public void testDoubleIlaAdd()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		double[] array1 = new double[LENGTH];
		double[] array2 = new double[LENGTH];
		double[] array3 = new double[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array1[i] = random.nextDouble();
			array2[i] = random.nextDouble();
			array3[i] = array1[i] + array2[i];
		}
		
		DoubleIla ila1 = DoubleIlaFromArray.create(array1);
		DoubleIla ila2 = DoubleIlaFromArray.create(array2);
		DoubleIla ila3 = DoubleIlaFromArray.create(array3);
		
		try
		{
			DoubleIlaAdd.create(null, ila2);
			fail("firstIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			DoubleIlaAdd.create(ila1, null);
			fail("secondIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = DoubleIlaTest.check(ila3,
			DoubleIlaAdd.create(ila1, ila2));
		
		assertNull(s, s);
	}
}