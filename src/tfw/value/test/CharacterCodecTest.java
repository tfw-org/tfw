package tfw.value.test;

import tfw.value.CharacterCodec;
import tfw.value.CodecException;

import junit.framework.TestCase;


/**
 *
 */
public class CharacterCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        CharacterCodec codec = CharacterCodec.INSTANCE;

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
            fail("encode() accepted non-Character value");
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

        Character charValue = new Character('z');
        String stringValue = "z";

		assertEquals("encode() returned the wrong value", stringValue,
			codec.encode(charValue));
		assertEquals("decode() returned the wrong value", charValue,
			codec.decode(stringValue));
    }
}
