package tfw.value.test;

import junit.framework.TestCase;
import tfw.value.CodecException;
import tfw.value.DoubleCodec;
import tfw.value.ValueCodec;

/**
 * 
 */
public class DoubleCodecTest extends TestCase {
	public void testCodec()throws Exception {
		ValueCodec codec = DoubleCodec.getInstance();
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
			fail("encode() accepted non-Double value");
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
		Double doubleValue = new Double(-1);
		String strValue = "-1.0";
		assertEquals("encode() return the wrong value", strValue,
			codec.encode(doubleValue));

		assertEquals("decode() returned the wrong value", doubleValue,
			codec.decode(strValue));
	}
}
