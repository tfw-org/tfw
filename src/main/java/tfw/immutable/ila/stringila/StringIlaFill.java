package tfw.immutable.ila.stringila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class StringIlaFill
{
    private StringIlaFill()
    {
        // non-instantiable class
    }

    public static StringIla create(String value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyStringIla(value, length);
    }

    private static class MyStringIla extends AbstractStringIla
        implements ImmutableProxy
    {
        private final String value;

        MyStringIla(String value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(String[] array, int offset,
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
                        
            map.put("name", "StringIlaFill");
            map.put("length", new Long(length()));
            map.put("value", value);

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
