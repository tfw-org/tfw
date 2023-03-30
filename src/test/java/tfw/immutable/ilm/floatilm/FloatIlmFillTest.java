package tfw.immutable.ilm.floatilm;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.immutable.ilm.floatilm.FloatIlmFill;
import tfw.immutable.ilm.floatilm.FloatIlmFromArray;

public class FloatIlmFillTest extends TestCase
{
	public void testFloatIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final float element = random.nextFloat();
	
		float[][] array = new float[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		FloatIlm ilm = FloatIlmFromArray.create(array);
		
		try
		{
			FloatIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			FloatIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			FloatIlmCheck.check(ilm,
				FloatIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
