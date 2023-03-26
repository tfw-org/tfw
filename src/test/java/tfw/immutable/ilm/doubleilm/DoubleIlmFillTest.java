package tfw.immutable.ilm.doubleilm;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmFill;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;

public class DoubleIlmFillTest extends TestCase
{
	public void testDoubleIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final double element = random.nextDouble();
	
		double[][] array = new double[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		DoubleIlm ilm = DoubleIlmFromArray.create(array);
		
		try
		{
			DoubleIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			DoubleIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			DoubleIlmCheck.check(ilm,
				DoubleIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
