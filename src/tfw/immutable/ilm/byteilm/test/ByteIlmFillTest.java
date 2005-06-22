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

package tfw.immutable.ilm.byteilm.test;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.immutable.ilm.byteilm.ByteIlmFill;
import tfw.immutable.ilm.byteilm.ByteIlmFromArray;

public class ByteIlmFillTest extends TestCase
{
	public void testByteIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final byte element = (byte)random.nextInt();
	
		byte[][] array = new byte[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		ByteIlm ilm = ByteIlmFromArray.create(array);
		
		try
		{
			ByteIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			ByteIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ByteIlmTest.check(ilm,
				ByteIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
