package tfw.immutable.ilm.intilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaFromArray;
import tfw.immutable.ilm.intilm.IntIlm;
import tfw.immutable.ilm.intilm.IntIlmFromArray;
import tfw.immutable.ilm.intilm.IntIlmFromIncrementIntIla;

public class IntIlmFromIncrementIntIlaTest extends TestCase
{
	public void testIntIlmFromIncrementIntIla()
	{
		final Random random = new Random();
		final int WIDTH = 20;
		final BigDecimal ROW_INCREMENT = new BigDecimal(2.3);
		final int HEIGHT = 9;
	
		int[] ilaArray = new int[WIDTH];
	
		for (int i=0 ; i < WIDTH ; i++)
		{
			ilaArray[i] = random.nextInt();
		}
		IntIla ila = IntIlaFromArray.create(ilaArray);
		
		int[][] ilmArray = new int[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(ilmArray[i], 0);
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
		
		IntIlm ilm = IntIlmFromArray.create(ilmArray);
		
		try
		{
			IntIlmCheck.check(ilm,
				IntIlmFromIncrementIntIla.create(
				ila, ROW_INCREMENT, 0));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
