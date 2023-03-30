package tfw.immutable.ilm.stringilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.stringila.StringIla;
import tfw.immutable.ila.stringila.StringIlaFromArray;
import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.immutable.ilm.stringilm.StringIlmFromArray;
import tfw.immutable.ilm.stringilm.StringIlmFromIncrementStringIla;

public class StringIlmFromIncrementStringIlaTest extends TestCase
{
	public void testStringIlmFromIncrementStringIla()
	{
		final Random random = new Random();
		final int WIDTH = 20;
		final BigDecimal ROW_INCREMENT = new BigDecimal(2.3);
		final int HEIGHT = 9;
	
		String[] ilaArray = new String[WIDTH];
	
		for (int i=0 ; i < WIDTH ; i++)
		{
			ilaArray[i] = new String();
		}
		StringIla ila = StringIlaFromArray.create(ilaArray);
		
		String[][] ilmArray = new String[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(ilmArray[i], "");
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
		
		StringIlm ilm = StringIlmFromArray.create(ilmArray);
		
		try
		{
			StringIlmCheck.check(ilm,
				StringIlmFromIncrementStringIla.create(
				ila, ROW_INCREMENT, ""));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
