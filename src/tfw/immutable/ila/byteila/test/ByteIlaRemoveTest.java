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

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ila.byteila.ByteIlaRemove;

public class ByteIlaRemoveTest extends TestCase
{
	public void testByteIlaRemove()
	{
		final Random random = new Random();
		final int LENGTH = 29;
		
		byte[] array = new byte[LENGTH];
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			array[0] = (byte)random.nextInt();
		}
		
		ByteIla ila = ByteIlaFromArray.create(array);
		
		try
		{
			ByteIlaRemove.create(null, 0);
			fail("ila == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ByteIlaRemove.create(ila, -1);
			fail("index < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ByteIlaRemove.create(ila, LENGTH);
			fail("index >= ila.length not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		for (int i=0 ; i < LENGTH ; i++)
		{
			byte[] a = new byte[LENGTH - 1];
			
			System.arraycopy(array, 0, a, 0, i);
			System.arraycopy(array, i + 1, a, i, LENGTH - i - 1);
			
			ByteIla ia = ByteIlaFromArray.create(a);
			
			String s = ByteIlaTest.check(ia,
				ByteIlaRemove.create(ila, i));
			
			assertNull(s, s);
		}
	}
}