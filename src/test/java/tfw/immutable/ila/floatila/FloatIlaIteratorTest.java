package tfw.immutable.ila.floatila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.floatila.FloatIla;
import tfw.immutable.ila.floatila.FloatIlaIterator;
import tfw.immutable.ila.floatila.FloatIlaFromArray;

public class FloatIlaIteratorTest extends TestCase
{
	public void testFloatIlaFill()
		throws DataInvalidException
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		float[] array = new float[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextFloat();
		}
		
		FloatIla ila = FloatIlaFromArray.create(array);
		FloatIlaIterator ii = new FloatIlaIterator(ila);

		int i=0;
		for ( ; ii.hasNext() ; i++)
		{
			if (i == array.length)
			{
				fail("Iterator did not stop correctly");
			}
			assertEquals("element "+i+" not equal!",
				ii.next(), array[i], 0f);
		}
		
		if (i != array.length)
		{
			fail("Iterator stopped at "+i+" not "+array.length);
		}		
	}
}
