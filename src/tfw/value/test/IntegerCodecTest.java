package tfw.value.test;

import tfw.value.CodecException;
import tfw.value.IntegerCodec;

import junit.framework.TestCase;


/**
 *
 */
public class IntegerCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        IntegerCodec codec = IntegerCodec.INSTANCE;

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
			fail("encode() accepted non-Integer value");
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

        Integer intValue = new Integer(-1);
        String strValue = "-1";
        assertEquals("encode() return the wrong value", strValue,
            codec.encode(intValue));

        assertEquals("decode() returned the wrong value", intValue,
            codec.decode(strValue));
    }
}
