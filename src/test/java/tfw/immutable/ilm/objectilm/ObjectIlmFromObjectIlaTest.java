package tfw.immutable.ilm.objectilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ila.objectila.ObjectIla;
import tfw.immutable.ila.objectila.ObjectIlaFromArray;
import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.immutable.ilm.objectilm.ObjectIlmFromArray;
import tfw.immutable.ilm.objectilm.ObjectIlmFromObjectIla;

public class ObjectIlmFromObjectIlaTest extends TestCase
{
	public void testObjectIlaFromObjectIla()
	{
		final Random random = new Random();
		final int WIDTH = 10;
		final int HEIGHT = 9;
	
		Object[] ilaArray = new Object[WIDTH*HEIGHT];
	
		for (int i=0 ; i < WIDTH*HEIGHT ; i++)
		{
			ilaArray[i] = new Object();
		}
		ObjectIla ila = ObjectIlaFromArray.create(ilaArray);
		
		Object[][] ilmArray = new Object[HEIGHT][WIDTH];
		
		System.arraycopy(ilaArray, WIDTH*0, ilmArray[0], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*1, ilmArray[1], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*2, ilmArray[2], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*3, ilmArray[3], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*4, ilmArray[4], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*5, ilmArray[5], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*6, ilmArray[6], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*7, ilmArray[7], 0, WIDTH);
		System.arraycopy(ilaArray, WIDTH*8, ilmArray[8], 0, WIDTH);
		
		ObjectIlm ilm = ObjectIlmFromArray.create(ilmArray);

		try
		{
			ObjectIlmCheck.check(ilm,
				ObjectIlmFromObjectIla.create(ila, WIDTH));
		}
		catch(IllegalArgumentException iae)
		{
			iae.printStackTrace();
			fail(iae.getMessage());
		}
	}
}
