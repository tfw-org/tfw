package tfw.immutable.ila.intila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.intila.IntIla;
import tfw.immutable.ila.intila.IntIlaIterator;
import tfw.immutable.ila.intila.IntIlaFromArray;

public class IntIlaIteratorTest extends TestCase
{
	public void testIntIlaFill()
		throws DataInvalidException
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		int[] array = new int[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = random.nextInt();
		}
		
		IntIla ila = IntIlaFromArray.create(array);
		IntIlaIterator ii = new IntIlaIterator(ila);

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
