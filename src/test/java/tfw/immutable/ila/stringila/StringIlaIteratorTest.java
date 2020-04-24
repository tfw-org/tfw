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

package tfw.immutable.ila.stringila;

import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ila.stringila.StringIlaIterator;

public class StringIlaIteratorTest extends TestCase
{
	public void testStringIlaFill()
		throws DataInvalidException
	{
		final int LENGTH = 29;	
		String[] array = new String[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = new String();
		}
		
		StringIla ila = StringIlaFromArray.create(array);
		StringIlaIterator ii = new StringIlaIterator(ila);

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
