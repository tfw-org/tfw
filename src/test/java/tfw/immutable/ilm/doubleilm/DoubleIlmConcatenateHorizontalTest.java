package tfw.immutable.ilm.doubleilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmConcatenateHorizontal;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;

public class DoubleIlmConcatenateHorizontalTest extends TestCase
{
	public void testDoubleIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		double[][] array1 = new double[HEIGHT][WIDTH];
		double[][] array2 = new double[HEIGHT][WIDTH];
		double[][] array3 = new double[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextDouble();
				array2[h][w] = random.nextDouble();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		DoubleIlm ilm1 = DoubleIlmFromArray.create(array1);
		DoubleIlm ilm2 = DoubleIlmFromArray.create(array2);
		DoubleIlm ilm3 = DoubleIlmFromArray.create(array3);

		try
		{
			DoubleIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			DoubleIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			DoubleIlmCheck.check(ilm3,
				DoubleIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}