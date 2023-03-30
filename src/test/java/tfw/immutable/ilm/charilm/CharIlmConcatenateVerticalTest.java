package tfw.immutable.ilm.charilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.charilm.CharIlm;
import tfw.immutable.ilm.charilm.CharIlmConcatenateVertical;
import tfw.immutable.ilm.charilm.CharIlmFromArray;

public class CharIlmConcatenateVerticalTest extends TestCase
{
	public void testCharIlaConcatenateVertical()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		char[][] array1 = new char[HEIGHT][WIDTH];
		char[][] array2 = new char[HEIGHT][WIDTH];
		char[][] array3 = new char[HEIGHT*2][WIDTH];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = (char)random.nextInt();
				array2[h][w] = (char)random.nextInt();
				array3[h][w] = array1[h][w];
				array3[HEIGHT+h][w] = array2[h][w];
			}
		}
	
		CharIlm ilm1 = CharIlmFromArray.create(array1);
		CharIlm ilm2 = CharIlmFromArray.create(array2);
		CharIlm ilm3 = CharIlmFromArray.create(array3);

		try
		{
			CharIlmConcatenateVertical.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			CharIlmConcatenateVertical.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			CharIlmCheck.check(ilm3,
				CharIlmConcatenateVertical.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
