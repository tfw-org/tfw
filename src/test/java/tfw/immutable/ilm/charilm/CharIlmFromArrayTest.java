package tfw.immutable.ilm.charilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class CharIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		char[] array = new char[] {1, 2, 3, 4, 5, 6};
		CharIlm charIlm = CharIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, charIlm.toArray()));
	}
}