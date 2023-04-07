package tfw.immutable.ilm.byteilm;

import java.util.Arrays;
import junit.framework.TestCase;

public class ByteIlmFromArrayTest extends TestCase
{
	public void testBooleanIlmFromArray() throws Exception
	{
		byte[] array = new byte[] {1, 2, 3, 4, 5, 6};
		ByteIlm byteIlm = ByteIlmFromArray.create(array, 3);
		
		assertTrue(Arrays.equals(array, byteIlm.toArray()));
	}
}