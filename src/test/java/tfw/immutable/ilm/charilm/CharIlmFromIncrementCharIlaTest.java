package tfw.immutable.ilm.charilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaFromArray;
import tfw.immutable.ilm.charilm.CharIlm;
import tfw.immutable.ilm.charilm.CharIlmFromArray;
import tfw.immutable.ilm.charilm.CharIlmFromIncrementCharIla;

public class CharIlmFromIncrementCharIlaTest extends TestCase
{
	public void testCharIlmFromIncrementCharIla()
	{
		final Random random = new Random();
		final int WIDTH = 20;
		final BigDecimal ROW_INCREMENT = new BigDecimal(2.3);
		final int HEIGHT = 9;
	
		char[] ilaArray = new char[WIDTH];
	
		for (int i=0 ; i < WIDTH ; i++)
		{
			ilaArray[i] = (char)random.nextInt();
		}
		CharIla ila = CharIlaFromArray.create(ilaArray);
		
		char[][] ilmArray = new char[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(ilmArray[i], (char)0);
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
		
		CharIlm ilm = CharIlmFromArray.create(ilmArray);
		
		try
		{
			CharIlmCheck.check(ilm,
				CharIlmFromIncrementCharIla.create(
				ila, ROW_INCREMENT, (char)0));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
