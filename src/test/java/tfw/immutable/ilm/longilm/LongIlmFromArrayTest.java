package tfw.immutable.ilm.longilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class LongIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		long[] array = new long[] {1, 2, 3, 4, 5, 6};
		LongIlm longIlm = LongIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, longIlm.toArray()));
	}
}