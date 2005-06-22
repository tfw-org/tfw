package tfw.value.test;

import junit.framework.TestCase;
import tfw.value.CodecException;
import tfw.value.ShortCodec;

/**
 * 
 */
public class ShortCodecTest extends TestCase {
	public void testCodec() throws Exception
	{
		ShortCodec codec = ShortCodec.INSTANCE;

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
			fail("encode() accepted non-Short value");
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
		Short shortValue = new Short((short)-1);
		String strValue = "-1";
		assertEquals("encode() return the wrong value", strValue,
			codec.encode(shortValue));

		assertEquals("decode() returned the wrong value", shortValue,
			codec.decode(strValue));

	}
}
