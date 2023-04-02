package tfw.immutable.ilm.byteilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.immutable.ilm.byteilm.ByteIlmConcatenateHorizontal;
import tfw.immutable.ilm.byteilm.ByteIlmFromArray;

public class ByteIlmConcatenateHorizontalTest extends TestCase
{
	public void testByteIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		byte[][] array1 = new byte[HEIGHT][WIDTH];
		byte[][] array2 = new byte[HEIGHT][WIDTH];
		byte[][] array3 = new byte[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = (byte)random.nextInt();
				array2[h][w] = (byte)random.nextInt();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		ByteIlm ilm1 = ByteIlmFromArray.create(array1);
		ByteIlm ilm2 = ByteIlmFromArray.create(array2);
		ByteIlm ilm3 = ByteIlmFromArray.create(array3);

		try
		{
			ByteIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			ByteIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ByteIlmCheck.check(ilm3,
				ByteIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}