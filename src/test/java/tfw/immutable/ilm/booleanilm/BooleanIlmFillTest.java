package tfw.immutable.ilm.booleanilm;

import java.util.Arrays;
import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.booleanilm.BooleanIlm;
import tfw.immutable.ilm.booleanilm.BooleanIlmFill;
import tfw.immutable.ilm.booleanilm.BooleanIlmFromArray;

public class BooleanIlmFillTest extends TestCase
{
	public void testBooleanIlaFill()
	{
		final Random random = new Random();
		final int WIDTH = 29;
		final int HEIGHT = 27;
		final boolean element = random.nextBoolean();
	
		boolean[][] array = new boolean[HEIGHT][WIDTH];
	
		for (int i=0 ; i < HEIGHT ; i++)
		{
			Arrays.fill(array[i], element);
		}
		
		BooleanIlm ilm = BooleanIlmFromArray.create(array);
		
		try
		{
			BooleanIlmFill.create(element, -1, 0);
			fail("width < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			BooleanIlmFill.create(element, 0, -1);
			fail("height < 0 not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			BooleanIlmCheck.check(ilm,
				BooleanIlmFill.create(element, WIDTH, HEIGHT));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
