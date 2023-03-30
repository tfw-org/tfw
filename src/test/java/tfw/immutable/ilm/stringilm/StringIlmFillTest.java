package tfw.immutable.ilm.stringilm;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.stringilm.StringIlm;
import tfw.immutable.ilm.stringilm.StringIlmFill;
import tfw.immutable.ilm.stringilm.StringIlmFromArray;

public class StringIlmFillTest extends TestCase
{
	public void testStringIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final String element = new String();
	
		String[][] array = new String[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		StringIlm ilm = StringIlmFromArray.create(array);
		
		try
		{
			StringIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			StringIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			StringIlmCheck.check(ilm,
				StringIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
