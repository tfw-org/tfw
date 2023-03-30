package tfw.immutable.ilm.longilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.longila.LongIla;
import tfw.immutable.ila.longila.LongIlaFromArray;
import tfw.immutable.ilm.longilm.LongIlm;
import tfw.immutable.ilm.longilm.LongIlmFromArray;
import tfw.immutable.ilm.longilm.LongIlmFromIncrementLongIla;

public class LongIlmFromIncrementLongIlaTest extends TestCase
{
	public void testLongIlmFromIncrementLongIla()
	{
		final Random random = new Random();
		final int WIDTH = 20;
		final BigDecimal ROW_INCREMENT = new BigDecimal(2.3);
		final int HEIGHT = 9;
	
		long[] ilaArray = new long[WIDTH];
	
		for (int i=0 ; i < WIDTH ; i++)
		{
			ilaArray[i] = random.nextLong();
		}
		LongIla ila = LongIlaFromArray.create(ilaArray);
		
		long[][] ilmArray = new long[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(ilmArray[i], 0L);
		}
		
		System.arraycopy(ilaArray,  0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray,  2, ilmArray[1], 0, WIDTH -  2);
		System.arraycopy(ilaArray,  4, ilmArray[2], 0, WIDTH -  4);
		System.arraycopy(ilaArray,  6, ilmArray[3], 0, WIDTH -  6);
		System.arraycopy(ilaArray,  9, ilmArray[4], 0, WIDTH -  9);
		System.arraycopy(ilaArray, 11, ilmArray[5], 0, WIDTH - 11);
		System.arraycopy(ilaArray, 13, ilmArray[6], 0, WIDTH - 13);
		System.arraycopy(ilaArray, 16, ilmArray[7], 0, WIDTH - 16);
		System.arraycopy(ilaArray, 18, ilmArray[8], 0, WIDTH - 18);
		
		LongIlm ilm = LongIlmFromArray.create(ilmArray);
		
		try
		{
			LongIlmCheck.check(ilm,
				LongIlmFromIncrementLongIla.create(
				ila, ROW_INCREMENT, 0L));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
