package tfw.immutable.ila.booleanila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class BooleanIlaFill
{
    private BooleanIlaFill()
    {
        // non-instantiable class
    }

    public static BooleanIla create(boolean value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyBooleanIla(value, length);
    }

    private static class MyBooleanIla extends AbstractBooleanIla
        implements ImmutableProxy
    {
        private final boolean value;

        MyBooleanIla(boolean value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(boolean[] array, int offset,
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
                        
            map.put("name", "BooleanIlaFill");
            map.put("length", new Long(length()));
            map.put("value", (value? Boolean.TRUE : Boolean.FALSE));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
