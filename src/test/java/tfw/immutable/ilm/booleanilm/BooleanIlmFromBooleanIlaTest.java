package tfw.immutable.ilm.booleanilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;
import tfw.immutable.ilm.booleanilm.BooleanIlm;
import tfw.immutable.ilm.booleanilm.BooleanIlmFromArray;
import tfw.immutable.ilm.booleanilm.BooleanIlmFromBooleanIla;

public class BooleanIlmFromBooleanIlaTest extends TestCase
{
	public void testBooleanIlaFromBooleanIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		boolean[] ilaArray = new boolean[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = random.nextBoolean();
		}
		BooleanIla ila = BooleanIlaFromArray.create(ilaArray);
		
		boolean[][] ilmArray = new boolean[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		BooleanIlm ilm = BooleanIlmFromArray.create(ilmArray);

		try
		{
			BooleanIlmCheck.check(ilm,
				BooleanIlmFromBooleanIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
