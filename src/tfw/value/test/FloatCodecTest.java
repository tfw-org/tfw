package tfw.value.test;

import junit.framework.TestCase;
import tfw.value.CodecException;
import tfw.value.FloatCodec;
import tfw.value.ValueCodec;

/**
 * 
 */
public class FloatCodecTest extends TestCase {
	public void testCodec() throws Exception {
		ValueCodec codec = FloatCodec.getInstance();
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
			fail("encode() accepted non-Float value");
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
		Float floatValue = new Float(-1);
		String strValue = "-1.0";
		assertEquals("encode() return the wrong value", strValue,
			codec.encode(floatValue));

		assertEquals("decode() returned the wrong value", floatValue,
			codec.decode(strValue));
	}
}
