package tfw.immutable.ilm.shortilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class ShortIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		short[] array = new short[] {1, 2, 3, 4, 5, 6};
		ShortIlm shortIlm = ShortIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, shortIlm.toArray()));
	}
}