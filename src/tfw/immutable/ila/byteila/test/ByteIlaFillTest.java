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

package tfw.immutable.ila.byteila.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFill;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class ByteIlaFillTest extends TestCase
{
	public void testByteIlaFill()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		final byte element = (byte)random.nextInt();
	
		byte[] array = new byte[LENGTH];
	
		Arrays.fill(array, element);
		
		ByteIla ila = ByteIlaFromArray.create(array);
		
		try
		{
			ByteIlaFill.create(element, -1);
			fail("length < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = ByteIlaCheck.check(ila,
			ByteIlaFill.create(element, LENGTH));
		
		assertNull(s, s);
	}
}
