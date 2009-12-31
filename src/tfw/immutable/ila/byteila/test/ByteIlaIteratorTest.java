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

package tfw.immutable.ila.byteila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaIterator;
import tfw.immutable.ila.byteila.ByteIlaFromArray;

public class ByteIlaIteratorTest extends TestCase
{
	public void testByteIlaFill()
		throws DataInvalidException
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		byte[] array = new byte[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = (byte)random.nextInt();
		}
		
		ByteIla ila = ByteIlaFromArray.create(array);
		ByteIlaIterator ii = new ByteIlaIterator(ila);

		int i=0;
		for ( ; ii.hasNext() ; i++)
		{
			if (i == array.length)
			{
				fail("Iterator did not stop correctly");
			}
			assertEquals("element "+i+" not equal!",
				ii.next(), array[i]);
		}
		
		if (i != array.length)
		{
			fail("Iterator stopped at "+i+" not "+array.length);
		}		
	}
}
