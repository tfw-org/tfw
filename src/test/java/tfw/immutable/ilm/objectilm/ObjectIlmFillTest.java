package tfw.immutable.ilm.objectilm;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.immutable.ilm.objectilm.ObjectIlmFill;
import tfw.immutable.ilm.objectilm.ObjectIlmFromArray;

public class ObjectIlmFillTest extends TestCase
{
	public void testObjectIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final Object element = new Object();
	
		Object[][] array = new Object[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		ObjectIlm ilm = ObjectIlmFromArray.create(array);
		
		try
		{
			ObjectIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			ObjectIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ObjectIlmCheck.check(ilm,
				ObjectIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
