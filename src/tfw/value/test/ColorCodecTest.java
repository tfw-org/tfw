package tfw.value.test;

import tfw.value.CodecException;
import tfw.value.ColorCodec;

import junit.framework.TestCase;

import java.awt.Color;


/**
 *
 */
public class ColorCodecTest extends TestCase
{
    public void testCodec() throws Exception
    {
        ColorCodec codec = ColorCodec.INSTANCE;

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
            fail("encode() accepted non-Color value");
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

        Color colorValue = new Color(125, 0, 222);
        String stringValue = "" + colorValue.getRGB();
		assertEquals("encode() returned the wrong value", stringValue,
			codec.encode(colorValue));
		assertEquals("decode() returned the wrong value", colorValue,
			codec.decode(stringValue));
    }
}
