package tfw.immutable.ilm.shortilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.shortilm.ShortIlm;
import tfw.immutable.ilm.shortilm.ShortIlmConcatenateVertical;
import tfw.immutable.ilm.shortilm.ShortIlmFromArray;

public class ShortIlmConcatenateVerticalTest extends TestCase
{
	public void testShortIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		short[][] array1 = new short[HEIGHT][WIDTH];
		short[][] array2 = new short[HEIGHT][WIDTH];
		short[][] array3 = new short[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = (short)random.nextInt();
				array2[h][w] = (short)random.nextInt();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		ShortIlm ilm1 = ShortIlmFromArray.create(array1);
		ShortIlm ilm2 = ShortIlmFromArray.create(array2);
		ShortIlm ilm3 = ShortIlmFromArray.create(array3);

		try
		{
			ShortIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			ShortIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ShortIlmCheck.check(ilm3,
				ShortIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
