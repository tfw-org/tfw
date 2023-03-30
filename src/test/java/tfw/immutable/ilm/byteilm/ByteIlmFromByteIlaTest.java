package tfw.immutable.ilm.byteilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.byteila.ByteIla;
import tfw.immutable.ila.byteila.ByteIlaFromArray;
import tfw.immutable.ilm.byteilm.ByteIlm;
import tfw.immutable.ilm.byteilm.ByteIlmFromArray;
import tfw.immutable.ilm.byteilm.ByteIlmFromByteIla;

public class ByteIlmFromByteIlaTest extends TestCase
{
	public void testByteIlaFromByteIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		byte[] ilaArray = new byte[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = (byte)random.nextInt();
		}
		ByteIla ila = ByteIlaFromArray.create(ilaArray);
		
		byte[][] ilmArray = new byte[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		ByteIlm ilm = ByteIlmFromArray.create(ilmArray);

		try
		{
			ByteIlmCheck.check(ilm,
				ByteIlmFromByteIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
