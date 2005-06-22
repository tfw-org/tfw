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

package tfw.immutable.ilm.doubleilm.test;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmConcatenateVertical;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;

public class DoubleIlmConcatenateVerticalTest extends TestCase
{
	public void testDoubleIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		double[][] array1 = new double[HEIGHT][WIDTH];
		double[][] array2 = new double[HEIGHT][WIDTH];
		double[][] array3 = new double[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextDouble();
				array2[h][w] = random.nextDouble();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		DoubleIlm ilm1 = DoubleIlmFromArray.create(array1);
		DoubleIlm ilm2 = DoubleIlmFromArray.create(array2);
		DoubleIlm ilm3 = DoubleIlmFromArray.create(array3);

		try
		{
			DoubleIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			DoubleIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			DoubleIlmTest.check(ilm3,
				DoubleIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
