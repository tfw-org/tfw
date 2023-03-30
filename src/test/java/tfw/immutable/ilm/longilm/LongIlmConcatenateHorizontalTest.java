package tfw.immutable.ilm.longilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.longilm.LongIlm;
import tfw.immutable.ilm.longilm.LongIlmConcatenateHorizontal;
import tfw.immutable.ilm.longilm.LongIlmFromArray;

public class LongIlmConcatenateHorizontalTest extends TestCase
{
	public void testLongIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		long[][] array1 = new long[HEIGHT][WIDTH];
		long[][] array2 = new long[HEIGHT][WIDTH];
		long[][] array3 = new long[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextLong();
				array2[h][w] = random.nextLong();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		LongIlm ilm1 = LongIlmFromArray.create(array1);
		LongIlm ilm2 = LongIlmFromArray.create(array2);
		LongIlm ilm3 = LongIlmFromArray.create(array3);

		try
		{
			LongIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			LongIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			LongIlmCheck.check(ilm3,
				LongIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
