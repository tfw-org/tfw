package tfw.immutable.ilm.doubleilm;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromIncrementDoubleIla;

public class DoubleIlmFromIncrementDoubleIlaTest extends TestCase
{
	public void testDoubleIlmFromIncrementDoubleIla()
	{
		final Random random = new Random();
		final int WIDTH = 20;
		final BigDecimal ROW_INCREMENT = new BigDecimal(2.3);
		final int HEIGHT = 9;
	
		double[] ilaArray = new double[WIDTH];
	
		for (int i=0 ; i < WIDTH ; i++)
		{
			ilaArray[i] = random.nextDouble();
		}
		DoubleIla ila = DoubleIlaFromArray.create(ilaArray);
		
		double[][] ilmArray = new double[HEIGHT][WIDTH];
		
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(ilmArray[i], 0.0);
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
		
		DoubleIlm ilm = DoubleIlmFromArray.create(ilmArray);
		
		try
		{
			DoubleIlmCheck.check(ilm,
				DoubleIlmFromIncrementDoubleIla.create(
				ila, ROW_INCREMENT, 0.0));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
