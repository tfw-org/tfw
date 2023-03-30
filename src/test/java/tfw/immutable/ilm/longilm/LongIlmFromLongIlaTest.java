package tfw.immutable.ilm.longilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ilm.longilm.LongIlm;
import tfw.immutable.ilm.longilm.LongIlmFromArray;
import tfw.immutable.ilm.longilm.LongIlmFromLongIla;

public class LongIlmFromLongIlaTest extends TestCase
{
	public void testLongIlaFromLongIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		long[] ilaArray = new long[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = random.nextLong();
		}
		LongIla ila = LongIlaFromArray.create(ilaArray);
		
		long[][] ilmArray = new long[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		LongIlm ilm = LongIlmFromArray.create(ilmArray);

		try
		{
			LongIlmCheck.check(ilm,
				LongIlmFromLongIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
