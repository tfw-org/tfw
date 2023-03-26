package tfw.immutable.ila.shortila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class ShortIlaFill
{
    private ShortIlaFill()
    {
        // non-instantiable class
    }

    public static ShortIla create(short value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyShortIla(value, length);
    }

    private static class MyShortIla extends AbstractShortIla
        implements ImmutableProxy
    {
        private final short value;

        MyShortIla(short value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(short[] array, int offset,
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
                        
            map.put("name", "ShortIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Short(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
