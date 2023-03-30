package tfw.immutable.ilm.floatilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.immutable.ilm.floatilm.FloatIlmConcatenateVertical;
import tfw.immutable.ilm.floatilm.FloatIlmFromArray;

public class FloatIlmConcatenateVerticalTest extends TestCase
{
	public void testFloatIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		float[][] array1 = new float[HEIGHT][WIDTH];
		float[][] array2 = new float[HEIGHT][WIDTH];
		float[][] array3 = new float[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextFloat();
				array2[h][w] = random.nextFloat();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		FloatIlm ilm1 = FloatIlmFromArray.create(array1);
		FloatIlm ilm2 = FloatIlmFromArray.create(array2);
		FloatIlm ilm3 = FloatIlmFromArray.create(array3);

		try
		{
			FloatIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			FloatIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlmCheck.check(ilm3,
				FloatIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
