package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=all
 */
public final class FloatIlaFill
{
    private FloatIlaFill()
    {
        // non-instantiable class
    }

    public static FloatIla create(float value, long length)
    {
        Argument.assertNotLessThan(length, 0, "length");

        return new MyFloatIla(value, length);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final float value;

        MyFloatIla(float value, long length)
        {
            super(length);
            this.value = value;
        }

        protected void toArrayImpl(float[] array, int offset,
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
                        
            map.put("name", "FloatIlaFill");
            map.put("length", new Long(length()));
            map.put("value", new Float(value));

            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
