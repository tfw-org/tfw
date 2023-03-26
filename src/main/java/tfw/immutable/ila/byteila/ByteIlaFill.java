package tfw.immutable.ila.byteila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class ByteIlaFill
{
    private ByteIlaFill()
    {
        // non-instantiable class
    }

    public static ByteIla create(byte value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyByteIla(value, length);
    }

    private static class MyByteIla extends AbstractByteIla
        implements ImmutableProxy
    {
        private final byte value;

        MyByteIla(byte value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(byte[] array, int offset,
                                   int stride, long start, int length)
        {
            final int startPlusLength = (int) (start + length);
            for(int startInt = (int) start;
                startInt != startPlusLength;
                ++startInt, offset += stride)
            {
                array[offset] = value;
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "ByteIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Byte(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
