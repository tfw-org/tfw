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

package tfw.immutable.ilm.floatilm.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.immutable.ilm.floatilm.FloatIlmConcatenateHorizontal;
import tfw.immutable.ilm.floatilm.FloatIlmFromArray;

public class FloatIlmConcatenateHorizontalTest extends TestCase
{
	public void testFloatIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		float[][] array1 = new float[HEIGHT][WIDTH];
		float[][] array2 = new float[HEIGHT][WIDTH];
		float[][] array3 = new float[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextFloat();
				array2[h][w] = random.nextFloat();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		FloatIlm ilm1 = FloatIlmFromArray.create(array1);
		FloatIlm ilm2 = FloatIlmFromArray.create(array2);
		FloatIlm ilm3 = FloatIlmFromArray.create(array3);

		try
		{
			FloatIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			FloatIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlmCheck.check(ilm3,
				FloatIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
