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

import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ila.doubleila.DoubleIlaFromLongIla;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;

public class DoubleIlaFromLongIlaTest extends TestCase
{
	public void testDoubleIlaFromLongIla()
	{
		final int LENGTH = 64;
		final long SKIP = Long.MAX_VALUE / LENGTH * 2;
		long[] longArray = new long[LENGTH];
		double[] doubleArray = new double[LENGTH];
		
		long v=-Long.MAX_VALUE-1;
		for (int i=0 ; i < LENGTH ; i++,v+=SKIP)
		{
			longArray[i] = v;
			doubleArray[i] = Double.longBitsToDouble(v);
		}
		
		LongIla longIla = LongIlaFromArray.create(longArray);
		DoubleIla doubleIla = DoubleIlaFromArray.create(doubleArray);
		
		String s = DoubleIlaCheck.check(doubleIla,
			DoubleIlaFromLongIla.create(longIla));
			
		assertNull(s, s);

	}
}
