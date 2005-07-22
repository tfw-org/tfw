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

package tfw.immutable.ilm.stringilm.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.immutable.ilm.stringilm.StringIlmConcatenateVertical;
import tfw.immutable.ilm.stringilm.StringIlmFromArray;

public class StringIlmConcatenateVerticalTest extends TestCase
{
	public void testStringIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		String[][] array1 = new String[HEIGHT][WIDTH];
		String[][] array2 = new String[HEIGHT][WIDTH];
		String[][] array3 = new String[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = new String();
				array2[h][w] = new String();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		StringIlm ilm1 = StringIlmFromArray.create(array1);
		StringIlm ilm2 = StringIlmFromArray.create(array2);
		StringIlm ilm3 = StringIlmFromArray.create(array3);

		try
		{
			StringIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			StringIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			StringIlmTest.check(ilm3,
				StringIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
