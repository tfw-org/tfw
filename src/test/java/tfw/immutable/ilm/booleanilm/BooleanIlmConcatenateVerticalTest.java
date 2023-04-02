package tfw.immutable.ilm.booleanilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.booleanilm.BooleanIlm;
import tfw.immutable.ilm.booleanilm.BooleanIlmConcatenateVertical;
import tfw.immutable.ilm.booleanilm.BooleanIlmFromArray;

public class BooleanIlmConcatenateVerticalTest extends TestCase
{
	public void testBooleanIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		boolean[][] array1 = new boolean[HEIGHT][WIDTH];
		boolean[][] array2 = new boolean[HEIGHT][WIDTH];
		boolean[][] array3 = new boolean[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = random.nextBoolean();
				array2[h][w] = random.nextBoolean();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		BooleanIlm ilm1 = BooleanIlmFromArray.create(array1);
		BooleanIlm ilm2 = BooleanIlmFromArray.create(array2);
		BooleanIlm ilm3 = BooleanIlmFromArray.create(array3);

		try
		{
			BooleanIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			BooleanIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			BooleanIlmCheck.check(ilm3,
				BooleanIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}