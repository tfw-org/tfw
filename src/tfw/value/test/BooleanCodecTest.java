package tfw.value.test;

import tfw.value.BooleanCodec;
import tfw.value.CodecException;

import junit.framework.TestCase;


/**
 *
 */
public class BooleanCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        BooleanCodec codec = BooleanCodec.INSTANCE;

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
            fail("encode() accepted non-Boolean value");
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

        assertEquals("encode() returned the wrong value",
            Boolean.TRUE.toString(), codec.encode(Boolean.TRUE));
        assertEquals("encode() returned the wrong value",
            Boolean.FALSE.toString(), codec.encode(Boolean.FALSE));

		assertEquals("decode() returned the wrong value", Boolean.TRUE,
			codec.decode(Boolean.TRUE.toString()));
		assertEquals("decode() returned the wrong value", Boolean.FALSE,
			codec.decode(Boolean.FALSE.toString()));
    }
}
