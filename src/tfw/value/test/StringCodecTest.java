package tfw.value.test;

import tfw.value.CodecException;
import tfw.value.StringCodec;

import junit.framework.TestCase;


/**
 *
 */
public class StringCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        StringCodec codec = StringCodec.INSTANCE;

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
            fail("encode() accepted non-String value");
        }
        catch (CodecException expected)
        {
            //System.out.println(expected);
        }

        String testString = "Hello World";

		assertEquals("encode() returned the wrong value", testString,
			codec.encode(testString));
		assertEquals("decode() returned the wrong value", testString,
			codec.decode(testString));
    }
}
