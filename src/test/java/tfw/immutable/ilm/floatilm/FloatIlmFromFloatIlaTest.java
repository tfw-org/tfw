package tfw.immutable.ilm.floatilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaFromArray;
import tfw.immutable.ilm.floatilm.FloatIlm;
import tfw.immutable.ilm.floatilm.FloatIlmFromArray;
import tfw.immutable.ilm.floatilm.FloatIlmFromFloatIla;

public class FloatIlmFromFloatIlaTest extends TestCase
{
	public void testFloatIlaFromFloatIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		float[] ilaArray = new float[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = random.nextFloat();
		}
		FloatIla ila = FloatIlaFromArray.create(ilaArray);
		
		float[][] ilmArray = new float[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		FloatIlm ilm = FloatIlmFromArray.create(ilmArray);

		try
		{
			FloatIlmCheck.check(ilm,
				FloatIlmFromFloatIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
