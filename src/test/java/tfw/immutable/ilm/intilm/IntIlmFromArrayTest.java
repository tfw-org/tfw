package tfw.immutable.ilm.intilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class IntIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		int[] array = new int[] {1, 2, 3, 4, 5, 6};
		IntIlm intIlm = IntIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, intIlm.toArray()));
	}
}