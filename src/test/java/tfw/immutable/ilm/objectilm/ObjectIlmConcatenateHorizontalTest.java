package tfw.immutable.ilm.objectilm;

import java.util.Random;
import junit.framework.TestCase;
import tfw.immutable.ilm.objectilm.ObjectIlm;
import tfw.immutable.ilm.objectilm.ObjectIlmConcatenateHorizontal;
import tfw.immutable.ilm.objectilm.ObjectIlmFromArray;

public class ObjectIlmConcatenateHorizontalTest extends TestCase
{
	public void testObjectIlaConcatenateHorizontal()
	{
		final Random random = new Random();
		final int WIDTH = 5;
		final int HEIGHT = 7;

		Object[][] array1 = new Object[HEIGHT][WIDTH];
		Object[][] array2 = new Object[HEIGHT][WIDTH];
		Object[][] array3 = new Object[HEIGHT][WIDTH*2];
		
		for (int h=0 ; h < HEIGHT ; h++)
		{
			for (int w=0 ; w < WIDTH ; w++)
			{
				array1[h][w] = new Object();
				array2[h][w] = new Object();
				array3[h][w] = array1[h][w];
				array3[h][WIDTH+w] = array2[h][w];
			}
		}
	
		ObjectIlm ilm1 = ObjectIlmFromArray.create(array1);
		ObjectIlm ilm2 = ObjectIlmFromArray.create(array2);
		ObjectIlm ilm3 = ObjectIlmFromArray.create(array3);

		try
		{
			ObjectIlmConcatenateHorizontal.create(null, ilm2);
			fail("ilm2 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		try
		{
			ObjectIlmConcatenateHorizontal.create(ilm1, null);
			fail("ilm1 == null not checked for!");
		}
		catch (IllegalArgumentException iae) {}
		
		try
		{
			ObjectIlmCheck.check(ilm3,
				ObjectIlmConcatenateHorizontal.create(ilm1, ilm2));
		}
		catch(IllegalArgumentException iae)
		{
			fail(iae.getMessage());
		}
	}
}
