package tfw.immutable.ilm.shortilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.shortila.ShortIla;
import tfw.immutable.ila.shortila.ShortIlaFromArray;
import tfw.immutable.ilm.shortilm.ShortIlm;
import tfw.immutable.ilm.shortilm.ShortIlmFromArray;
import tfw.immutable.ilm.shortilm.ShortIlmFromShortIla;

public class ShortIlmFromShortIlaTest extends TestCase
{
	public void testShortIlaFromShortIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		short[] ilaArray = new short[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = (short)random.nextInt();
		}
		ShortIla ila = ShortIlaFromArray.create(ilaArray);
		
		short[][] ilmArray = new short[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		ShortIlm ilm = ShortIlmFromArray.create(ilmArray);

		try
		{
			ShortIlmCheck.check(ilm,
				ShortIlmFromShortIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
