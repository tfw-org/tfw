package tfw.value.test;

import tfw.value.CodecException;
import tfw.value.LongCodec;

import junit.framework.TestCase;


/**
 *
 */
public class LongCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        LongCodec codec = LongCodec.INSTANCE;

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
            fail("encode() accepted non-Long value");
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

        Long longValue = new Long(-1);
        String strValue = "-1";
        assertEquals("encode() return the wrong value", strValue,
            codec.encode(longValue));

        assertEquals("decode() returned the wrong value", longValue,
            codec.decode(strValue));
    }
}
