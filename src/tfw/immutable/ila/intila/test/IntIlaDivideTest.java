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

package tfw.immutable.ila.intila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaDivide;
import tfw.immutable.ila.intila.IntIlaFromArray;

public class IntIlaDivideTest extends TestCase
{
	public void testIntIlaDivide()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		int[] array1 = new int[LENGTH];
		int[] array2 = new int[LENGTH];
		int[] array3 = new int[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array1[i] = random.nextInt();
			array2[i] = random.nextInt();
			array3[i] = array1[i] / array2[i];
		}
		
		IntIla ila1 = IntIlaFromArray.create(array1);
		IntIla ila2 = IntIlaFromArray.create(array2);
		IntIla ila3 = IntIlaFromArray.create(array3);
		
		try
		{
			IntIlaDivide.create(null, ila2);
			fail("firstIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			IntIlaDivide.create(ila1, null);
			fail("secondIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = IntIlaCheck.check(ila3,
			IntIlaDivide.create(ila1, ila2));
		
		assertNull(s, s);
	}
}