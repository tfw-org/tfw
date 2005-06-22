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

package tfw.immutable.ilm.intilm.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.immutable.ilm.intilm.IntIlmConcatenateVertical;
import tfw.immutable.ilm.intilm.IntIlmFromArray;

public class IntIlmConcatenateVerticalTest extends TestCase
{
	public void testIntIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		int[][] array1 = new int[HEIGHT][WIDTH];
		int[][] array2 = new int[HEIGHT][WIDTH];
		int[][] array3 = new int[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextInt();
				array2[h][w] = random.nextInt();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		IntIlm ilm1 = IntIlmFromArray.create(array1);
		IntIlm ilm2 = IntIlmFromArray.create(array2);
		IntIlm ilm3 = IntIlmFromArray.create(array3);

		try
		{
			IntIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			IntIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			IntIlmTest.check(ilm3,
				IntIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
