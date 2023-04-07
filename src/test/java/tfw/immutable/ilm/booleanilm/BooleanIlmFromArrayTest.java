package tfw.immutable.ilm.booleanilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class BooleanIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		boolean[] array = new boolean[] {true, false, true, false, true, false};
		BooleanIlm booleanIlm = BooleanIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, booleanIlm.toArray()));
	}
}