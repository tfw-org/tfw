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
import tfw.immutable.ila.doubleila.DoubleIlaIterator;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;

public class DoubleIlaIteratorTest extends TestCase
{
	public void testDoubleIlaFill()
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		double[] array = new double[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextDouble();
		}
		
		DoubleIla ila = DoubleIlaFromArray.create(array);
		DoubleIlaIterator ii = new DoubleIlaIterator(ila);

		int i=0;
		for ( ; ii.hasNext() ; i++)
		{
			if (i == array.length)
			{
				fail("Iterator did not stop correctly");
			}
			assertEquals("element "+i+" not equal!",
				ii.next(), array[i], 0);
		}
		
		if (i != array.length)
		{
			fail("Iterator stopped at "+i+" not "+array.length);
		}		
	}
}
