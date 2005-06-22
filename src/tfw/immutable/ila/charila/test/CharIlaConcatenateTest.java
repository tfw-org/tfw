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

package tfw.immutable.ila.charila.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaConcatenate;
import tfw.immutable.ila.charila.CharIlaFromArray;

public class CharIlaConcatenateTest extends TestCase
{
	public void testCharIlaConcatenate()
	{
		final Random random = new Random();
		final int LENGTH1 = 29;
		final int LENGTH2 = 17;
		
		char[] array1 = new char[LENGTH1];
		char[] array2 = new char[LENGTH2];
		
		for (int i=0 ; i < LENGTH1 ; i++)
		{
			array1[i] = (char)random.nextInt();
		}
		for (int i=0 ; i < LENGTH2 ; i++)
		{
			array2[i] = (char)random.nextInt();
		}
		
		char[] array3 = new char[LENGTH1 + LENGTH2];
		
		System.arraycopy(array1, 0, array3, 0, LENGTH1);
		System.arraycopy(array2, 0, array3, LENGTH1, LENGTH2);
		
		CharIla ila1 = CharIlaFromArray.create(array1);
		CharIla ila2 = CharIlaFromArray.create(array2);
		CharIla ila3 = CharIlaFromArray.create(array3);
		
		try
		{
			CharIlaConcatenate.create(null, ila2);
			fail("firstIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			CharIlaConcatenate.create(ila1, null);
			fail("secondIla == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		String s = CharIlaTest.check(ila3,
			CharIlaConcatenate.create(ila1, ila2));
		
		assertNull(s, s);
	}
}