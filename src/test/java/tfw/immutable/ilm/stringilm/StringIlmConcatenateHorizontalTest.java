package tfw.immutable.ilm.stringilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.immutable.ilm.stringilm.StringIlmConcatenateHorizontal;
import tfw.immutable.ilm.stringilm.StringIlmFromArray;

public class StringIlmConcatenateHorizontalTest extends TestCase
{
	public void testStringIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		String[][] array1 = new String[HEIGHT][WIDTH];
		String[][] array2 = new String[HEIGHT][WIDTH];
		String[][] array3 = new String[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = new String();
				array2[h][w] = new String();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		StringIlm ilm1 = StringIlmFromArray.create(array1);
		StringIlm ilm2 = StringIlmFromArray.create(array2);
		StringIlm ilm3 = StringIlmFromArray.create(array3);

		try
		{
			StringIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			StringIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			StringIlmCheck.check(ilm3,
				StringIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
