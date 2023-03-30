package tfw.immutable.ilm.doubleilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.doubleila.DoubleIla;
import tfw.immutable.ila.doubleila.DoubleIlaFromArray;
import tfw.immutable.ilm.doubleilm.DoubleIlm;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromArray;
import tfw.immutable.ilm.doubleilm.DoubleIlmFromDoubleIla;

public class DoubleIlmFromDoubleIlaTest extends TestCase
{
	public void testDoubleIlaFromDoubleIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		double[] ilaArray = new double[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = random.nextDouble();
		}
		DoubleIla ila = DoubleIlaFromArray.create(ilaArray);
		
		double[][] ilmArray = new double[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		DoubleIlm ilm = DoubleIlmFromArray.create(ilmArray);

		try
		{
			DoubleIlmCheck.check(ilm,
				DoubleIlmFromDoubleIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
