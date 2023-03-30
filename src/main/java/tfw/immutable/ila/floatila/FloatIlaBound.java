package tfw.immutable.ila.floatila;

import java.util.HashMap;
import java.util.Map;
import tfw.check.Argument;
import tfw.immutable.DataInvalidException;
import tfw.immutable.ImmutableProxy;

/**
 *
 * @immutables.types=numeric
 */
public final class FloatIlaBound
{
    private FloatIlaBound()
    {
        // non-instantiable class
    }

    public static FloatIla create(FloatIla ila, float minimum,
        float maximum)
    {
        Argument.assertNotNull(ila, "ila");
        Argument.assertNotGreaterThan(minimum, maximum, "minimum", "maximum");

        return new MyFloatIla(ila, minimum, maximum);
    }

    private static class MyFloatIla extends AbstractFloatIla
        implements ImmutableProxy
    {
        private final FloatIla ila;
        private final float minimum;
        private final float maximum;

        MyFloatIla(FloatIla ila, float minimum, float maximum)
        {
            super(ila.length());
                    
            this.ila = ila;
            this.minimum = minimum;
            this.maximum = maximum;
        }

        protected void toArrayImpl(float[] array, int offset,
                                   int stride, long start, int length)
            throws DataInvalidException
        {
            ila.toArray(array, offset, stride, start, length);

            for (int ii = offset; length > 0; ii += stride, --length)
            {
                float tmp = array[ii];
                if (tmp < minimum)
                {
                    array[ii] = minimum;
                }
                else if (tmp > maximum)
                {
                    array[ii] = maximum;
                }
            }
        }
                
        public Map<String, Object> getParameters()
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
                        
            map.put("name", "FloatIlaBound");
            map.put("ila", getImmutableInfo(ila));
            map.put("minimum", new Float(minimum));
            map.put("maximum", new Float(maximum));
            map.put("length", new Long(length()));
                        
            return(map);
        }
    }
}
// AUTO GENERATED FROM TEMPLATE
