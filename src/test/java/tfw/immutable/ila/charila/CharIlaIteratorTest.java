package tfw.immutable.ila.charila;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ila.charila.CharIla;
import tfw.immutable.ila.charila.CharIlaIterator;
import tfw.immutable.ila.charila.CharIlaFromArray;

public class CharIlaIteratorTest extends TestCase
{
	public void testCharIlaFill()
		throws DataInvalidException
	{
		final Random random = new Random();
		final int LENGTH = 29;	
		char[] array = new char[LENGTH];
	
		for(int i=0 ; i < array.length ; i++)
		{
			array[i] = (char)random.nextInt();
		}
		
		CharIla ila = CharIlaFromArray.create(array);
		CharIlaIterator ii = new CharIlaIterator(ila);

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
