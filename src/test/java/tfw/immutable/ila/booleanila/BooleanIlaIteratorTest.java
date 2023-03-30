package tfw.immutable.ila.booleanila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.booleanila.BooleanIla;
import tfw.immutable.ila.booleanila.BooleanIlaIterator;
import tfw.immutable.ila.booleanila.BooleanIlaFromArray;

public class BooleanIlaIteratorTest extends TestCase
{
	public void testBooleanIlaFill()
		throws DataInvalidException
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		boolean[] array = new boolean[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextBoolean();
		}
		
		BooleanIla ila = BooleanIlaFromArray.create(array);
		BooleanIlaIterator ii = new BooleanIlaIterator(ila);

		int i=0;
		for ( ; ii.hasNext() ; i++)
		{
			if (i == array.length)
			{
				fail("Iterator did not stop correctly");
			}
			assertEquals("element "+i+" not equal!",
				ii.next(), array[i]);
		}
		
		if (i != array.length)
		{
			fail("Iterator stopped at "+i+" not "+array.length);
		}		
	}
}
