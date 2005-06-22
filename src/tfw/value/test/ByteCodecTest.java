package tfw.value.test;

import junit.framework.TestCase;
import tfw.value.ByteCodec;
import tfw.value.CodecException;
import tfw.value.ValueCodec;

/**
 * 
 */
public class ByteCodecTest extends TestCase {
	public void testCodec() throws Exception {
		ValueCodec codec = ByteCodec.getInstance();
		try
		{
			codec.decode(null);
			fail("decode() accepted null");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}
		try
		{
			codec.encode(null);
			fail("encode() accepted null");
		}
		catch (IllegalArgumentException expected)
		{
			//System.out.println(expected);
		}
		try
		{
			codec.encode(new Object());
			fail("encode() accepted non-Byte value");
		}
		catch (CodecException expected)
		{
			//System.out.println(expected);
		}
		try
		{
			codec.decode("gibberish");
			fail("decode() accepted invalid value");
		}
		catch (CodecException expected)
		{
			//System.out.println(expected);
		}
		Byte byteValue = new Byte((byte) 7);
		String strValue = "7";
		assertEquals("encode() return the wrong value", strValue,
			codec.encode(byteValue));

		assertEquals("decode() returned the wrong value", byteValue,
			codec.decode(strValue));

	}
}
